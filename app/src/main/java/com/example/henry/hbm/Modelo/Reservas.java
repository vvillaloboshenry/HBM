package com.example.henry.hbm.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vasquez on 27/06/2016.
 */
public class Reservas implements Parcelable {
    private int _id;
    private String Fecha;
    private String FechaInicio;
    private String FechaSalida;
    private Cliente cliente;
    private Habitacion habitacion;
    private int img;
    private int estado;
    private int dropState;

    public Reservas() {

    }

    public Reservas(int _id, String fecha, String fechaInicio, String fechaSalida, Cliente cliente, Habitacion habitacion, int img, int estado, int dropState) {
        this._id = _id;
        Fecha = fecha;
        FechaInicio = fechaInicio;
        FechaSalida = fechaSalida;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.img = img;
        this.estado = estado;
        this.dropState = dropState;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaSalida() {
        return FechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        FechaSalida = fechaSalida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
    public String toString() {
        return "Reservas{" +
                "_id=" + _id +
                ", Fecha='" + Fecha + '\'' +
                ", FechaInicio='" + FechaInicio + '\'' +
                ", FechaSalida='" + FechaSalida + '\'' +
                ", cliente=" + cliente +
                ", habitacion=" + habitacion +
                ", estado=" + estado +
                ", dropState=" + dropState +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.Fecha);
        dest.writeString(this.FechaInicio);
        dest.writeString(this.FechaSalida);
        dest.writeParcelable(this.cliente, flags);
        dest.writeParcelable(this.habitacion, flags);
        dest.writeInt(this.img);
        dest.writeInt(this.estado);
        dest.writeInt(this.dropState);
    }

    protected Reservas(Parcel in) {
        this._id = in.readInt();
        this.Fecha = in.readString();
        this.FechaInicio = in.readString();
        this.FechaSalida = in.readString();
        this.cliente = in.readParcelable(Cliente.class.getClassLoader());
        this.habitacion = in.readParcelable(Habitacion.class.getClassLoader());
        this.img = in.readInt();
        this.estado = in.readInt();
        this.dropState = in.readInt();
    }

    public static final Parcelable.Creator<Reservas> CREATOR = new Parcelable.Creator<Reservas>() {
        public Reservas createFromParcel(Parcel source) {
            return new Reservas(source);
        }

        public Reservas[] newArray(int size) {
            return new Reservas[size];
        }
    };
}
