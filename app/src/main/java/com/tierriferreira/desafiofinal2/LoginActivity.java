package com.tierriferreira.desafiofinal2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.auth.AuthSingleton;
import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username, password;
    private Button btnLogin;
    private AuthStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        storage = new AuthStorage(new DatabaseHelper(this));
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

        // TODO: Remember me button.
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
        // Return não necessário porque estamos no fim do método.
    }
}
