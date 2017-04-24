package com.example.henry.hbm.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vasquez on 07/07/2016.
 */
public class GastoDAO {
    private AdministrarDB obj;
    private SQLiteDatabase db;
    private Context context;
    private ContentValues registro;
    Cursor C = null;

    public GastoDAO(Context c) {
        context = c;
    }

    public void openDataBase() throws SQLException {
        obj = new AdministrarDB(context, "HotelDB", null, 1);
        db = obj.getWritableDatabase();
    }

    public void closeDataBase() {
        db.close();
    }

    public long crearGasto(int reservID, String fecha, long numDias, double precio) {
        registro = new ContentValues();
        registro.put("ReservaID", reservID);
        registro.put("Fecha", fecha);
        registro.put("Cantidad", numDias);
        registro.put("Precio", precio);
        long cod = db.insert("Gasto", null, registro);
        return cod;
    }

    public Cursor consultarGastoporReservaID(int reservaID) {
        String consulta = "select _id,ReservaID,Fecha,Cantidad,Precio,estado,dropState from Gasto where ReservaID=" + reservaID;
        C = db.rawQuery(consulta, null);
        return C;
    }


}
