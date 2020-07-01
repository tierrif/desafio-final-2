package com.tierriferreira.desafiofinal2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            Intent intent = new Intent(this, LoginActivity.class);
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

    }
}
