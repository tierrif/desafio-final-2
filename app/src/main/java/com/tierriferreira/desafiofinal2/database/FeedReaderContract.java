package com.tierriferreira.desafiofinal2.database;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    /* Recomendado pela Google para manter privado para não existirem
       instanciações acidentais.
     */
    private FeedReaderContract() {}

    // Classe estática que vai representar uma tabela.
    public static class FeedEntry implements BaseColumns {
        // Clientes.
        public static final String TABLE_CLIENTE = "Cliente";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_IDADE = "idade";
        public static final String COLUMN_URL_FOTO = "url_foto";
        // Imóveis.
        public static final String TABLE_IMOVEL = "Imovel";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_TIPOLOGIA = "tipologia";
        public static final String COLUMN_LOCALIZACAO = "localizacao";
        // url_foto já existe, podemos usar a mesma constante.
        public static final String COLUMN_ID_CLIENTE = "id_cliente";
        public static final String COLUMN_ID_CARACTERISTICAS = "id_caracteristicas";
        // Características de imóveis.
        public static final String TABLE_IMOVEL_CARACTERISTICAS = "ImovelCaracteristicas";
        public static final String COLUMN_SAUNA = "sauna";
        public static final String COLUMN_AREA_COMUM = "area_comum";
        // Autenticação.
        public static final String TABLE_AUTH = "Auth";
        public static final String COLUMN_SUPER_ADMIN = "super_admin";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }
}

