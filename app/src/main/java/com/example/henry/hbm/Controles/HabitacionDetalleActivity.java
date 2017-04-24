package com.example.henry.hbm.Controles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henry.hbm.Modelo.Habitacion;
import com.example.henry.hbm.R;

public class HabitacionDetalleActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Codigo, Titulo, Cuerpo, Precio, Baños, Dormitorios, Cuartos;
    Button btnIrReservar;
    Toolbar toolbar;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion_detalle);
        toolbar = (Toolbar) findViewById(R.id.toolbarHabitacionDetalle);
        toolbar.setTitle("Detalles de Habitaciones");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Codigo = (TextView) findViewById(R.id.habitacion_detalle_tvHabitacionCodigo);
        Titulo = (TextView) findViewById(R.id.habitacion_detalle_tvCategoriaTitulo);
        Cuerpo = (TextView) findViewById(R.id.habitacion_detalle_tvCategoriaDescripcion);
        Precio = (TextView) findViewById(R.id.habitacion_detalle_tvHabitacionPrecio);
        Baños = (TextView) findViewById(R.id.habitacion_detalle_tvHabitacionBanos);
        Dormitorios = (TextView) findViewById(R.id.habitacion_detalle_tvHabitacionDormitorios);
        Cuartos = (TextView) findViewById(R.id.habitacion_detalle_tvHabitacionCuarto);
        btnIrReservar = (Button) findViewById(R.id.habitacion_detalle_btnIrReservar);
        img = (ImageView) findViewById(R.id.detalle_imgDetalle);
        btnIrReservar.setOnClickListener(this);

        Habitacion hab = getIntent().getParcelableExtra("Habitacion");
        Codigo.setText(hab.getHabitacionID() + "");
        Titulo.setText(hab.getCategoria().getTitulo());
        Cuerpo.setText(hab.getCategoria().getDescripcion());
        Precio.setText(hab.getPrecio() + "");
        Baños.setText(hab.getNBanos() + "");
        Dormitorios.setText(hab.getNDormitorios() + "");
        Cuartos.setText(hab.getNCuartos() + "");
        img.setBackgroundResource(hab.getImagen());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Metodo que da inicio a la actividad ReservarActivity.class cuando se escucha que se a pulsado el boton
    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, ReservarActivity.class);
        i.putExtra("cod", Codigo.getText());
        i.putExtra("precio", Precio.getText() + "");
        startActivity(i);
        this.finish();
    }

    // Metodo que se activa al momento de pulsar "atras" en esta actividad
    @Override
    public void onBackPressed() {
        this.finish();
    }
}
