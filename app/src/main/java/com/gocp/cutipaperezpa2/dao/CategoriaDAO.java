package com.gocp.cutipaperezpa2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gocp.cutipaperezpa2.database.BodegaContract;
import com.gocp.cutipaperezpa2.database.BodegaDbHelper;
import com.gocp.cutipaperezpa2.entidad.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private BodegaDbHelper helper;

    public CategoriaDAO(Context ctx){
        helper = new BodegaDbHelper(ctx);
    }


    public List<Categoria> listar(){
        List<Categoria> lista = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(BodegaContract.Categoria.T,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BodegaContract.Categoria.ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(BodegaContract.Categoria.NOMBRE));
                lista.add(new Categoria(id, nombre));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}
