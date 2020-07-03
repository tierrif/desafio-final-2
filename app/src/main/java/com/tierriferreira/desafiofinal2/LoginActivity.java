package com.tierriferreira.desafiofinal2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.auth.AuthSingleton;
import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.models.AuthCredentials;
import com.tierriferreira.desafiofinal2.requests.Receiver;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String PACKAGE = "com.tierriferreira.desafiofinal2.receiver";
    public static final String CHAVE = "com.tierriferreira.desafiofinal2.PREFERENCE_FILE_KEY";
    private EditText username, password;
    private Button btnLogin;
    private CheckBox checkBox;
    private AuthStorage storage;
    private BroadcastReceiver success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        checkBox = (CheckBox) findViewById(R.id.rememberMe);
        btnLogin.setOnClickListener(this);
        storage = new AuthStorage(new DatabaseHelper(this));
        // Iniciar o receiver.
        success = new Receiver();
        registerReceiver(success, new IntentFilter(PACKAGE));
        // Verificar se existe uma sessão.
        SharedPreferences shared = getSharedPreferences(CHAVE, Context.MODE_PRIVATE);
        if (AuthSingleton.getInstance().login(shared.getString("username", null), shared.getString("password", null), storage)) {
            // Buscar credenciais para saber se é super administrador ou não.
            AuthCredentials credentials = storage.retrieveByUsername(shared.getString("username", null));
            // Ir para a atividade principal, já que existe sessão.
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("user", credentials.getUsername());
            intent.putExtra("superAdmin", credentials.isSuperAdmin());
            // Antes de iniciar atividade, notificar que foi encontrada uma sessão.
            Toast.makeText(this, "Sessão recuperada.", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
            return;
        }
        // Se não existem credenciais ainda, criar o super administrador.
        if (storage.retrieveByUsername("admin") == null)
            storage.put(new AuthCredentials("admin", "admin", true));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != btnLogin.getId()) return;

        if (!AuthSingleton.getInstance().login(username.getText().toString(), password.getText().toString(), storage)) {
            Toast.makeText(this, "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
            return;
        }

        Intent in = new Intent(PACKAGE);
        sendBroadcast(in);

        AuthCredentials credentials = storage.retrieveByUsername(username.getText().toString());
        // Manter sessão se o checkbox estiver
        if (checkBox.isChecked()) {
            /* Para obter as nossas SharedPreferences, precisamos da chave exclusivamente nossa.
               A Google recomenda usarmos o nosso pacote para a chave ser única. */
            SharedPreferences shared = getSharedPreferences(CHAVE, Context.MODE_PRIVATE);
            /* Existem vários modos no segundo parâmetro.
               MODE_PRIVATE - significa que APENAS esta aplicação pode aceder a esta chave.
               MODE_WORLD_READABLE - todas as aplicações podem ler esta chave, e apenas ler.
               MODE_WORLD_WRITABLE - todas as aplicações podem ler e escrever nesta chave.

               Não é grave a chave não ser declarada com o pacote no caso de usarmos o contexto
               em modo privado (MODE_PRIVATE), mas é má prática usar outros modos com uma chave
               que facilmente se repetirá.

               Usamos MODE_PRIVATE porque obviamente não queremos que outras aplicações vejam credenciais.
             */
            // Para editar, chamar edit().
            SharedPreferences.Editor editor = shared.edit();
            // Colocar as credenciais.
            editor.putString("username", credentials.getUsername());
            editor.putString("password", credentials.getPassword());
            editor.apply();
            /*
              Vamos usar apply() em vez de commit por uma razão simples, estamos numa atividade.
              Não queremos que a interface do utilizador seja bloqueada devido ao facto de estarmos
              a guardar definições, por isso fazemos de forma assíncrona. Isto significa que os dados
              vão ser guardados noutro processo, enquando o resto da execução funciona. Podemos colocar
              mais código abaixo de apply() e este será executado instantaneamente. Ao contrário de
              commit(), que demorará mais algum tempo, dependendo da quantidade de informação que estamos
              a guardar, que será muita em projeto real.
             */
        }

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("user", credentials.getUsername());
        intent.putExtra("superAdmin", credentials.isSuperAdmin());
        startActivity(intent);
        finish();
        // Return não necessário porque estamos no fim do método.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(success);
    }
}
