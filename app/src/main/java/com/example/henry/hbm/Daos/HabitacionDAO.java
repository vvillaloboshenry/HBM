package com.example.henry.hbm.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Vasquez on 13/06/2016.
 */
public class HabitacionDAO {
    private AdministrarDB obj;
    private SQLiteDatabase db;
    private Context context;
    private ContentValues registro;

    public HabitacionDAO(Context c) {
        context = c;
    }

    public void openDataBase() throws SQLException {
        obj = new AdministrarDB(context, "HotelDB", null, 1);
        db = obj.getWritableDatabase();
    }

    public void closeDataBase() {
        db.close();
    }

    public void crearHabitacion(int HabitacionID, double Precio, int NCuartos, int NDormitorios, int NBanos, int CategoriaID) {
        String sql = "insert or replace into Habitacion(HabitacionID,Precio,NCuartos,NDormitorios,NBanos,CategoriaID) values(" + HabitacionID + "," + Precio + "," + NCuartos + "," + NDormitorios + "," + NBanos + "," + CategoriaID + ")";
        db.execSQL(sql);
        System.out.println("INSERTE");
    }

    public Cursor leer() {
        String consulta = "select h.HabitacionID,c.Titulo,c.Descripcion,h.Precio,h.NCuartos,h.NDormitorios, h.NBanos from Habitacion as h inner join " +
                "Categoria as c ON h.CategoriaID=c._id where h.estado = 1 and h.dropState = 1 ";
        Cursor C = db.rawQuery(consulta, null);
        return C;
    }

}

