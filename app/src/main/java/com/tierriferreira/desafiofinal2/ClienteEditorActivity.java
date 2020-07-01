package com.tierriferreira.desafiofinal2;

import android.widget.EditText;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Cliente;

public class ClienteEditorActivity extends EditorActivity {
    private EditText nome, idade, urlFoto;
    private Storage<Cliente> storage;

    @Override
    protected void onReady(int pos) {
        nome = (EditText) findViewById(R.id.clienteNome);
        idade = (EditText) findViewById(R.id.clienteIdade);
        urlFoto = (EditText) findViewById(R.id.clienteUrlFoto);
        // Quando pos é -1, estamos a criar.
        if (pos == -1) return;
        DatabaseHelper helper = new DatabaseHelper(this);
        storage = new ClienteStorage(helper);
        Cliente cliente = storage.retrieveAll().get(pos);
        nome.setText(cliente.getNome());
        // Se for -1, não existe, por isso não setar. Tipos primitivos não podem ser nulos.
        if (cliente.getIdade() != -1) idade.setText(String.valueOf(cliente.getIdade()));
        urlFoto.setText(cliente.getUrlFoto());
    }

    @Override
    protected void onEditorButtonClick(int viewId, int pos) {
        if (nome.getText().toString().isEmpty()) {
            Toast.makeText(this, "Coloque pelo menos um nome.", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         * Try-catch para prevenir erros causados por números inválidos dados pelo utilizador.
         * Acontece na tentativa de usar inteiros longos, quando esses números não são
         * aplicáveis a inteiros normais. Por isso, um NumberFormatException é "jogado", ao
         * chamar Integer.parseInt().
         */
        int age;
        try {
            age = idade.getText().toString().isEmpty() ? -1 : Integer.parseInt(idade.getText().toString());
        } catch (NumberFormatException e) {
            age = -1;
            Toast.makeText(this, "Idade inválida", Toast.LENGTH_SHORT).show();
        }

        // Obter cliente pelo índice para sabermos o ID, assim como setar novas informações.
        Cliente cliente = storage.retrieveAll().get(pos);
        cliente.setNome(nome.getText().toString());
        cliente.setIdade(age);
        cliente.setUrlFoto(urlFoto.getText().toString());
        if (viewId == R.id.delete) storage.delete(cliente.getId());
        else if (viewId == R.id.save) {
            // Quando é -1, estamos a criar, porque o índice é não existente.
            if (pos == -1) storage.put(cliente);
            else storage.update(cliente, cliente.getId());
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cliente_editor;
    }
}
