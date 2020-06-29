package com.tierriferreira.desafiofinal2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.auth.AuthSingleton;
import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.models.AuthCredentials;
import com.tierriferreira.desafiofinal2.requests.Receiver;
import com.tierriferreira.desafiofinal2.requests.SuccessReceiver;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String PACKAGE = "com.tierriferreira.desafiofinal2.receiver";
    private EditText username, password;
    private Button btnLogin;
    private AuthStorage storage;
    private BroadcastReceiver success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        storage = new AuthStorage(new DatabaseHelper(this));
        // Iniciar o receiver.
        success = new Receiver();
        // Se não existem credenciais ainda, criar o super administrador.
        if (!AuthSingleton.getInstance().login("admin", "admin", storage))
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

        registerReceiver(success, new IntentFilter(PACKAGE));
        Intent in = new Intent(PACKAGE);
        sendBroadcast(in);

        // TODO: Remember me button.
        AuthCredentials credentials = storage.retrieveByUsername(username.getText().toString());
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("user", credentials.getUsername());
        intent.putExtra("superAdmin", credentials.isSuperAdmin());
        startActivity(intent);
        finish();
        // Return não necessário porque estamos no fim do método.
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(success);
    }
}
