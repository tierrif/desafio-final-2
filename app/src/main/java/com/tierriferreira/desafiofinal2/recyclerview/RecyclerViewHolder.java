package com.tierriferreira.desafiofinal2.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/*
 * Esta classe abstrata pode parecer ser de pouco uso, mas é mesmo necessária devido
 * à generalização no retorno do método getViewHolder em RecyclerAdapter, que também
 * é abstrato. Por isso, não podemos usar uma classe em específico, mas uma geral também.
 */
public abstract class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected static final String TAG = "RecyclerViewHolder";

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClick(v, getPosition());
    }

    public abstract void onItemClick(View view, int position);
}
