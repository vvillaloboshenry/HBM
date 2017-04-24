package com.example.henry.hbm.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vasquez on 21/06/2016.
 */
public class Categoria implements Parcelable {
    private int CategoriaID;
    private String Titulo;
    private String Descripcion;

    public Categoria() {
    }

    public Categoria(int categoriaID, String titulo, String descripcion) {
        CategoriaID = categoriaID;
        Titulo = titulo;
        Descripcion = descripcion;
    }

    public int getCategoriaID() {
        return CategoriaID;
    }

    public void setCategoriaID(int categoriaID) {
        CategoriaID = categoriaID;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "CategoriaID=" + CategoriaID +
                ", Titulo='" + Titulo + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CategoriaID);
        dest.writeString(this.Titulo);
        dest.writeString(this.Descripcion);
    }

    protected Categoria(Parcel in) {
        this.CategoriaID = in.readInt();
        this.Titulo = in.readString();
        this.Descripcion = in.readString();
    }

    public static final Parcelable.Creator<Categoria> CREATOR = new Parcelable.Creator<Categoria>() {
        public Categoria createFromParcel(Parcel source) {
            return new Categoria(source);
        }

        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };
}
