package com.example.henry.hbm.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vasquez on 22/06/2016.
 */
public class Cliente implements Parcelable {
    private int DniCliente;
    private String Nombre;
    private String Apellido;
    private int Telefono;
    private String Clave;
    private int dropState;

    public Cliente() {
    }

    public Cliente(int dniCliente, String nombre, String apellido, int telefono, String clave, int dropState) {
        DniCliente = dniCliente;
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        Clave = clave;
        this.dropState = dropState;
    }

    public int getDniCliente() {
        return DniCliente;
    }

    public void setDniCliente(int dniCliente) {
        DniCliente = dniCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public int getDropState() {
        return dropState;
    }

    public void setDropState(int dropState) {
        this.dropState = dropState;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "DniCliente=" + DniCliente +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Telefono=" + Telefono +
                ", Clave='" + Clave + '\'' +
                ", dropState=" + dropState +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.DniCliente);
        dest.writeString(this.Nombre);
        dest.writeString(this.Apellido);
        dest.writeInt(this.Telefono);
        dest.writeString(this.Clave);
        dest.writeInt(this.dropState);
    }

    protected Cliente(Parcel in) {
        this.DniCliente = in.readInt();
        this.Nombre = in.readString();
        this.Apellido = in.readString();
        this.Telefono = in.readInt();
        this.Clave = in.readString();
        this.dropState = in.readInt();
    }

    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        public Cliente createFromParcel(Parcel source) {
            return new Cliente(source);
        }

        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
