package com.tierriferreira.desafiofinal2.recyclerview;

import android.view.View;

import com.tierriferreira.desafiofinal2.R;
import com.tierriferreira.desafiofinal2.RecyclerActivity;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.requests.VolleySingleton;

public class ClienteAdapter extends RecyclerAdapter {
    private Storage<Cliente> storage;
    private RecyclerActivity activity;

    public ClienteAdapter(Storage<Cliente> storage, RecyclerActivity activity) {
        this.storage = storage;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // Fazer cast porque apenas a classe que herda tem os getters que queremos.
        ClienteViewHolder clienteHolder = (ClienteViewHolder) holder;
        // Obter cliente pelo índice, neste caso através da posição do recycler view.
        Cliente cliente = storage.retrieveAll().get(position);
        clienteHolder.getNomeView().setText(cliente.getNome());
        // Se for -1, não existe, por isso não setar. Tipos primitivos não podem ser nulos.
        if (cliente.getIdade() != -1) clienteHolder.getIdadeView().setText(String.valueOf(cliente.getIdade()));
        clienteHolder.getFotoView().setImageUrl(cliente.getUrlFoto(), VolleySingleton.getInstance(activity).getImageLoader());
    }

    @Override
    public int getItemLayout() {
        return R.layout.cliente_item;
    }

    @Override
    public RecyclerViewHolder getViewHolder(View view) {
        return new ClienteViewHolder(view, activity);
    }

    @Override
    public int getItemCount() {
        return storage.retrieveAll().size();
    }
}
