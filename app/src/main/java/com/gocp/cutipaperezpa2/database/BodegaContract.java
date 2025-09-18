package com.gocp.cutipaperezpa2.database;

public class BodegaContract {

    private BodegaContract(){}

    public static final String DB_NAME = "db_bodega.db";
    public static final int DB_VERSION = 1;

    public static final class Producto {
        public static final String T = "productos";
        public static final String ID = "_id";
        public static final String NOMBRE = "nombre";
        public static final String CANTIDAD = "cantidad";
        public static final String PRECIO = "precio";
        public static final String CATEGORIA_ID = "categoria_id";
    }
    public static final class Categoria {
        public static final String T = "categorias";
        public static final String ID = "_id";
        public static final String NOMBRE = "nombre";
    }

}
