package com.example.henry.hbm.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vasquez on 21/06/2016.
 */
public class CategoriaDAO {
    private AdministrarDB obj;
    private SQLiteDatabase db;
    private Context context;
    private ContentValues registro;

    public CategoriaDAO(Context c) {
        context = c;
    }

    public void openDataBase() throws SQLException {
        obj = new AdministrarDB(context, "HotelDB", null, 1);
        db = obj.getWritableDatabase();
    }

    public void closeDataBase() {
        db.close();
    }

    public Cursor leer() {
        String campos[] = new String[]{"_id,Titulo,Descripcion"};
        Cursor C = db.query("Categoria", campos, null, null, null, null, null);
        return C;
    }

}

