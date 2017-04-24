package com.example.henry.hbm.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.henry.hbm.Modelo.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasquez on 22/06/2016.
 */
public class ClienteDAO {
    private AdministrarDB obj;
    private SQLiteDatabase db;
    private Context context;
    private ContentValues registro;

    public ClienteDAO(Context c) {
        context = c;
    }

    public void openDataBase() throws SQLException {
        obj = new AdministrarDB(context, "HotelDB", null, 1);
        db = obj.getWritableDatabase();
    }

    public void closeDataBase() {
        db.close();
    }

    public long crearCliente(int DniCliente, String Nombre, String Apellido, String Clave) {
        registro = new ContentValues();
        registro.put("DniCliente", DniCliente);
        registro.put("Nombre", Nombre);
        registro.put("Apellido", Apellido);
        registro.put("Clave", Clave);
        long cod = db.insert("Cliente", null, registro);
        return cod;
    }

    public List<Cliente> consulta_all_clientes() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = null;
        String campos[] = new String[]{"DniCliente,Nombre,Apellido,Clave,dropState"};
        Cursor c = db.query("Cliente", campos, null, null, null, null, null);
        try {
            if (c.moveToFirst()) {
                do {
                    cliente = new Cliente();
                    cliente.setDniCliente(c.getInt(c.getColumnIndex("DniCliente")));
                    cliente.setNombre(c.getString(c.getColumnIndex("Nombre")));
                    cliente.setApellido(c.getString(c.getColumnIndex("Apellido")));
                    cliente.setClave(c.getString(c.getColumnIndex("Clave")));
                    cliente.setDropState(c.getInt(c.getColumnIndex("dropState")));
                    clientes.add(cliente);
                } while (c.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            c.close();
        }

        return clientes;
    }

    public Cliente validarLogin(String usuario, String clave) {
        Cliente cliente = null;
        String campos[] = new String[]{"DniCliente,Nombre,Apellido,Clave,dropState"};
        Cursor c = db.query("Cliente", campos, "DniCliente =" + usuario + " and Clave='" + clave + "'", null, null, null, null);
        try {
            if (c.moveToFirst()) {
                do {
                    cliente = new Cliente();
                    cliente.setDniCliente(c.getInt(c.getColumnIndex("DniCliente")));
                    cliente.setNombre(c.getString(c.getColumnIndex("Nombre")));
                    cliente.setApellido(c.getString(c.getColumnIndex("Apellido")));
                    cliente.setClave(c.getString(c.getColumnIndex("Clave")));
                    cliente.setDropState(c.getInt(c.getColumnIndex("dropState")));
                } while (c.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            c.close();
        }

        return cliente;
    }
}
