package com.example.henry.hbm.Controles;

import android.app.Activity;

import com.example.henry.hbm.R;

import java.util.Random;

/**
 * Created by Vasquez on 25/06/2016.
 * Clase usada para algunas configuraciones en el proyecto como retornar los dias, meses o imagenes random para las habitaciones
 */

public class Configuraciones extends Activity {
    
    /**
     * @return metodo que retorna valores random tipo int de las ID de las imagenes contenidas en el paquete drawable
     */
    public static int getRandomReservaDrawable() {
        Random rnd = new Random();
        switch (rnd.nextInt(8)) {
            default:
            case 0:
                return R.drawable.foto1;
            case 1:
                return R.drawable.foto2;
            case 2:
                return R.drawable.foto3;
            case 3:
                return R.drawable.foto4;
            case 4:
                return R.drawable.portada1;
            case 5:
                return R.drawable.portada2;
            case 6:
                return R.drawable.portada3;
            case 7:
                return R.drawable.portada4;
        }
    }

    /**
     * @param mes Parametro tipo int que se usa para comparar cada caso y devolver un valor tipo String
     * @return Devuelve el nombre del mes
     */
    public static String nombreMes(int mes) {
        String nombre = "";
        switch (mes) {
            case 1:
                nombre = "Enero";
                break;
            case 2:
                nombre = "Febrero";
                break;
            case 3:
                nombre = "Marzo";
                break;
            case 4:
                nombre = "Abril";
                break;
            case 5:
                nombre = "Mayo";
                break;
            case 6:
                nombre = "Junio";
                break;
            case 7:
                nombre = "Julio";
                break;
            case 8:
                nombre = "Agosto";
                break;
            case 9:
                nombre = "Septiembre";
                break;
            case 10:
                nombre = "Octubre";
                break;
            case 11:
                nombre = "Noviembre";
                break;
            case 12:
                nombre = "Diciembre";
                break;
        }
        return nombre;
    }

    /**
     * @param dia Parametro tipo int que se usa para comparar para cada caso y devolver un valor String
     * @return Devuelve el nombre del dia
     */
    public static String nombreDia(int dia) {
        String nombre = "";
        switch (dia) {
            case 1:
                nombre = "Domingo";
                break;
            case 2:
                nombre = "Lunes";
                break;
            case 3:
                nombre = "Martes";
                break;
            case 4:
                nombre = "Miercoles";
                break;
            case 5:
                nombre = "Jueves";
                break;
            case 6:
                nombre = "Viernes";
                break;
            case 7:
                nombre = "Sabado";
                break;
        }
        return nombre;
    }
}




