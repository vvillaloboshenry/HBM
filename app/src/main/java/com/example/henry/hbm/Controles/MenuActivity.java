package com.example.henry.hbm.Controles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.henry.hbm.R;


public class MenuActivity extends Activity implements View.OnClickListener {
    Intent intent;
    int[] menu_slider = {R.drawable.portada1, R.drawable.portada2, R.drawable.portada3, R.drawable.portada4};
    int[] menu_drawableButton = {R.drawable.btnlleno, R.drawable.btnvacio};
    Button b1, b2, b3, b4, menu_btnInfo, menu_btnCuartos, menu_btnGaleria, menu_btnUbicacion, menu_btnServicios, btnPrecios;
    Button[] botonId = {b1, b2, b3, b4};
    boolean estado = false;
    Runnable runnable;
    ImageView foto;
    int total, i = 0;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        foto = (ImageView) findViewById(R.id.menu_imgSlider);
        botonId[0] = (Button) findViewById(R.id.menu_btn1);
        botonId[1] = (Button) findViewById(R.id.menu_btn2);
        botonId[2] = (Button) findViewById(R.id.menu_btn3);
        botonId[3] = (Button) findViewById(R.id.menu_btn4);
        menu_btnInfo = (Button) findViewById(R.id.menu_btnInfo);
        menu_btnCuartos = (Button) findViewById(R.id.menu_btnCuartos);
        menu_btnGaleria = (Button) findViewById(R.id.menu_btnGaleria);
        menu_btnServicios = (Button) findViewById(R.id.menu_btnServicios);
        menu_btnUbicacion = (Button) findViewById(R.id.menu_btnUbicacion);
        btnPrecios = (Button) findViewById(R.id.menu_btnMisReservas);

        botonId[0].setOnClickListener(this);
        botonId[1].setOnClickListener(this);
        botonId[2].setOnClickListener(this);
        botonId[3].setOnClickListener(this);

        menu_btnInfo.setOnClickListener(this);
        menu_btnCuartos.setOnClickListener(this);
        menu_btnGaleria.setOnClickListener(this);
        menu_btnUbicacion.setOnClickListener(this);
        menu_btnServicios.setOnClickListener(this);
        btnPrecios.setOnClickListener(this);
        total = menu_slider.length;

    }

    @Override
    protected void onResume() {
        super.onResume();
        estado = true;
        playSliderAnimation(estado);
    }

    @Override
    protected void onPause() {
        super.onPause();
        estado = false;
        playSliderAnimation(estado);
    }

    /**
     * Este metodo se ejecutara cuando es llamado desde el onCreate "playSliderAnimation"
     * Realizara una tarea cada 2 segundos de espera; esta tarea consistira  en cambiar la imagen del slider
     * y pintar el boton correspondiente deacuerdo a la imagen mostrada
     */
    public void playSliderAnimation(boolean valor) {
        if (valor == true) {
            runnable = new Runnable() {
                public void run() {
                    if (i == total) {
                        i = 0;
                    }
                    foto.setBackgroundResource(menu_slider[i]);
                    pintarBoton(i);
                    i++;
                    handler.postDelayed(runnable, 2000);
                }
            };
            handler.post(runnable);
        } else {
            if (i == total) {
                i = 0;
            }
            foto.setBackgroundResource(menu_slider[i]);
            pintarBoton(i);
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * @param v Este metodo recive como parametro un objeto del tipo View "v" que servira para  obtener
     *          los id de los recursos con eventos OnClick en este contexto para que luego por cada case en el
     *          metodo se realize una accion correspondiente al recurso pulsado
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_btnServicios:
                break;
            case R.id.menu_btn1:
                i = 0;
                pintarBoton(i);
                break;
            case R.id.menu_btn2:
                i = 1;
                pintarBoton(i);
                break;
            case R.id.menu_btn3:
                i = 2;
                pintarBoton(i);
                break;
            case R.id.menu_btn4:
                i = 3;
                pintarBoton(i);
                break;
            case R.id.menu_btnInfo:
                intent = new Intent(this, InformacionActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_btnCuartos:
                intent = new Intent(this, HabitacionActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_btnGaleria:
                intent = new Intent(this, GaleriaActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_btnUbicacion:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=-6.793828491790145,-79.88852815178075");
                intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.menu_btnMisReservas:
                intent = new Intent(this, MisReservasActivity.class);
                startActivity(intent);
                break;
        }
        if (i == total) {
            i = 0;
        }
        foto.setBackgroundResource(menu_slider[i]);
        pintarBoton(i);
    }

    /**
     * @param index Este metodo recive como parametro un valor de tipo entero llamado index que no es mas que
     *              un parametro que tiene como objetivo identificar el boton que se debe pintar al ser seleccionado
     */
    public void pintarBoton(int index) {
        for (int i = 0; i < 4; i++) {
            if (i == index) {
                botonId[i].setBackgroundResource(menu_drawableButton[0]);
            } else {
                botonId[i].setBackgroundResource(menu_drawableButton[1]);
            }
        }
    }

    // Metodo que se activa al momento de pulsar "atras" en esta actividad
    @Override
    public void onBackPressed() {
        this.finish();
    }
}
