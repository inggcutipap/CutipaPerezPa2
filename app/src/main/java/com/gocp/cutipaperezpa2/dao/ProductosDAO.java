package com.gocp.cutipaperezpa2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gocp.cutipaperezpa2.database.BodegaContract;
import com.gocp.cutipaperezpa2.database.BodegaDbHelper;

public class ProductosDAO {
    private BodegaDbHelper helper;

    public ProductosDAO(Context ctx){
        helper = new BodegaDbHelper(ctx);
    }

    public long insertar(String nombre, int cantidad, double precio, int categoriaId){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BodegaContract.Producto.NOMBRE, nombre);
        cv.put(BodegaContract.Producto.CANTIDAD, cantidad);
        cv.put(BodegaContract.Producto.PRECIO, precio);
        cv.put(BodegaContract.Producto.CATEGORIA_ID, categoriaId);
        return db.insert(BodegaContract.Producto.T, null, cv);
    }

    public Cursor listar(){
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT p." + BodegaContract.Producto.ID + ", " +
                "p." + BodegaContract.Producto.NOMBRE + ", " +
                "p." + BodegaContract.Producto.CANTIDAD + ", " +
                "p." + BodegaContract.Producto.PRECIO + ", " +
                "p." + BodegaContract.Producto.CATEGORIA_ID + ", " +
                "c." + BodegaContract.Categoria.NOMBRE + " as categoriaNombre " +
                "FROM " + BodegaContract.Producto.T + " p " +
                "INNER JOIN " + BodegaContract.Categoria.T + " c " +
                "ON p." + BodegaContract.Producto.CATEGORIA_ID + " = c." + BodegaContract.Categoria.ID;

        return db.rawQuery(query, null);
    }


    public int actualizar(int id, String nombre, int cantidad, double precio, int categoriaId){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BodegaContract.Producto.NOMBRE, nombre);
        cv.put(BodegaContract.Producto.CANTIDAD, cantidad);
        cv.put(BodegaContract.Producto.PRECIO, precio);
        cv.put(BodegaContract.Producto.CATEGORIA_ID, categoriaId);
        return db.update(BodegaContract.Producto.T, cv,
                BodegaContract.Producto.ID + "=?", new String[]{String.valueOf(id)});
    }

    public int eliminar(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(BodegaContract.Producto.T,
                BodegaContract.Producto.ID + "=?", new String[]{String.valueOf(id)});
    }
}
