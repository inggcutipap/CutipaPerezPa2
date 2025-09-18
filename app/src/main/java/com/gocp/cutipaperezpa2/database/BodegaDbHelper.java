package com.gocp.cutipaperezpa2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BodegaDbHelper extends SQLiteOpenHelper {
    public BodegaDbHelper(Context ctx) {
        super(ctx, BodegaContract.DB_NAME, null, BodegaContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + BodegaContract.Categoria.T + " (" +
                BodegaContract.Categoria.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BodegaContract.Categoria.NOMBRE + " TEXT NOT NULL)");


        db.execSQL("CREATE TABLE " + BodegaContract.Producto.T + " (" +
                BodegaContract.Producto.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BodegaContract.Producto.NOMBRE + " TEXT NOT NULL, " +
                BodegaContract.Producto.CANTIDAD + " INTEGER NOT NULL, " +
                BodegaContract.Producto.PRECIO + " REAL NOT NULL, " +
                BodegaContract.Producto.CATEGORIA_ID + " INTEGER, " +
                "FOREIGN KEY(" + BodegaContract.Producto.CATEGORIA_ID + ") REFERENCES " +
                BodegaContract.Categoria.T + "(" + BodegaContract.Categoria.ID + "))");


        db.execSQL("INSERT INTO " + BodegaContract.Categoria.T + "(" +
                BodegaContract.Categoria.NOMBRE + ") VALUES " +
                "('Abarrotes'),('Bebidas'),('Limpieza')");


        db.execSQL("INSERT INTO " + BodegaContract.Producto.T + "(" +
                BodegaContract.Producto.NOMBRE + "," +
                BodegaContract.Producto.CANTIDAD + "," +
                BodegaContract.Producto.PRECIO + "," +
                BodegaContract.Producto.CATEGORIA_ID + ") VALUES " +
                "('Arroz 5kg', 20, 22.50, 1)," +
                "('Aceite 1L', 15, 9.90, 1)," +
                "('Coca Cola 1.5L', 25, 6.50, 2)," +
                "('Detergente 1kg', 10, 8.20, 3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BodegaContract.Producto.T);
        db.execSQL("DROP TABLE IF EXISTS " + BodegaContract.Categoria.T);
        onCreate(db);
    }
}
