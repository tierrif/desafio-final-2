package com.tierriferreira.desafiofinal2;

import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.database.ImovelCarateristicasStorage;
import com.tierriferreira.desafiofinal2.database.ImovelStorage;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;
import com.tierriferreira.desafiofinal2.recyclerview.ClienteAdapter;
import com.tierriferreira.desafiofinal2.recyclerview.ImovelAdapter;
import com.tierriferreira.desafiofinal2.recyclerview.RecyclerAdapter;

public class ImovelActivity extends RecyclerActivity {
    @Override
    public RecyclerAdapter getAdapter() {
        DatabaseHelper helper = new DatabaseHelper(this);
        Storage<Cliente> cStorage = new ClienteStorage(helper);
        Storage<ImovelCarateristicas> caStorage = new ImovelCarateristicasStorage(helper);
        Storage<Imovel> iStorage = new ImovelStorage(helper, caStorage, cStorage);
        // Instanciamos aqui outra vez porque o contexto muda, ou seja, esta é outra atividade.
        return new ImovelAdapter(iStorage, this);
    }

    @Override
    public Class<?> getEditorActivity() {
        return ImovelEditorActivity.class;
    }
}
