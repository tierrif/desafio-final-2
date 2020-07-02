package com.tierriferreira.desafiofinal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tierriferreira.desafiofinal2.recyclerview.RecyclerAdapter;

public abstract class RecyclerActivity extends AppCompatActivity {
    private boolean dualFrame;
    private int curPosition;

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
        // Obter a view de detalhes, nula se estivermos em modo retrato.
        View details = findViewById(R.id.editor);
        dualFrame = details != null && details.getVisibility() == View.VISIBLE;
        if (savedInstanceState != null) curPosition = savedInstanceState.getInt("curPosition", 0);

        if (dualFrame) showDetails(curPosition);
    }

    public void showDetails(int i) {
        curPosition = i;
        onDetailsShown(dualFrame, i);
    }

    public abstract void onDetailsShown(boolean dualFrame, int i);

    // Obter o adapter do recycler view, que varia.
    public abstract RecyclerAdapter getAdapter();

    // A atividade para onde a intent do editor irá. <?> em templates significa qualquer classe.
    public abstract Class<?> getEditorActivity();
}
