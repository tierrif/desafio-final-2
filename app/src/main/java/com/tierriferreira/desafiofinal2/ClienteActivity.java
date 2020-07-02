package com.tierriferreira.desafiofinal2;

import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.recyclerview.ClienteAdapter;
import com.tierriferreira.desafiofinal2.recyclerview.RecyclerAdapter;

public class ClienteActivity extends RecyclerActivity {
    @Override
    public RecyclerAdapter getAdapter() {
        /*
         * long posImovel - Passar quando estamos a usar o recycler dos clientes
         * para selecionar um para um imóvel, que é chamado sempre que clicamos em
         * "adicionar cliente" no editor de um imóvel.
         */
        int posImovel = getIntent().getIntExtra("posImovel", -1);
        // Instanciamos aqui outra vez porque o contexto muda, ou seja, esta é outra atividade.
        return new ClienteAdapter(new ClienteStorage(new DatabaseHelper(this)), this, posImovel);
    }

    @Override
    public Class<?> getEditorActivity() {
        return ClienteEditorActivity.class;
    }
}
