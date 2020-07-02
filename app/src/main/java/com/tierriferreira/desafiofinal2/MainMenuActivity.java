package com.tierriferreira.desafiofinal2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tierriferreira.desafiofinal2.database.DatabaseHelper;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    // btnContas será nulo se o utilizador não é super administrador.
    private Button btnClientes, btnImoveis, btnContas;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        if (item.getItemId() == R.id.sair) {
            // Sair e remover a sessão dos preferences.
            SharedPreferences shared = getSharedPreferences(LoginActivity.CHAVE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.remove("username");
            editor.remove("password");
            /*
             * Neste caso em específico, temos de usar commit(). Como apply() é
             * assíncrono, este será executado possivelmente mais tarde, ou seja,
             * a sessão poderia ser eliminada depois da atividade ter iniciado,
             * retomando a sessão de novo.
             */
            editor.commit();
            // Não colocamos a posição, porque estamos a criar.
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.reset) {
            // Repor dados.
            DatabaseHelper helper = new DatabaseHelper(this);
            // onUpgrade simplesmente elimina e cria tabelas.
            helper.reset();
            Toast.makeText(this, "Dados repostos.", Toast.LENGTH_SHORT).show();
            // Não colocamos a posição, porque estamos a criar.
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("superAdmin", false)) setContentView(R.layout.activity_main_menu_admin);
        else setContentView(R.layout.activity_main_menu);
        btnClientes = (Button) findViewById(R.id.btnClientes);
        btnImoveis = (Button) findViewById(R.id.btnImoveis);
        btnContas = (Button) findViewById(R.id.btnContas);
        btnClientes.setOnClickListener(this);
        btnImoveis.setOnClickListener(this);
        // No caso de estar em modo super administrador.
        if (btnContas != null) btnContas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnClientes:
                intent.setClass(this, ClienteActivity.class);
                break;
            case R.id.btnImoveis:
                intent.setClass(this, ImovelActivity.class);
                break;
            case R.id.btnContas:
                intent.setClass(this, CredentialsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
