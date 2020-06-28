package com.tierriferreira.desafiofinal2.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.tierriferreira.desafiofinal2.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteStorage extends Storage<Cliente> {
    public ClienteStorage(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    protected List<Cliente> iterateResults(Cursor cursor) {
        // Executa sempre que iteração de resultados seja necessária.
        List<Cliente> items = new ArrayList<>(); // Criar uma lista de o que queremos.
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry._ID));
            String nome = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_NOME));
            int idade = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_IDADE));
            String urlFoto = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_URL_FOTO));
            items.add(new Cliente(id, nome, idade, urlFoto)); // Adicionar à lista.
        }
        // Deixar que a classe mãe faça o resto.
        return items;
    }

    @Override
    protected ContentValues getValuesFromModel(Cliente cliente) {
        // Instanciar ContentValues.
        ContentValues values = new ContentValues();
        // Adicionar todos os dados que queremos que a base de dados guarde do modelo (todos).
        values.put(FeedReaderContract.FeedEntry._ID, cliente.getId());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NOME, cliente.getNome());
        values.put(FeedReaderContract.FeedEntry.COLUMN_IDADE, cliente.getIdade());
        values.put(FeedReaderContract.FeedEntry.COLUMN_URL_FOTO, cliente.getUrlFoto());
        // Deixar que a classe mãe faça o resto.
        return values;
    }

    @Override
    protected String[] getCollumns() {
        return new String[]{
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NOME,
                FeedReaderContract.FeedEntry.COLUMN_IDADE,
                FeedReaderContract.FeedEntry.COLUMN_URL_FOTO
        };
    }

    @Override
    protected String getResponsibleTable() {
        return FeedReaderContract.FeedEntry.TABLE_CLIENTE;
    }
}
