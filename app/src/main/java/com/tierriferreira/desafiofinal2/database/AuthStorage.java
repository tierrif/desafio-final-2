package com.tierriferreira.desafiofinal2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tierriferreira.desafiofinal2.models.AuthCredentials;

import java.util.ArrayList;
import java.util.List;

public class AuthStorage extends Storage<AuthCredentials> {
    public AuthStorage(DatabaseHelper helper) {
        super(helper);
    }

    // Método adicional porque é a única tabela que busca entradas por outro atributo.
    public AuthCredentials retrieveByUsername(String username) {
        // Como vamos ler, vamos usar a base de dados que pode ser lida.
        SQLiteDatabase db = getHelper().getReadableDatabase();
        // As colunas que vamos pedir na query.
        String[] projection = getCollumns();
        // Cláusula WHERE.
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        // Argumentos da cláusula WHERE.
        String[] selectionArgs = {username};
        // Agora a query.
        Cursor cursor = db.query(
                getResponsibleTable(), // Nome da tabela.
                projection, // De que colunas queremos dados.
                selection, // Filtrar pelo username (cláusula WHERE).
                selectionArgs, // O que o "?" representa na cláusula WHERE.
                null, // Não precisamos de GROUP BY.
                null, // Não precisamos de HAVING.
                null // Ordem de resultados.
        );

        List<AuthCredentials> items = iterateResults(cursor);

        cursor.close();

        // Retorna nulo se não forem encontrados resultados.
        return items.size() > 0 ? items.get(0) : null;
    }

    @Override
    protected List<AuthCredentials> iterateResults(Cursor cursor) {
        // Executa sempre que iteração de resultados seja necessária.
        List<AuthCredentials> items = new ArrayList<>(); // Criar uma lista de o que queremos.
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry._ID));
            String username = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_PASSWORD));
            items.add(new AuthCredentials(id, username, password)); // Adicionar à lista.
        }
        // Deixar que a classe mãe faça o resto.
        return items;
    }

    @Override
    protected ContentValues getValuesFromModel(AuthCredentials credentials) {
        // Instanciar ContentValues.
        ContentValues values = new ContentValues();
        // Adicionar todos os dados que queremos que a base de dados guarde do modelo (todos).
        values.put(FeedReaderContract.FeedEntry._ID, credentials.getId());
        values.put(FeedReaderContract.FeedEntry.COLUMN_USERNAME, credentials.getUsername());
        values.put(FeedReaderContract.FeedEntry.COLUMN_PASSWORD, credentials.getPassword());
        // Deixar que a classe mãe faça o resto.
        return values;
    }

    @Override
    protected String[] getCollumns() {
        return new String[]{
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_USERNAME,
                FeedReaderContract.FeedEntry.COLUMN_PASSWORD
        };
    }

    @Override
    protected String getResponsibleTable() {
        return FeedReaderContract.FeedEntry.TABLE_AUTH;
    }
}
