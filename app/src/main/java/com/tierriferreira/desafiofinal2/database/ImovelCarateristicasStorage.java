package com.tierriferreira.desafiofinal2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.tierriferreira.desafiofinal2.models.ImovelCarateristicas;

import java.util.ArrayList;
import java.util.List;

public class ImovelCarateristicasStorage extends Storage<ImovelCarateristicas> {
    public ImovelCarateristicasStorage(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    protected List<ImovelCarateristicas> iterateResults(Cursor cursor) {
        // Executa sempre que iteração de resultados seja necessária.
        List<ImovelCarateristicas> items = new ArrayList<>(); // Criar uma lista de o que queremos.
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry._ID));
            // Inteiros -> Booleanos: Verificar se é maior ou igual que 1 para verdadeiro. 0 sendo falso.
            boolean sauna = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_SAUNA)) >= 1;
            boolean areaComum = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_AREA_COMUM)) >= 1;
            Log.e("Sauna", sauna + "");
            Log.e("Areacomum", areaComum + "");
            items.add(new ImovelCarateristicas(id, sauna, areaComum)); // Adicionar à lista.
        }
        // Deixar que a classe mãe faça o resto.
        return items;
    }

    @Override
    protected ContentValues getValuesFromModel(ImovelCarateristicas carateristicas) {
        // Instanciar ContentValues.
        ContentValues values = new ContentValues();
        // Adicionar todos os dados que queremos que a base de dados guarde do modelo (todos).
        values.put(FeedReaderContract.FeedEntry.COLUMN_SAUNA, carateristicas.hasSauna() ? 1 : 0);
        values.put(FeedReaderContract.FeedEntry.COLUMN_AREA_COMUM, carateristicas.hasAreaComum() ? 1 : 0);
        // Deixar que a classe mãe faça o resto.
        return values;
    }

    @Override
    protected String[] getCollumns() {
        return new String[]{
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_SAUNA,
                FeedReaderContract.FeedEntry.COLUMN_AREA_COMUM
        };
    }

    @Override
    protected String getResponsibleTable() {
        return FeedReaderContract.FeedEntry.TABLE_IMOVEL_CARACTERISTICAS;
    }
}
