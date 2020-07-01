package com.tierriferreira.desafiofinal2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.database.DatabaseHelper;

public abstract class EditorActivity extends Activity implements View.OnClickListener {
    private Button save, delete;
    private DatabaseHelper databaseHelper;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        save.setOnClickListener(this);
        delete.setOnClickListener(this);
        pos = getIntent().getIntExtra("pos", -1);
        onReady(pos);
    }

    @Override
    public void onClick(View v) {
        if (pos == -1 && v.getId() == R.id.delete) {
            Toast.makeText(this, "Não é permitida a eliminação do que não está criado.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Chamar o método implementado e enviar o ID da view, assim como o índice. -1 se estivermos a criar.
        onEditorButtonClick(v.getId(), pos);
    }

    // Executa no final do onCreate. Passa o índice do item a ser editado ou -1 se estivermos a criar.
    protected abstract void onReady(int pos);

    // Executa no clique de um dos botões.
    protected abstract void onEditorButtonClick(int viewId, int pos);

    // Retornar o ID do layout.
    protected abstract int getLayout();
}
