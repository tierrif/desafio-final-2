package com.tierriferreira.desafiofinal2.recyclerview;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.tierriferreira.desafiofinal2.ClienteEditorActivity;
import com.tierriferreira.desafiofinal2.R;
import com.tierriferreira.desafiofinal2.RecyclerActivity;

public class ClienteViewHolder extends RecyclerViewHolder {
    private RecyclerActivity activity;
    private TextView nomeView, idadeView;
    private NetworkImageView fotoView;

    public ClienteViewHolder(View v, RecyclerActivity activity) {
        super(v);
        this.nomeView = (TextView) v.findViewById(R.id.clienteNomeView);
        this.idadeView = (TextView) v.findViewById(R.id.clienteIdadeView);
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

    public TextView getNomeView() {
        return nomeView;
    }

    public TextView getIdadeView() {
        return idadeView;
    }

    public NetworkImageView getFotoView() {
        return fotoView;
    }
}
