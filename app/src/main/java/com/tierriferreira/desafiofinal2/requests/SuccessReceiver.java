package com.tierriferreira.desafiofinal2.requests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * Indica ao utilizador assíncronamente que houve sucesso
 * ao buscar dados em background.
 */
public class SuccessReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Reached", "Reached");
    }
}
