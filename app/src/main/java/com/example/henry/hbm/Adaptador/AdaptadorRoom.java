package com.example.henry.hbm.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henry.hbm.Modelo.Habitacion;
import com.example.henry.hbm.Controles.HabitacionDetalleActivity;
import com.example.henry.hbm.R;

import java.util.ArrayList;

/**
 * Created by Henry on 17/05/2016.
 */
public class AdaptadorRoom extends BaseAdapter {
    private final Context context;
    private ArrayList<Habitacion> items;
    private int resource;
    private int resourceIdTitle, resourceIdDescription, resourceIdImg;

    public AdaptadorRoom(Context context, ArrayList<Habitacion> arraydir, int resource, int resourceIdTitle, int resourceIdDescription, int resourceIdImg) {
        this.context = context;
        this.items = arraydir;
        this.resource = resource;
        this.resourceIdTitle = resourceIdTitle;
        this.resourceIdDescription = resourceIdDescription;
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
        return items.get(position).getHabitacionID();
    }

    @Override
    public View getView(final int position, final View vd, final ViewGroup parent) {
        TextView title, descrp;
        ImageView img;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View inflate = inflater.inflate(resource, null, true);
        final Habitacion habitacion = items.get(position);
        title = (TextView) inflate.findViewById(resourceIdTitle);
        title.setText("" + habitacion.getCategoria().getTitulo());
        descrp = (TextView) inflate.findViewById(resourceIdDescription);
        descrp.setText("" + habitacion.getCategoria().getDescripcion());
        img = (ImageView) inflate.findViewById(resourceIdImg);
        img.setBackgroundResource(habitacion.getImagen());

        Button btn = (Button) inflate.findViewById(R.id.item_list_habitaciones_btnDetalles);
        Button.OnClickListener btnOnclick = new Button.OnClickListener() {
            public void onClick(View v) {
                // TODO: Se envia el intent con la referencia del Objeto Habitacion "habitacion"
                Intent mainDetalle = new Intent(parent.getContext(), HabitacionDetalleActivity.class);
                Activity activity = (Activity) context;
                mainDetalle.putExtra("Habitacion", habitacion);
                activity.startActivity(mainDetalle);
            }
        };
        btn.setOnClickListener(btnOnclick);
        // retorna un View
        return inflate;
    }

}