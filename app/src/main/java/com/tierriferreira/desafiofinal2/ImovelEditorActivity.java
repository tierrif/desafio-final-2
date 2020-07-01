package com.tierriferreira.desafiofinal2;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.database.ImovelCarateristicasStorage;
import com.tierriferreira.desafiofinal2.database.ImovelStorage;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;

public class ImovelEditorActivity extends EditorActivity {
    private EditText descricao, tipologia, localizacao, urlFoto;
    private CheckBox sauna, areaComum;
    private Storage<Imovel> storage;

    @Override
    protected void onReady(int pos) {
        descricao = (EditText) findViewById(R.id.imovelDescricao);
        tipologia = (EditText) findViewById(R.id.imovelTipologia);
        localizacao = (EditText) findViewById(R.id.imovelLocalizacao);
        urlFoto = (EditText) findViewById(R.id.imovelUrlFoto);
        sauna = (CheckBox) findViewById(R.id.sauna);
        areaComum = (CheckBox) findViewById(R.id.areaComum);
        // Quando pos é -1, estamos a criar.
        if (pos == -1) return;
        DatabaseHelper helper = new DatabaseHelper(this);
        storage = new ImovelStorage(helper, new ImovelCarateristicasStorage(helper), new ClienteStorage(helper));
        Imovel imovel = storage.retrieveAll().get(pos);
        descricao.setText(imovel.getDescricao());
        tipologia.setText(imovel.getTipologia());
        localizacao.setText(imovel.getLocalizacao());
        urlFoto.setText(imovel.getUrlFoto());
        // setChecked(boolean) - Setar true ou false se o checkbox está setado. Neste caso já temos os booleanos guardados.
        sauna.setChecked(imovel.getCaracteristicas().hasSauna());
        areaComum.setChecked(imovel.getCaracteristicas().hasAreaComum());
    }

    @Override
    protected void onEditorButtonClick(int viewId, int pos) {
        if (descricao.getText().toString().isEmpty()) {
            Toast.makeText(this, "Coloque pelo menos uma descrição.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obter imovel pelo índice para sabermos o ID, assim como setar novas informações.
        Imovel imovel = storage.retrieveAll().get(pos);
        imovel.setDescricao(descricao.getText().toString());
        imovel.setTipologia(tipologia.getText().toString());
        imovel.setLocalizacao(localizacao.getText().toString());
        imovel.setUrlFoto(urlFoto.getText().toString());
        imovel.getCaracteristicas().setSauna(sauna.isChecked());
        imovel.getCaracteristicas().setAreaComum(areaComum.isChecked());

        // Fazer cast de classe mãe para a que herda, que tem um método próprio de que precisamos.
        Storage<ImovelCarateristicas> cStorage = ((ImovelStorage) storage).getImovelCaracteristicasStorage();
        if (viewId == R.id.delete) {
            storage.delete(imovel.getId());
            cStorage.delete(imovel.getCaracteristicas().getId());
        } else if (viewId == R.id.save) {
            // Quando é -1, estamos a criar, porque o índice é não existente.
            if (pos == -1) {
                storage.put(imovel);
                cStorage.put(imovel.getCaracteristicas());
            } else {
                storage.update(imovel, imovel.getId());
                cStorage.update(imovel.getCaracteristicas(), imovel.getCaracteristicas().getId());
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_imovel_editor;
    }
}
