package com.example.henry.hbm.Adaptador;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.henry.hbm.R;


/**
 * Created by Henry on 17/05/2016.
 */
public class AdaptadorGaleria extends BaseAdapter {
    Context context;
    Integer[] imagenes;
    int background;
    //TODO: Guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
    SparseArray<Bitmap> imagenesEscaladas = new SparseArray<Bitmap>(7);

    public AdaptadorGaleria(Context context, Integer[] imagenes) {
        super();
        this.imagenes = imagenes;
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.Gallery1);
        background = typedArray.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
        typedArray.recycle();
    }

    @Override
    public int getCount() {
        return imagenes.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imagen = new ImageView(context);
        //TODO: Reescalamos la imagen para evitar "java.lang.OutOfMemory" en el caso de imágenes de gran resolución
        if (imagenesEscaladas.get(position) == null) {
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context.getResources(), imagenes[position], 120, 0);
            imagenesEscaladas.put(position, bitmap);
        }
        imagen.setImageBitmap(imagenesEscaladas.get(position));
        //TODO: Se aplica el estilo
        imagen.setBackgroundResource(background);
        return imagen;
    }

}
