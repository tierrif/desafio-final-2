package com.tierriferreira.desafiofinal2.requests;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tierriferreira.desafiofinal2.LoginActivity;
import com.tierriferreira.desafiofinal2.MainMenuActivity;
import com.tierriferreira.desafiofinal2.R;
import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.AuthCredentials;
import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestMaker implements Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String URL = "https://www.dropbox.com/s/brj3s1aenj07icm/desafio.json?dl=1";
    public static final String PACKAGE = "com.tierriferreira.desafiofinal2.success";
    private static final String TAG = "RequestMaker";

    private Context context;
    private Storage<Cliente> cStorage;
    private Storage<Imovel> iStorage;
    private AuthStorage auth;

    // AuthStorage, apesar de extender Storage<T>, tem um método próprio, por isso não pode ser generalizado.
    public RequestMaker(Context context, Storage<Cliente> cStorage, Storage<Imovel> iStorage, AuthStorage auth) {
        this.context = context;
        this.cStorage = cStorage;
        this.iStorage = iStorage;
        this.auth = auth;
    }

    public void createRequest() {
        // Criar queue de pedidos.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Criar pedido JSON.
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                this,
                this
        );
        // Finalizar pedido.
        queue.add(req);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Erro ao fazer pedido.", error);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            // Contas de autenticação login.
            JSONArray accounts = response.getJSONArray("users");
            for (int i = 0; i < accounts.length(); i++) {
                JSONObject obj = accounts.getJSONObject(i);
                String usr = obj.getString("user");
                // Se já existir, não criar outra vez.
                if (auth.retrieveByUsername(usr) != null) break;
                auth.put(new AuthCredentials(usr, obj.getString("password"), false));
            }

            // Clientes.
            JSONObject clientesObj = response.getJSONObject("clientes");
            JSONArray clientesArr = clientesObj.getJSONArray("cliente");
            for (int i = 0; i < accounts.length(); i++) {
                JSONObject obj = clientesArr.getJSONObject(i);
                cStorage.put(new Cliente(
                        obj.getString("nome"),
                        obj.getInt("idade"),
                        obj.getString("url_foto")
                ));
            }

            // Imóveis.
            JSONObject imoveisObj = response.getJSONObject("imoveis");
            JSONArray imoveisArr = imoveisObj.getJSONArray("imovel");
            for (int i = 0; i < imoveisArr.length(); i++) {
                JSONObject obj = imoveisArr.getJSONObject(i);
                ImovelCarateristicas iC = new ImovelCarateristicas();
                // Verificar se existe.
                if (obj.has("lista_caracteristicas")) {
                    JSONArray caracteristicas = obj.getJSONArray("lista_caracteristicas");
                    for (int j = 0; j < caracteristicas.length(); j++) {
                        JSONObject cObj = caracteristicas.getJSONObject(j);
                        // Verificar se existem e colocar os valores string convertidos em valores booleanos.
                        if (cObj.has("sauna")) iC.setSauna(cObj.getString("sauna").equals("sim"));
                        if (cObj.has("area_comum"))
                            iC.setAreaComum(cObj.getString("area_comum").equals("sim"));
                    }

                    iStorage.put(new Imovel(
                            obj.getString("descricao"),
                            obj.getString("tipologia"),
                            obj.getString("localizacao"),
                            obj.getString("url_foto"),
                            iC,
                            null // Nenhum imóvel tem cliente.
                    ));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Gerar notificação.
        NotificationManager manager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Pedido completo.")
                .setContentText("Os dados da aplicação foram pedidos com sucesso.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.addLine("Sucesso");
        builder.setStyle(style);
        Notification notif = builder.build();
        notif.vibrate = new long[]{150, 300, 150, 60};
        notif.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(R.drawable.ic_launcher_background, notif);
        Log.e("Reached", "Reached");
    }
}
