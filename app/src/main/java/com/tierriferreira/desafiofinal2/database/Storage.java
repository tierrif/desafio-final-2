package com.tierriferreira.desafiofinal2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/* Foi necessário o uso de templates nesta classe, devido ao retorno dos métodos.
 * Como os métodos retornam o modelo representado, é necessário especificá-lo no parâmetro
 * da template (T). T poderá ser Imovel, Cliente ou AuthCredentials neste projeto. Para
 * mantê-lo dinâmico, no entanto, é melhor usar este modelo genérico, que facilmente permitirá a
 * adição de uma nova tabela na base de dados (sendo que cada classe que herda desta representa uma
 * e apenas uma tabela da base de dados). T será o tipo de dados em classe que representa esta mesma
 * tabela.
 */
public abstract class Storage<T> {
    private DatabaseHelper helper;

    /* Construtor será chamado pela classe que herda através de super().
     * Claro que não pode ser chamado diretamente, pois esta classe é
     * abstrata.
     */
    public Storage(DatabaseHelper helper) {
        this.helper = helper;
    }

    // Obter todos os resultados possíveis na tabela.
    public List<T> retrieveAll() {
        // Como vamos ler, vamos usar a base de dados que pode ser lida.
        SQLiteDatabase db = helper.getReadableDatabase();
        // As colunas que vamos pedir na query.
        String[] projection = getCollumns();
        // Agora a query.
        Cursor cursor = db.query(
                getResponsibleTable(), // Nome da tabela.
                projection, // De que colunas queremos dados.
                null, // Não precisamos de filtrar na cláusula WHERE.
                null, // Não precisamos de filtrar se argumentos são têm um certo valor.
                null, // Não precisamos de GROUP BY.
                null, // Não precisamos de HAVING.
                null // Ordem de resultados.
        );

        /* Deixar a classe que extende iterar os resultados por nós,
         * porque não conseguimos saber os atributos do modelo apenas
         * através da template, que nem é de tipo específico mas completamente
         * genérico (não extende nenhuma classe).
         */
        List<T> items = iterateResults(cursor);

        cursor.close(); // Fechar a query.
        return items;
    }

    // Obter um resultado através do seu ID.
    public T retrieveById(int id) {
        // Como vamos ler, vamos usar a base de dados que pode ser lida.
        SQLiteDatabase db = helper.getReadableDatabase();
        // As colunas que vamos pedir na query.
        String[] projection = getCollumns();
        // Cláusula WHERE.
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        // Argumentos da cláusula WHERE.
        String[] selectionArgs = {String.valueOf(id)};
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

        List<T> items = iterateResults(cursor);

        cursor.close();

        // Retorna nulo se não forem encontrados resultados.
        return items.size() > 0 ? items.get(0) : null;
    }

    // Enviar para a base de dados uma nova entrada, do tipo T (modelo que representa a tabela).
    public void put(T t) {
        // Vamos escrever na base de dados, por isso queremos chamar getWritableDatabase.
        SQLiteDatabase db = helper.getWritableDatabase();
        // Agora, vamos pedir à classe filho para passar o objeto para ContentValues.
        ContentValues values = getValuesFromModel(t);
        /* Por fim, como criámos o nosso array associativo com o que queremos
           inserir na DB, vamos aplicá-lo na mesma.
         */
        db.insert(getResponsibleTable(), null, values);
    }

    // Eliminar uma entrada através do seu ID.
    public void delete(int id) {
        // Vamos escrever na base de dados, por isso queremos chamar getWritableDatabase.
        SQLiteDatabase db = helper.getWritableDatabase();
        // Eliminar da base de dados.
        db.delete(
                getResponsibleTable(),
                "_id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    /* Atualizar uma entrada através da instância do seu modelo e do seu ID.
     * Passamos o id, mesmo que seja parte do modelo, porque não existe garantia nem suporte direto.
     * Isto significa que não podemos aceder a propriedades de tipos genéricos (templates), porque não
     * sabemos o tipo deles de qualquer forma. A única forma seria se todos os modelos herdassem de algo
     * em comum; Neste caso, isto não acontece. Se isto acontecesse, a template desta classe seria
     * <T extends ClasseEmComum>, ClasseEmComum seria a classe que todos os modelos herdam.
     */
    public void update(T newValues, int id) {
        // Vamos escrever na base de dados, por isso queremos chamar getWritableDatabase.
        SQLiteDatabase db = helper.getWritableDatabase();
        // Atualizar a DB com as novas informações.
        db.update(
                getResponsibleTable(),
                getValuesFromModel(newValues),
                "_id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    protected DatabaseHelper getHelper() {
        return helper;
    }

    // A classe que herda vai iterar os resultados baseados na tabela da base de dados.
    protected abstract List<T> iterateResults(Cursor cursor);

    // Traduzir o modelo para ContentValues.
    protected abstract ContentValues getValuesFromModel(T t);

    // Obter as colunas a pesquisar.
    protected abstract String[] getCollumns();

    // A tabela responsável por esta classe que herda, que varia entre classes obviamente.
    protected abstract String getResponsibleTable();
}
