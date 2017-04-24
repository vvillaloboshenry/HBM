package com.example.henry.hbm.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vasquez on 07/07/2016.
 */
public class Gasto implements Parcelable {
    private int _id;
    private Reservas reservas;
    private String Fecha;
    private String numDias;
    private Double Precio;
    private int estado;
    private int dropState;

    public Gasto() {

    }

    public Gasto(int _id, Reservas reservas, String fecha, String numDias, Double precio, int estado, int dropState) {
        this._id = _id;
        this.reservas = reservas;
        Fecha = fecha;
        this.numDias = numDias;
        Precio = precio;
        this.estado = estado;
        this.dropState = dropState;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Reservas getReservas() {
        return reservas;
    }

    public void setReservas(Reservas reservas) {
        this.reservas = reservas;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNumDias() {
        return numDias;
    }

    public void setNumDias(String numDias) {
        this.numDias = numDias;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getDropState() {
        return dropState;
    }

    public void setDropState(int dropState) {
        this.dropState = dropState;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeParcelable(this.reservas, 0);
        dest.writeString(this.Fecha);
        dest.writeString(this.numDias);
        dest.writeValue(this.Precio);
        dest.writeInt(this.estado);
        dest.writeInt(this.dropState);
    }

    protected Gasto(Parcel in) {
        this._id = in.readInt();
        this.reservas = in.readParcelable(Reservas.class.getClassLoader());
        this.Fecha = in.readString();
        this.numDias = in.readString();
        this.Precio = (Double) in.readValue(Double.class.getClassLoader());
        this.estado = in.readInt();
        this.dropState = in.readInt();
    }

    public static final Creator<Gasto> CREATOR = new Creator<Gasto>() {
        public Gasto createFromParcel(Parcel source) {
            return new Gasto(source);
        }

        public Gasto[] newArray(int size) {
            return new Gasto[size];
        }
    };
}
