package com.tierriferreira.desafiofinal2;

import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.recyclerview.ClienteAdapter;
import com.tierriferreira.desafiofinal2.recyclerview.RecyclerAdapter;

public class ClienteActivity extends RecyclerActivity {
    @Override
    public RecyclerAdapter getAdapter() {
        // Instanciamos aqui outra vez porque o contexto muda, ou seja, esta é outra atividade.
        return new ClienteAdapter(new ClienteStorage(new DatabaseHelper(this)), this);
    }

    @Override
    public Class<?> getEditorActivity() {
        return ClienteEditorActivity.class;
    }
}
