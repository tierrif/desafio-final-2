package com.tierriferreira.desafiofinal2.recyclerview;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.tierriferreira.desafiofinal2.ClienteEditorActivity;
import com.tierriferreira.desafiofinal2.R;
import com.tierriferreira.desafiofinal2.RecyclerActivity;

public class ImovelViewHolder extends RecyclerViewHolder {
    private RecyclerActivity activity;
    private TextView descricaoView, tipologiaView, localizacaoView;
    private NetworkImageView fotoView;

    public ImovelViewHolder(View v, RecyclerActivity activity) {
        super(v);
        this.descricaoView = (TextView) v.findViewById(R.id.imovelDescricaoView);
        this.tipologiaView = (TextView) v.findViewById(R.id.imovelTipologiaView);
        this.fotoView = (NetworkImageView) v.findViewById(R.id.fotoView);
        this.activity = activity;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(activity, ClienteEditorActivity.class);
        intent.putExtra("pos", position);
        activity.startActivity(intent);
        activity.finish();
        // Não é necessário chamar "return", estamos no fim do método.
    }

    public TextView getDescricaoView() {
        return descricaoView;
    }

    public TextView getTipologiaView() {
        return tipologiaView;
    }

    public TextView getLocalizacaoView() {
        return localizacaoView;
    }

    public NetworkImageView getFotoView() {
        return fotoView;
    }
}
