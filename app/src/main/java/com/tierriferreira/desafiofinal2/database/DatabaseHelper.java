package com.tierriferreira.desafiofinal2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Versão da base de dados. Recomendada pela Google para iniciar em 1.
    public static final int DATABASE_VERSION = 2; // Incrementa em cada alteração física na DB.
    public static final String DATABASE_NAME = "DesafioFinal2.db"; // Nome do ficheiro da DB.

    // Queries SQL ficam em constantes.
    // Criação das tabelas.
    public static final String SQL_CREATE_ENTRIES_CLIENTE =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.TABLE_CLIENTE + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_NOME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_IDADE + " INTEGER," +
                    FeedReaderContract.FeedEntry.COLUMN_URL_FOTO + " TEXT" +
                    ")";

    /* ID do cliente fica na tabela de imóveis, porque Cliente 1 - N Imovel,
       e a chave estrangeira (não declarada por desempenho) vai para a tabela
       com a relação de muitos. */
    public static final String SQL_CREATE_ENTRIES_IMOVEL =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.TABLE_IMOVEL + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_DESCRICAO + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_TIPOLOGIA + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_LOCALIZACAO + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_URL_FOTO + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_ID_CLIENTE + " INTEGER," +
                    FeedReaderContract.FeedEntry.COLUMN_ID_CARACTERISTICAS + " INTEGER" +
                    ")";

    public static final String SQL_CREATE_ENTRIES_IMOVEL_CARACTERISTICAS =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.TABLE_IMOVEL_CARACTERISTICAS + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_SAUNA + " INTEGER(1)," +
                    FeedReaderContract.FeedEntry.COLUMN_AREA_COMUM + " INTEGER(1)" +
                    ")";

    public static final String SQL_CREATE_ENTRIES_AUTH =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedEntry.TABLE_AUTH + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_SUPER_ADMIN + " INTEGER(1)," +
                    FeedReaderContract.FeedEntry.COLUMN_USERNAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_PASSWORD + " TEXT" +
                    ")";

    // Eliminação das tabelas.
    public static final String SQL_DELETE_ENTRIES_CLIENTE =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_CLIENTE;
    public static final String SQL_DELETE_ENTRIES_IMOVEL =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_IMOVEL;
    public static final String SQL_DELETE_ENTRIES_IMOVEL_CARACTERISTICAS =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_IMOVEL_CARACTERISTICAS;
    public static final String SQL_DELETE_ENTRIES_AUTH =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_AUTH;

    public DatabaseHelper(Context context) {
        // O Android vai fazer tudo o que necessitar se dermos o nome da DB e a versão.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar as tabelas quando a DB for criada.
        db.execSQL(SQL_CREATE_ENTRIES_CLIENTE);
        db.execSQL(SQL_CREATE_ENTRIES_IMOVEL);
        db.execSQL(SQL_CREATE_ENTRIES_IMOVEL_CARACTERISTICAS);
        db.execSQL(SQL_CREATE_ENTRIES_AUTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* A Google recomenda eliminar a base de dados e criar de novo quando existir
           uma atualização, porque esta está armazenada apenas em cache.
         */
        // Eliminar as tabelas primeiro.
        db.execSQL(SQL_DELETE_ENTRIES_CLIENTE);
        db.execSQL(SQL_DELETE_ENTRIES_IMOVEL);
        db.execSQL(SQL_DELETE_ENTRIES_IMOVEL_CARACTERISTICAS);
        db.execSQL(SQL_DELETE_ENTRIES_AUTH);
        onCreate(db); // Reaproveitar o código e atualizar a BD.
    }

    public void reset() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES_CLIENTE);
        db.execSQL(SQL_DELETE_ENTRIES_IMOVEL);
        db.execSQL(SQL_DELETE_ENTRIES_IMOVEL_CARACTERISTICAS);
        db.execSQL(SQL_DELETE_ENTRIES_AUTH);

        db.execSQL(SQL_CREATE_ENTRIES_CLIENTE);
        db.execSQL(SQL_CREATE_ENTRIES_IMOVEL);
        db.execSQL(SQL_CREATE_ENTRIES_IMOVEL_CARACTERISTICAS);
        db.execSQL(SQL_CREATE_ENTRIES_AUTH);
    }
}
