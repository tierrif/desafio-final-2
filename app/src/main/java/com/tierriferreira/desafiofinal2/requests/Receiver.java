package com.tierriferreira.desafiofinal2.requests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * Inicializa o serviço de criação de pedido.
 */
public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RequestService.class);
        // Iniciar o serviço de pedidos.
        context.startService(i);
    }
}
