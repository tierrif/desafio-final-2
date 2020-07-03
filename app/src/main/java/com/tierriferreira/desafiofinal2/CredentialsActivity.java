package com.tierriferreira.desafiofinal2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.AuthCredentials;

import java.util.List;

public class CredentialsActivity extends AppCompatActivity implements View.OnClickListener {
    /* https://developer.android.com/training/keyboard-input/style#AutoComplete
     * AutoCompleteTextView - View que herda de EditText que permite fazer
     * auto compleção de strings dadas no java, através do ArrayAdapter.
     * Também existe EditText#setAutofillHints, mas esse método requere API 26 (Android 8.0),
     * portanto é necessário usar algo que suporta versões antigas também.
     */
    private AutoCompleteTextView usr;
    private EditText pwd;
    private Button save;
    private Storage<AuthCredentials> storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        usr = (AutoCompleteTextView) findViewById(R.id.usernameEdit);
        pwd = (EditText) findViewById(R.id.passwordEdit);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        // Auto completar todos os usernames existentes no EditText de username.
        DatabaseHelper helper = new DatabaseHelper(this);
        // Inicializar o representante da tabela de autenticação.
        storage = new AuthStorage(helper);
        // Obter todas as credenciais para obter os usernames.
        List<AuthCredentials> creds = storage.retrieveAll();
        // Criar um array com o tamanho da lista de credenciais.
        String[] hints = new String[creds.size()];
        // For normal porque é um array e precisamos de um array em ArrayAdapters. Popular o array com os usernames.
        for (int i = 0; i < creds.size(); i++) hints[i] = creds.get(i).getUsername();
        /*
         * Criar o adapter, que terá todos os resultados, neste caso os usernames.
         * Como a Google usa android.R.layout.simple_list_item_1 como layout de exemplo,
         * não vamos alterar o que já existe.
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hints);
        // Setar o adapter.
        usr.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != save.getId()) return;
        // Fazer cast porque precisamos de um método próprio de AuthStorage.
        AuthCredentials creds = ((AuthStorage) storage).retrieveByUsername(usr.getText().toString());
        if (creds == null) {
            Toast.makeText(this, "Utilizador não encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.getText().toString().isEmpty()) {
            Toast.makeText(this, "A palavra passe não pode ser vazia.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Alterar a palavra passe.
        creds.setPassword(pwd.getText().toString());
        // Atualizar BD.
        storage.update(creds, creds.getId());
        Toast.makeText(this, "Credenciais atualizadas para " + creds.getUsername() + ".", Toast.LENGTH_SHORT).show();
    }
}
