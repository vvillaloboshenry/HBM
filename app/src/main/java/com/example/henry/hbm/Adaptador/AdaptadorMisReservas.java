package com.example.henry.hbm.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henry.hbm.Modelo.Reservas;

import java.util.ArrayList;

/**
 * Created by Henry on 17/05/2016.
 */
public class AdaptadorMisReservas extends BaseAdapter {
    private final Context context;
    private ArrayList<Reservas> items;
    private int resource;
    private int resourceIdHabitacion, resourceIdTitulo, resourceIdFechaInicio, resourceIdFechaFin, resourceIdPrecio, resourceIdImg;

    public AdaptadorMisReservas(Context context, ArrayList<Reservas> items, int resource, int resourceIdHabitacion, int resourceIdTitulo, int resourceIdFechaInicio, int resourceIdFechaFin, int resourceIdPrecio, int resourceIdImg) {
        this.context = context;
        this.items = items;
        this.resource = resource;
        this.resourceIdHabitacion = resourceIdHabitacion;
        this.resourceIdTitulo = resourceIdTitulo;
        this.resourceIdFechaInicio = resourceIdFechaInicio;
        this.resourceIdFechaFin = resourceIdFechaFin;
        this.resourceIdPrecio = resourceIdPrecio;
        this.resourceIdImg = resourceIdImg;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).get_id();
    }

    @Override
    public View getView(final int position, final View vd, final ViewGroup parent) {
        TextView tv_Id, tvTitulo, tvFechaInicio, tvFechaSalida, tvPrecio;
        ImageView img;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View inflate = inflater.inflate(resource, null, true);
        Reservas reservas = items.get(position);
        tv_Id = (TextView) inflate.findViewById(resourceIdHabitacion);
        tv_Id.setText(reservas.get_id() + "");
        tvTitulo = (TextView) inflate.findViewById(resourceIdTitulo);
        tvTitulo.setText(reservas.getHabitacion().getCategoria().getTitulo());
        tvFechaInicio = (TextView) inflate.findViewById(resourceIdFechaInicio);
        tvFechaInicio.setText(reservas.getFechaInicio());
        tvFechaSalida = (TextView) inflate.findViewById(resourceIdFechaFin);
        tvFechaSalida.setText(reservas.getFechaSalida());
        tvPrecio = (TextView) inflate.findViewById(resourceIdPrecio);
        tvPrecio.setText(reservas.getHabitacion().getPrecio() + "");
        img = (ImageView) inflate.findViewById(resourceIdImg);
        img.setBackgroundResource(reservas.getImg());
        return inflate;
    }

}