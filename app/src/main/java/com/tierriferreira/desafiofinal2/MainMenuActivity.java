package com.tierriferreira.desafiofinal2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("superAdmin", false)) setContentView(R.layout.activity_main_menu_admin);
        else setContentView(R.layout.activity_main_menu);
    }
}
