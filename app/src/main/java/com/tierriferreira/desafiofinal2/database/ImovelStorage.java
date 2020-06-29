package com.tierriferreira.desafiofinal2.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.tierriferreira.desafiofinal2.models.Cliente;
import com.tierriferreira.desafiofinal2.models.Imovel;
import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;

import java.util.ArrayList;
import java.util.List;

public class ImovelStorage extends Storage<Imovel> {
    private Storage<ImovelCarateristicas> imovelCStorage;
    private Storage<Cliente> clienteStorage;

    public ImovelStorage(DatabaseHelper helper, Storage<ImovelCarateristicas> imovelCStorage, Storage<Cliente> clienteStorage) {
        super(helper);
        this.imovelCStorage = imovelCStorage;
        this.clienteStorage = clienteStorage;
    }

    @Override
    protected List<Imovel> iterateResults(Cursor cursor) {
        // Executa sempre que iteração de resultados seja necessária.
        List<Imovel> items = new ArrayList<>(); // Criar uma lista de o que queremos.
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry._ID));
            String descricao = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_DESCRICAO));
            String tipologia = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_TIPOLOGIA));
            String localizacao = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_LOCALIZACAO));
            String urlFoto = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_URL_FOTO));
            int idCaracteristicas = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_ID_CARACTERISTICAS));
            int idCliente = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_ID_CLIENTE));
            items.add(new Imovel(id, descricao, tipologia, localizacao, urlFoto, imovelCStorage.retrieveById(idCaracteristicas), clienteStorage.retrieveById(idCliente))); // Adicionar à lista.
        }
        // Deixar que a classe mãe faça o resto.
        return items;
    }

    @Override
    protected ContentValues getValuesFromModel(Imovel imovel) {
        // Instanciar ContentValues.
        ContentValues values = new ContentValues();
        // Adicionar todos os dados que queremos que a base de dados guarde do modelo (todos).
        values.put(FeedReaderContract.FeedEntry.COLUMN_DESCRICAO, imovel.getDescricao());
        values.put(FeedReaderContract.FeedEntry.COLUMN_TIPOLOGIA, imovel.getTipologia());
        values.put(FeedReaderContract.FeedEntry.COLUMN_LOCALIZACAO, imovel.getLocalizacao());
        values.put(FeedReaderContract.FeedEntry.COLUMN_URL_FOTO, imovel.getUrlFoto());
        values.put(FeedReaderContract.FeedEntry.COLUMN_ID_CARACTERISTICAS, imovel.getCaracteristicas().getId());
        // No caso de o imóvel não ter cliente, usar -1 como ID de cliente não existente.
        if (imovel.getCliente() != null) values.put(FeedReaderContract.FeedEntry.COLUMN_ID_CLIENTE, imovel.getCliente().getId());
        else values.put(FeedReaderContract.FeedEntry.COLUMN_ID_CLIENTE, -1);
        // Deixar que a classe mãe faça o resto.
        return values;
    }

    @Override
    protected String[] getCollumns() {
        return new String[]{
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_DESCRICAO,
                FeedReaderContract.FeedEntry.COLUMN_TIPOLOGIA,
                FeedReaderContract.FeedEntry.COLUMN_LOCALIZACAO,
                FeedReaderContract.FeedEntry.COLUMN_URL_FOTO,
                FeedReaderContract.FeedEntry.COLUMN_ID_CARACTERISTICAS,
                FeedReaderContract.FeedEntry.COLUMN_ID_CLIENTE
        };
    }

    @Override
    protected String getResponsibleTable() {
        return FeedReaderContract.FeedEntry.TABLE_IMOVEL;
    }
}
