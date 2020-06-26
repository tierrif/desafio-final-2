package com.tierriferreira.desafiofinal2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tierriferreira.desafiofinal2.models.AuthCredentials;

import java.util.ArrayList;
import java.util.List;

public class AuthStorage {
    private DatabaseHelper helper;

    public AuthStorage(DatabaseHelper helper) {
        this.helper = helper;
    }

    public List<AuthCredentials> retrieveAllCredentials() {
        // Como vamos ler, vamos usar a base de dados que pode ser lida.
        SQLiteDatabase db = helper.getReadableDatabase();
        // As colunas que vamos pedir na query.
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_USERNAME,
                FeedReaderContract.FeedEntry.COLUMN_PASSWORD
        };
        // Agora a query.
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_AUTH, // Nome da tabela.
                projection, // De que colunas queremos dados.
                null, // Não precisamos de filtrar na cláusula WHERE.
                null, // Não precisamos de filtrar se argumentos são têm um certo valor.
                null, // Não precisamos de GROUP BY.
                null, // Não precisamos de HAVING.
                null // Ordem de resultados.
        );
        // Temos de iterar os resultados.
        List<AuthCredentials> items = new ArrayList<>(); // Criar uma lista de o que queremos.
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_PASSWORD));
            items.add(new AuthCredentials(username, password)); // Adicionar à lista.
        }
        cursor.close(); // Fechar a query.

        return items;
    }

    public AuthCredentials getCredentialsByUsername(String username) {
        // Como vamos ler, vamos usar a base de dados que pode ser lida.
        SQLiteDatabase db = helper.getReadableDatabase();
        // As colunas que vamos pedir na query.
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_USERNAME,
                FeedReaderContract.FeedEntry.COLUMN_PASSWORD
        };
        // Cláusula WHERE.
        String selection = FeedReaderContract.FeedEntry.COLUMN_USERNAME + " = ?";
        // Argumentos da cláusula WHERE.
        String[] selectionArgs = { username };

        // Agora a query.
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_AUTH, // Nome da tabela.
                projection, // De que colunas queremos dados.
                selection, // Filtrar pelo username (cláusula WHERE).
                selectionArgs, // O que o "?" representa na cláusula WHERE.
                null, // Não precisamos de GROUP BY.
                null, // Não precisamos de HAVING.
                null // Ordem de resultados.
        );
        // Temos de iterar os resultados.
        List<AuthCredentials> items = new ArrayList<>(); // Criar uma lista de o que queremos.
        while (cursor.moveToNext()) {
            String usernameColumn = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_USERNAME));
            String passwordColumn = cursor.getString(cursor.getColumnIndex(
                    FeedReaderContract.FeedEntry.COLUMN_PASSWORD));
            items.add(new AuthCredentials(usernameColumn, passwordColumn)); // Adicionar à lista.
        }
        cursor.close(); // Fechar a query.

        // Retorna nulo se não forem encontrados resultados.
        return items.size() > 0 ? items.get(0) : null;
    }

    public boolean updateCredentialsFor(String username, String newPassword) {
        // Se não existe, retornar falso (insucesso).
        if (getCredentialsByUsername(username) == null) return false;
        // Vamos escrever na base de dados, por isso queremos chamar getWritableDatabase.
        SQLiteDatabase db = helper.getWritableDatabase();
        // Agora, vamos criar um novo conjunto de dados a escrever.
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_PASSWORD, newPassword);
        db.update(
                FeedReaderContract.FeedEntry.TABLE_AUTH,
                values,
                FeedReaderContract.FeedEntry.COLUMN_USERNAME + " = ?",
                new String[]{username}
        );
        // Sucesso.
        return true;
    }
}
