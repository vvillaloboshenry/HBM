package com.example.henry.hbm.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Henry on 25/06/2016.
 */
public class ReservaDAO {
    private AdministrarDB obj;
    private SQLiteDatabase db;
    private Context context;
    private ContentValues registro;
    Cursor C = null;
    private static final int Activos = 1;
    private static final int Finalizados = 2;
    private static final int Cancelados = 3;
    private static final int Papelera = 0;
    private static final int Disponible = 1;
    private static final int Ocupado = 0;


    public ReservaDAO(Context c) {
        context = c;
    }

    public void openDataBase() throws SQLException {
        obj = new AdministrarDB(context, "HotelDB", null, 1);
        db = obj.getWritableDatabase();
    }

    public void closeDataBase() {
        db.close();
    }

    public int insertar(String Fecha, String FechaInicio, String FechaSalida, String DniCliente, String HabitacionID) {
        int ID = 0;
        registro = new ContentValues();
        registro.put("Fecha", Fecha);
        registro.put("FechaInicio", FechaInicio);
        registro.put("FechaSalida", FechaSalida);
        registro.put("DniCliente", DniCliente);
        registro.put("HabitacionID", HabitacionID);
        long cod = db.insert("ReservaHabitacion", null, registro);

        if (cod != -1) {
            Toast.makeText(context, "Se reservo correctamente, Gracias.", Toast.LENGTH_SHORT).show();
            Cursor cur = db.rawQuery("SELECT last_insert_rowid()", null);
            cur.moveToFirst();
            ID = cur.getInt(0);
            cur.close();
            if (ID > -1) {
                Cursor cc = db.rawQuery("update Habitacion set estado=" + Ocupado + " where HabitacionID=" + HabitacionID, null);
                cc.moveToFirst();
                cc.close();
            } else {
                System.out.println("NO SE SE MODIFICO");
            }


        } else {
            Toast.makeText(context, "No se pudo reservar, intentelo mas tarde", Toast.LENGTH_SHORT).show();
        }
        return ID;
    }

    public Cursor consultarAllReservas(int dni) {
        String consulta = "select r._id,r.Fecha,r.FechaInicio,r.FechaSalida,r.DniCliente,c.Nombre,c.Apellido,r.HabitacionID,cat.Titulo,cat.Descripcion," +
                "h.Precio,h.NCuartos,h.NDormitorios,h.NBanos,r.estado,r.dropState from ReservaHabitacion as r inner join Cliente as c On r.DniCliente = c.DniCliente inner join Habitacion as h " +
                "On r.HabitacionID=h.HabitacionID inner join Categoria as cat On h.CategoriaID=cat._id where r.dropState = " + Disponible + " and r.DniCliente=" + dni + " and r.estado!=" + Cancelados;
        C = db.rawQuery(consulta, null);
        return C;
    }

    /**
     * consulta de fechas funcion: where DIFF(fechaInicio,getDate)< 5
     *
     * @return
     */
    public Cursor consultarReservasRecientes(int dni) {
        String campos[] = new String[]{"_id,Fecha,FechaInicio,FechaSalida,DniCliente,HabitacionID"};
        C = db.query("ReservaHabitacion", campos, "dropState=" + Disponible, null, null, null, "FechaSalida");
        return C;
    }

    public Cursor consultarReservasActivas(int dni) {
        String consulta = "select r._id,r.Fecha,r.FechaInicio,r.FechaSalida,r.DniCliente,c.Nombre,c.Apellido,r.HabitacionID,cat.Titulo,cat.Descripcion," +
                "h.Precio,h.NCuartos,h.NDormitorios,h.NBanos,r.estado,r.dropState from ReservaHabitacion as r inner join Cliente as c On r.DniCliente = c.DniCliente inner join Habitacion as h " +
                "On r.HabitacionID=h.HabitacionID inner join Categoria as cat On h.CategoriaID=cat._id where r.estado= " + Activos + " and r.dropState = " + Disponible + " and r.DniCliente=" + dni;
        C = db.rawQuery(consulta, null);
        return C;
    }

    public Cursor consultarReservasCanceladas(int dni) {
        String consulta = "select r._id,r.Fecha,r.FechaInicio,r.FechaSalida,r.DniCliente,c.Nombre,c.Apellido,r.HabitacionID,cat.Titulo,cat.Descripcion," +
                "h.Precio,h.NCuartos,h.NDormitorios,h.NBanos,r.estado,r.dropState from ReservaHabitacion as r inner join Cliente as c On r.DniCliente = c.DniCliente inner join Habitacion as h " +
                "On r.HabitacionID=h.HabitacionID inner join Categoria as cat On h.CategoriaID=cat._id where r.estado= " + Cancelados + " and r.dropState = " + Disponible + " and r.DniCliente=" + dni;
        C = db.rawQuery(consulta, null);
        return C;
    }

    /**
     * consulta de fechs agregar triiger funcion : where fechaSalida<getDate o getDate> fechaSaldida update estado = 2
     *
     * @return
     */
    public Cursor consultarReservasFinalizadas(int dni) {
        String consulta = "select r._id,r.Fecha,r.FechaInicio,r.FechaSalida,r.DniCliente,c.Nombre,c.Apellido,r.HabitacionID,cat.Titulo,cat.Descripcion," +
                "h.Precio,h.NCuartos,h.NDormitorios,h.NBanos,r.estado,r.dropState from ReservaHabitacion as r inner join Cliente as c On r.DniCliente = c.DniCliente inner join Habitacion as h " +
                "On r.HabitacionID=h.HabitacionID inner join Categoria as cat On h.CategoriaID=cat._id where r.estado= " + Finalizados + " and r.dropState = " + Disponible + " and r.DniCliente=" + dni;
        C = db.rawQuery(consulta, null);
        return C;
    }

    // Ver reservas en Papelera
    public Cursor consultarReservasPapelera(int dni) {
        String consulta = "select r._id,r.Fecha,r.FechaInicio,r.FechaSalida,r.DniCliente,c.Nombre,c.Apellido,r.HabitacionID,cat.Titulo,cat.Descripcion," +
                "h.Precio,h.NCuartos,h.NDormitorios,h.NBanos,r.estado,r.dropState from ReservaHabitacion as r inner join Cliente as c On r.DniCliente = c.DniCliente inner join Habitacion as h " +
                "On r.HabitacionID=h.HabitacionID inner join Categoria as cat On h.CategoriaID=cat._id where r.dropState = " + Papelera + " and r.DniCliente=" + dni;
        C = db.rawQuery(consulta, null);
        return C;
    }

    /**
     * Metodo que se encargara de la cancelacion de las reservas dado a un estado
     *
     * @param _idReserva
     */
    public int cancelarReserva(int _idReserva, int _idHabitacion) {
        int rpt = 0;
        registro = new ContentValues();
        registro.put("estado", Disponible);
        int ctd = db.update("Habitacion", registro, "HabitacionID=" + _idHabitacion, null);
        if (ctd > 0) {
            registro = new ContentValues();
            registro.put("estado", Cancelados);
            registro.put("dropState", Disponible);
            rpt = db.update("ReservaHabitacion", registro, "_id=" + _idReserva + " and estado=" + Activos, null);
        } else {
            Toast.makeText(context, "No se pudo cancelar reservacion:", Toast.LENGTH_LONG).show();
        }
        return rpt;
    }

    /**
     * Metodo que se encargara de colocar en un estado de Papelera a las reservas dado a un codigo de estado
     *
     * @param _idReserva Este metodo es el unico que modifica el dropState a 0 independientemente del estado en que se encuentre ( Activos,
     */
    public int enviarPapeleraReserva(int _idReserva) {
        registro = new ContentValues();
        registro.put("dropState", Papelera);
        int rpt = db.update("ReservaHabitacion", registro, "_id=" + _idReserva, null);
        return rpt;
    }

    /**
     * Eliminacion fisica de las reservas por un parametro ID
     *
     * @param _idReserva
     */
    public int eliminarReserva(int _idReserva) {
        int rpt = db.delete("ReservaHabitacion", "_id=" + _idReserva, null);
        return rpt;
    }

}
