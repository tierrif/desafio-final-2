package com.tierriferreira.desafiofinal2.requests;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.database.ImovelCarateristicasStorage;
import com.tierriferreira.desafiofinal2.database.ImovelStorage;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;

public class RequestService extends IntentService {
    public RequestService() {
        super("RequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Inicializar o helper da base de dados.
        DatabaseHelper helper = new DatabaseHelper(this);
        // Inicializar todos os representantes das tabelas.
        Storage<Cliente> cStorage = new ClienteStorage(helper);
        Storage<ImovelCarateristicas> caracteristicas = new ImovelCarateristicasStorage(helper);
        Storage<Imovel> iStorage = new ImovelStorage(helper, caracteristicas, cStorage);
        // Tem o seu próprio método que vai ser utilizado, não pode ser generalizada.
        AuthStorage auth = new AuthStorage(helper);
        // Inicializar o gerenciador de pedidos Volley.
        RequestMaker maker = new RequestMaker(this, cStorage, iStorage, caracteristicas, auth);
        // Iniciar pedido.
        maker.createRequest();
    }
}
