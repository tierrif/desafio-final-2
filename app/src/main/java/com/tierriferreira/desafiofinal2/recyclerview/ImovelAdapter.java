package com.tierriferreira.desafiofinal2.recyclerview;

import android.view.View;

import com.tierriferreira.desafiofinal2.R;
import com.tierriferreira.desafiofinal2.RecyclerActivity;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.requests.VolleySingleton;

public class ImovelAdapter extends RecyclerAdapter {
    private Storage<Imovel> storage;
    private RecyclerActivity activity;

    public ImovelAdapter(Storage<Imovel> storage, RecyclerActivity activity) {
        this.storage = storage;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // Fazer cast porque apenas a classe que herda tem os getters que queremos.
        ImovelViewHolder imovelViewHolder = (ImovelViewHolder) holder;
        // Obter imovel pelo índice, neste caso através da posição do recycler view.
        Imovel imovel = storage.retrieveAll().get(position);
        imovelViewHolder.getDescricaoView().setText(imovel.getDescricao());
        imovelViewHolder.getTipologiaView().setText(imovel.getTipologia());
        imovelViewHolder.getLocalizacaoView().setText(imovel.getLocalizacao());
        imovelViewHolder.getSaunaValue().setText(imovel.getCaracteristicas().hasSauna() ? "sim" : "não");
        imovelViewHolder.getAreaComumValue().setText(imovel.getCaracteristicas().hasAreaComum() ? "sim" : "não");
        if (imovel.getCliente() != null) imovelViewHolder.getVendidoValue().setText("sim, a " + imovel.getCliente().getNome());
        imovelViewHolder.getFotoView().setImageUrl(imovel.getUrlFoto(), VolleySingleton.getInstance(activity).getImageLoader());
    }

    @Override
    public int getItemLayout() {
        return R.layout.imovel_item;
    }

    @Override
    public RecyclerViewHolder getViewHolder(View view) {
        return new ImovelViewHolder(view, activity);
    }

    @Override
    public int getItemCount() {
        return storage.retrieveAll().size();
    }
}
