package com.tierriferreira.desafiofinal2.recyclerview;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.tierriferreira.desafiofinal2.ClienteEditorActivity;
import com.tierriferreira.desafiofinal2.ImovelEditorActivity;
import com.tierriferreira.desafiofinal2.R;
import com.tierriferreira.desafiofinal2.RecyclerActivity;

public class ClienteViewHolder extends RecyclerViewHolder {
    private RecyclerActivity activity;
    private TextView nomeView, idadeView;
    private NetworkImageView fotoView;
    private int posImovel;

    public ClienteViewHolder(View v, RecyclerActivity activity) {
        this(v, activity, -1);
    }

    /*
     * long posImovel - Passar quando estamos a usar o recycler dos clientes
     * para selecionar um para um imóvel, que é chamado sempre que clicamos em
     * "adicionar cliente" no editor de um imóvel.
     */
    public ClienteViewHolder(View v, RecyclerActivity activity, int posImovel) {
        super(v);
        this.nomeView = (TextView) v.findViewById(R.id.clienteNomeView);
        this.idadeView = (TextView) v.findViewById(R.id.clienteIdadeView);
        this.fotoView = (NetworkImageView) v.findViewById(R.id.fotoView);
        this.activity = activity;
        this.posImovel = posImovel;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (posImovel != -1) {
            // Se o utilizador selecionou um cliente a partir do menu do editor do imovel, ir de volta
            // e enviar a posição selecionada.
            Intent intent = new Intent(activity, ImovelEditorActivity.class);
            intent.putExtra("pos", posImovel);
            intent.putExtra("posCliente", position);
            activity.startActivity(intent);
            activity.finish();
            return;
        }
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
