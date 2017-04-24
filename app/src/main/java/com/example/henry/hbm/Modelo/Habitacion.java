package com.example.henry.hbm.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Henry on 17/05/2016.
 */
public class Habitacion implements Parcelable {
    private int HabitacionID;
    private double Precio;
    private int NCuartos;
    private int NDormitorios;
    private int NBanos;
    private int imagen;
    private Categoria categoria;
    private int estado;
    private int dropState;

    public Habitacion() {
    }

    //CONSTRUCTOR 1

    public Habitacion(int habitacionID, double precio, int NCuartos, int NDormitorios, int NBanos, int imagen, Categoria categoria, int estado, int dropState) {
        HabitacionID = habitacionID;
        Precio = precio;
        this.NCuartos = NCuartos;
        this.NDormitorios = NDormitorios;
        this.NBanos = NBanos;
        this.imagen = imagen;
        this.categoria = categoria;
        this.estado = estado;
        this.dropState = dropState;
    }

    public int getHabitacionID() {
        return HabitacionID;
    }

    public void setHabitacionID(int habitacionID) {
        HabitacionID = habitacionID;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public int getNCuartos() {
        return NCuartos;
    }

    public void setNCuartos(int NCuartos) {
        this.NCuartos = NCuartos;
    }

    public int getNDormitorios() {
        return NDormitorios;
    }

    public void setNDormitorios(int NDormitorios) {
        this.NDormitorios = NDormitorios;
    }

    public int getNBanos() {
        return NBanos;
    }

    public void setNBanos(int NBanos) {
        this.NBanos = NBanos;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
        return "Habitacion{" +
                "HabitacionID=" + HabitacionID +
                ", Precio=" + Precio +
                ", NCuartos=" + NCuartos +
                ", NDormitorios=" + NDormitorios +
                ", NBanos=" + NBanos +
                ", imagen=" + imagen +
                ", categoria=" + categoria +
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
        dest.writeInt(this.HabitacionID);
        dest.writeDouble(this.Precio);
        dest.writeInt(this.NCuartos);
        dest.writeInt(this.NDormitorios);
        dest.writeInt(this.NBanos);
        dest.writeInt(this.imagen);
        dest.writeParcelable(this.categoria, 0);
        dest.writeInt(this.estado);
        dest.writeInt(this.dropState);
    }

    protected Habitacion(Parcel in) {
        this.HabitacionID = in.readInt();
        this.Precio = in.readDouble();
        this.NCuartos = in.readInt();
        this.NDormitorios = in.readInt();
        this.NBanos = in.readInt();
        this.imagen = in.readInt();
        this.categoria = in.readParcelable(Categoria.class.getClassLoader());
        this.estado = in.readInt();
        this.dropState = in.readInt();
    }

    public static final Creator<Habitacion> CREATOR = new Creator<Habitacion>() {
        public Habitacion createFromParcel(Parcel source) {
            return new Habitacion(source);
        }

        public Habitacion[] newArray(int size) {
            return new Habitacion[size];
        }
    };
}