package com.tierriferreira.desafiofinal2.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return getViewHolder(v);
    }

    // Retornar o ID do layout do item, que varia.
    public abstract int getItemLayout();

    // Retornar o objeto do view holder através da view. Este objeto tem de ser de um modelo que
    // implementa RecyclerViewHolder.
    public abstract RecyclerViewHolder getViewHolder(View view);
}
