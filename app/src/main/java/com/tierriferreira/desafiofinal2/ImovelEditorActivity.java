package com.tierriferreira.desafiofinal2;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tierriferreira.desafiofinal2.database.ClienteStorage;
import com.tierriferreira.desafiofinal2.database.DatabaseHelper;
import com.tierriferreira.desafiofinal2.database.ImovelCarateristicasStorage;
import com.tierriferreira.desafiofinal2.database.ImovelStorage;
import com.tierriferreira.desafiofinal2.database.Storage;
import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;

public class ImovelEditorActivity extends EditorActivity {
    private EditText descricao, tipologia, localizacao, urlFoto;
    private Button adicionarCliente;
    private CheckBox sauna, areaComum;
    private Storage<Imovel> storage;

    @Override
    protected void onReady(int pos) {
        descricao = (EditText) findViewById(R.id.imovelDescricao);
        tipologia = (EditText) findViewById(R.id.imovelTipologia);
        localizacao = (EditText) findViewById(R.id.imovelLocalizacao);
        urlFoto = (EditText) findViewById(R.id.imovelUrlFoto);
        adicionarCliente = (Button) findViewById(R.id.adicionarCliente);
        sauna = (CheckBox) findViewById(R.id.sauna);
        areaComum = (CheckBox) findViewById(R.id.areaComum);
        adicionarCliente.setOnClickListener(this);
        DatabaseHelper helper = new DatabaseHelper(this);
        storage = new ImovelStorage(helper, new ImovelCarateristicasStorage(helper), new ClienteStorage(helper));
        // Quando pos é -1, estamos a criar.
        if (pos == -1) return;
        Imovel imovel = storage.retrieveAll().get(pos);
        int posCliente = getIntent().getIntExtra("posCliente", -1);
        if (posCliente != -1) {
            // Não repetir de novo.
            getIntent().removeExtra("posCliente");
            // Buscar a tabela dos clientes através de um método próprio de ImovelStorage.
            Storage<Cliente> cStorage = ((ImovelStorage) storage).getClienteStorage();
            Cliente cliente = cStorage.retrieveAll().get(posCliente);
            adicionarCliente.setText("Cliente: " + cliente.getNome());
            // Atualizar dados.
            imovel.setCliente(cliente);
            storage.update(imovel, imovel.getId());
        }
        // Mostrar o cliente a que já está vendido no botão.
        if (imovel.getCliente() != null) adicionarCliente.setText("Cliente: " + imovel.getCliente().getNome());
        descricao.setText(imovel.getDescricao());
        tipologia.setText(imovel.getTipologia());
        localizacao.setText(imovel.getLocalizacao());
        urlFoto.setText(imovel.getUrlFoto());
        // setChecked(boolean) - Setar true ou false se o checkbox está setado. Neste caso já temos os booleanos guardados.
        if (imovel.getCaracteristicas() == null) {
            Log.e("Editor", "Caracteristicas é nulo");
            return;
        }
        sauna.setChecked(imovel.getCaracteristicas().hasSauna());
        areaComum.setChecked(imovel.getCaracteristicas().hasAreaComum());
    }

    @Override
    protected void onEditorButtonClick(int viewId, int pos) {
        if (viewId == R.id.adicionarCliente) {
            if (pos == -1) {
                // Não permitir adicionar um cliente a um imóvel que ainda não existe.
                Toast.makeText(this, "Não pode adicionar clientes a imóveis ainda não criados.", Toast.LENGTH_SHORT).show();
                return;
            }
            /*
             * Iniciar a atividade recycler de clientes, mas com a posição do imóvel.
             * Isto porque nós vamos deixar o utilizador listar os clientes, e quando
             * selecionar algum, esse será o cliente adicionado a este imóvel (nesta posição).
             */
            Intent intent = new Intent(this, ClienteActivity.class);
            intent.putExtra("posImovel", pos);
            startActivity(intent);
            finish();
            return;
        }

        if (descricao.getText().toString().isEmpty()) {
            Toast.makeText(this, "Coloque pelo menos uma descrição.", Toast.LENGTH_SHORT).show();
            return;
        }

        Imovel imovel;
        // Obter cliente pelo índice para sabermos o ID, assim como setar novas informações.
        if (pos != -1) imovel = storage.retrieveAll().get(pos);
        else imovel = new Imovel(); // Se estivermos a criar
        imovel.setDescricao(descricao.getText().toString());
        imovel.setTipologia(tipologia.getText().toString());
        imovel.setLocalizacao(localizacao.getText().toString());
        imovel.setUrlFoto(urlFoto.getText().toString());
        if (imovel.getCaracteristicas() == null) imovel.setCaracteristicas(new ImovelCarateristicas());
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
                long id = cStorage.put(imovel.getCaracteristicas());
                imovel.getCaracteristicas().setId(id);
                storage.put(imovel);
            } else {
                cStorage.update(imovel.getCaracteristicas(), imovel.getCaracteristicas().getId());
                storage.update(imovel, imovel.getId());
            }
        }

        // Para atualizar o recycler view, temos que chamar o onCreate outra vez, por isso não é possível aproveitar o ciclo de vida.
        Intent intent = new Intent(this, ImovelActivity.class);
        startActivity(intent);
        // Terminar a atividade de edição.
        finish();
        // Return não necessário, estamos no fim do método.
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_imovel_editor;
    }
}
