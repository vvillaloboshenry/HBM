package com.example.henry.hbm.Controles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henry.hbm.Adaptador.AdaptadorGaleria;
import com.example.henry.hbm.Adaptador.BitmapUtils;
import com.example.henry.hbm.R;


public class GaleriaActivity extends Activity {
    ImageView imagenSeleccionada;
    Gallery gallery;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        imagenSeleccionada = (ImageView) findViewById(R.id.seleccionada);
        texto = (TextView) findViewById(R.id.galeria_tvInterior);

        final String[] descrp = {"Sala", "Baños", "Habitaciones", "Interiores", "Sala", "Interiores", "Habitaciones", "cuartocopiado"};
        final Integer[] imagenes = {R.drawable.foto1, R.drawable.foto2, R.drawable.foto3,
                R.drawable.foto4, R.drawable.foto1, R.drawable.foto2, R.drawable.foto3, R.drawable.foto3
        };

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new AdaptadorGaleria(this, imagenes));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 400, 0));
                texto.setText(descrp[position]);
            }

        });
    }
}
