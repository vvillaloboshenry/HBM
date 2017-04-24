package com.example.henry.hbm.Daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdministrarDB extends SQLiteOpenHelper {

    public AdministrarDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Cliente(DniCliente integer PRIMARY KEY NOT NULL, Nombre text NOT NULL, Apellido text NOT NULL,Clave text NOT NULL,dropState integer NOT NULL DEFAULT 1)");
        db.execSQL("create table Categoria(_id integer PRIMARY KEY AUTOINCREMENT, Titulo text NOT NULL,Descripcion text NOT NULL)");
        db.execSQL("create table Habitacion(HabitacionID integer PRIMARY KEY NOT NULL, Precio real NOT NULL,NCuartos integer NOT NULL,NDormitorios integer NOT NULL, NBanos integer NOT NULL,Imagenurl text, CategoriaID integer,estado integer NOT NULL DEFAULT 1,dropState integer NOT NULL DEFAULT 1, FOREIGN KEY(CategoriaID) REFERENCES Categoria)");
        db.execSQL("create table ReservaHabitacion(_id integer PRIMARY KEY AUTOINCREMENT,Fecha numeric NOT NULL, FechaInicio numeric NOT NULL, FechaSalida numeric NOT NULL,DniCliente integer,HabitacionID integer,estado integer NOT NULL DEFAULT 1,dropState integer NOT NULL DEFAULT 1,FOREIGN KEY(DniCliente) REFERENCES Cliente,FOREIGN KEY(HabitacionID) REFERENCES Habitacion)");
        db.execSQL("create table Gasto(_id integer PRIMARY KEY AUTOINCREMENT, ReservaID integer,Fecha numeric NOT NULL,Cantidad integer NOT NULL,Precio real NOT NULL,estado integer NOT NULL DEFAULT 1, dropState integer NOT NULL DEFAULT 1,FOREIGN KEY(ReservaID) REFERENCES ReservaHabitacion)");

        /**
         * Poblamiento de Tabla Categoria
         */
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Habitacion Cute','Bonita Habitacion de amplia sala')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Habitacion Vip','Habitacion Vip 3 baños')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Habitacion 3D Vip','Habitacion de Sala Vip 3D con un dormitorio')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Lo mejor de lo Mejor','Habitacion Sala Vip con buena vista tranquilo')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Habitacion for Star','Excelente Habitacion de 4 estrellas con amplia sala, 3 dormitorios, habitacion con buena vista; excelente para pasar las noches')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Habitacion GOLD','Habitacion Vip 5 cuartos, sala amplia, 4 dormitorios')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Habitacion Cama','Habitacion Vip + sala personal con 1 dormitorio ')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Camas Dobles','Habitacion para familia pequeña')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Camas Triples','Habitacion para toda la familia y a la comodida de su bolsillo')");
        db.execSQL("insert into Categoria(Titulo,Descripcion) values ('Cama Nupsial','Habitacion excelente para la luna de miel')");

        /**
         * Poblamiento de Clientes
         */
        db.execSQL("insert into Cliente(DniCliente,Nombre,Apellido,Clave) values (45454545,'Henry','Vasquez','holamundo')");
        db.execSQL("insert into Cliente(DniCliente,Nombre,Apellido,Clave) values (42424242,'Joel','Vasquez','hello')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}


