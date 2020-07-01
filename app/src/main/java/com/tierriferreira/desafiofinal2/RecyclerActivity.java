package com.tierriferreira.desafiofinal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tierriferreira.desafiofinal2.recyclerview.RecyclerAdapter;

public abstract class RecyclerActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.novo) {
            Intent intent = new Intent(this, getEditorActivity());
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
        setContentView(R.layout.activity_recycler);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(getAdapter());
    }

    // Obter o adapter do recycler view, que varia.
    public abstract RecyclerAdapter getAdapter();

    // A atividade para onde a intent do editor irá. <?> em templates significa qualquer classe.
    public abstract Class<?> getEditorActivity();
}
