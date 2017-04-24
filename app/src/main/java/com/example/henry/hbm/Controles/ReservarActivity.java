package com.example.henry.hbm.Controles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.hbm.Daos.GastoDAO;
import com.example.henry.hbm.Daos.ReservaDAO;
import com.example.henry.hbm.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReservarActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener {
    int[] slider = {R.drawable.sliderreserva1, R.drawable.sliderreserva2, R.drawable.sliderreserva3, R.drawable.sliderreserva4, R.drawable.sliderreserva5};
    int[] drawableButton = {R.drawable.slidereserva1, R.drawable.slidereserva2};
    Toolbar toolbar;
    ReservaDAO reservaDAO;
    GastoDAO gastoDAO;
    String dateEntrada, dateSalida;
    int total;
    int i = 0;
    ImageButton b1, b2, b3, b4, b5;
    TextView tvIdHabitacion, tvDniCliente, tvNombreCliente, tvDiaEntrada, tvDiaSalida, tvMesEntrada, tvMesSalida, tvNombreDiaEntrada, tvNombreDiaSalida, tvYearEntrada, tvYearSalida, tvMuestraEntrada, tvMuestraSalida;
    Button btnRegistrar;
    LinearLayout rvfechaEntrada, Cuerpo, Cabecera;
    RelativeLayout rvfechaSalida, sliderGeneral;
    boolean estadoFecha;
    Runnable runnable;
    final Handler handler = new Handler();
    ImageButton[] botonId = {b1, b2, b3, b4, b5};
    Date fechaEntradaF, fechaSalidaF;
    String precioHabitacion;
    boolean estado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        toolbar = (Toolbar) findViewById(R.id.toolbarReserva);
        toolbar.setTitle("Reservas - HBM");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvIdHabitacion = (TextView) findViewById(R.id.reserva_tvHabitacionId);
        tvDniCliente = (TextView) findViewById(R.id.reserva_tvDniCliente);
        tvNombreCliente = (TextView) findViewById(R.id.reserva_tvNombreCliente);
        tvDiaEntrada = (TextView) findViewById(R.id.reserva_tvDiaEntrada);
        tvDiaSalida = (TextView) findViewById(R.id.reserva_tvDiaSalida);
        tvMesEntrada = (TextView) findViewById(R.id.reserva_tvMesEntrada);
        tvMesSalida = (TextView) findViewById(R.id.reserva_tvMesSalida);
        tvNombreDiaEntrada = (TextView) findViewById(R.id.reserva_tvNombreDiaEntrada);
        tvNombreDiaSalida = (TextView) findViewById(R.id.reserva_tvNombreDiaSalida);
        //tvYearEntrada = (TextView) findViewById(R.id.reserva_tvYearEntrada);
        tvYearSalida = (TextView) findViewById(R.id.reserva_tvYearSalida);
        botonId[0] = (ImageButton) findViewById(R.id.btnsliderreserva0);
        botonId[1] = (ImageButton) findViewById(R.id.btnsliderreserva1);
        botonId[2] = (ImageButton) findViewById(R.id.btnsliderreserva2);
        botonId[3] = (ImageButton) findViewById(R.id.btnsliderreserva3);
        botonId[4] = (ImageButton) findViewById(R.id.btnsliderreserva4);
        btnRegistrar = (Button) findViewById(R.id.reserva_btnReservar);
        rvfechaEntrada = (LinearLayout) findViewById(R.id.reserva_relativefechaEntrada);
        Cuerpo = (LinearLayout) findViewById(R.id.Cuerpo);
        Cabecera = (LinearLayout) findViewById(R.id.Cabecera);
        rvfechaSalida = (RelativeLayout) findViewById(R.id.reserva_relativefechaSalida);
        tvMuestraEntrada = (TextView) findViewById(R.id.reserva_tvMuestraEntrada);
        tvMuestraSalida = (TextView) findViewById(R.id.reserva_tvMuestraSalida);
        sliderGeneral = (RelativeLayout) findViewById(R.id.reserva_sliderGeneral);

        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String usuario = prefs.getString("usuario", "");
        String nombre = prefs.getString("nombre", "");
        String codigoHabitacion = getIntent().getStringExtra("cod");
        precioHabitacion = getIntent().getStringExtra("precio");
        tvDniCliente.setText(usuario);
        tvNombreCliente.setText(nombre);
        tvIdHabitacion.setText(codigoHabitacion);

        rvfechaEntrada.setOnClickListener(this);
        rvfechaSalida.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        total = slider.length;
        verificarFecha();

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

    public void playSliderAnimation(boolean valor) {
        if (valor == true) {
            runnable = new Runnable() {
                public void run() {
                    if (i == total) {
                        i = 0;
                    }
                    sliderGeneral.setBackgroundResource(slider[i]);
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
            sliderGeneral.setBackgroundResource(slider[i]);
            pintarBoton(i);
            handler.removeCallbacks(runnable);
        }
    }

    public void pintarBoton(int index) {
        for (int i = 0; i < 5; i++) {
            if (i == index) {
                botonId[i].setImageResource(drawableButton[0]);
            } else {
                botonId[i].setImageResource(drawableButton[1]);
            }
        }
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

    public long sacarDiferencia(Date fechaFinal, Date FechaInicio) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al dÃ­a
        long diferencia = (fechaFinal.getTime() - FechaInicio.getTime()) / MILLSECS_PER_DAY;
        return diferencia;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reserva_btnReservar:
                String habitacionId = tvIdHabitacion.getText().toString();
                String dni = tvDniCliente.getText().toString();
                Calendar c = Calendar.getInstance();
                int anio = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                String fechaSistema = (dia < 10 ? "0" + dia : dia) + "/" +
                        (mes + 1 < 10 ? "0" + (mes + 1) : mes + 1) + "/" +
                        anio;
                if (habitacionId != null && dni != null && dateEntrada != null && dateSalida != null && fechaSistema != null) {
                    try {
                        String numDias = sacarDiferencia(fechaSalidaF, fechaEntradaF) + "";
                        reservaDAO = new ReservaDAO(this);
                        reservaDAO.openDataBase();
                        int cod = reservaDAO.insertar(fechaSistema, dateEntrada, dateSalida, dni, habitacionId);
                        if (cod > 0) {
                            gastoDAO = new GastoDAO(this);
                            gastoDAO.openDataBase();
                            long rpt = gastoDAO.crearGasto(cod, fechaSistema, Integer.parseInt(numDias), Double.parseDouble(precioHabitacion));
                            if (rpt != -1) {
                                Toast.makeText(this, "Se reservo correctamente, Gracias.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "No se pudo reservar, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "No se pudo reservar, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        reservaDAO.closeDataBase();
                        gastoDAO.closeDataBase();
                        dateEntrada = null;
                        dateSalida = null;
                        this.finish();
                    }
                } else {
                    Toast.makeText(this, "Primero rellene todos los campos.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.reserva_relativefechaEntrada:
                estadoFecha = true;
                scheduleTestDrive(v);
                break;
            case R.id.reserva_relativefechaSalida:
                estadoFecha = false;
                scheduleTestDrive(v);
                break;
        }
    }

    private int year, month, day, nameday;

    public void scheduleTestDrive(View view) {
        initDateTimeData();
        Calendar cDefault = Calendar.getInstance();
        if (estadoFecha == true) {
            cDefault.set(year, month, day);
        } else {
            cDefault.set(year, month, day + 1);
        }
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
        datePickerDialog.setThemeDark(true);
    }

    private void initDateTimeData() {
        if (year == 0) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        verificarFecha();
    }

    public void verificarFecha() {
        if (dateEntrada == null) {
            Cuerpo.setVisibility(View.GONE);
           // Cuerpo.setVisibility(View.INVISIBLE);


            /*
            tvMuestraEntrada.setVisibility(View.VISIBLE);
            tvDiaEntrada.setVisibility(View.INVISIBLE);
            tvMesEntrada.setVisibility(View.INVISIBLE);
            tvYearEntrada.setVisibility(View.INVISIBLE);
            tvNombreDiaEntrada.setVisibility(View.INVISIBLE);
            tvMuestraEntrada.setText("Sin fechas");*/
        }
        if (dateSalida == null) {
            tvMuestraSalida.setVisibility(View.VISIBLE);
            tvDiaSalida.setVisibility(View.INVISIBLE);
            tvMesSalida.setVisibility(View.INVISIBLE);
            tvYearSalida.setVisibility(View.INVISIBLE);
            tvNombreDiaSalida.setVisibility(View.INVISIBLE);
            tvMuestraSalida.setText("Sin fechas");

        }
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
        Calendar tdefault = Calendar.getInstance();

        year = i;
        month = i1;
        day = i2;
        tdefault.set(year, month, day);
        nameday = tdefault.get(Calendar.DAY_OF_WEEK);
        if (estadoFecha == true) {
            Calendar calendar = new GregorianCalendar(year, month - 1, day);
            fechaEntradaF = new java.sql.Date(calendar.getTimeInMillis());
            /*tvDiaEntrada.setVisibility(View.VISIBLE);
            tvMesEntrada.setVisibility(View.VISIBLE);
            tvYearEntrada.setVisibility(View.VISIBLE);
            tvNombreDiaEntrada.setVisibility(View.VISIBLE);
            tvMuestraEntrada.setVisibility(View.INVISIBLE);
            tvDiaEntrada.setText((day < 10 ? "0" + day : day) + "");
            String mes = (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "";
            tvMesEntrada.setText(Configuraciones.nombreMes(Integer.parseInt(mes)) + "");
            tvYearEntrada.setText(year + "");
            tvNombreDiaEntrada.setText(Configuraciones.nombreDia(nameday));**/
            Cabecera.setVisibility(View.GONE);
            //Cabecera.setVisibility(View.INVISIBLE);
            Cuerpo.setVisibility(View.VISIBLE);

            tvDiaEntrada.setText((day < 10 ? "0" + day : day) + "");
            String mes = (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "";
            tvMesEntrada.setText(Configuraciones.nombreMes(Integer.parseInt(mes)) + "  " + year);
            tvNombreDiaEntrada.setText(Configuraciones.nombreDia(nameday));

            dateEntrada = (day < 10 ? "0" + day : day) + "/" +
                    (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "/" +
                    year;
        }
        if (estadoFecha == false) {
            Calendar calendar = new GregorianCalendar(year, month - 1, day);
            fechaSalidaF = new java.sql.Date(calendar.getTimeInMillis());
            tvDiaSalida.setVisibility(View.VISIBLE);
            tvMesSalida.setVisibility(View.VISIBLE);
            tvYearSalida.setVisibility(View.VISIBLE);
            tvNombreDiaSalida.setVisibility(View.VISIBLE);
            tvMuestraSalida.setVisibility(View.INVISIBLE);
            tvDiaSalida.setText((day < 10 ? "0" + day : day) + "");
            String mes = (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "";
            tvMesSalida.setText(Configuraciones.nombreMes(Integer.parseInt(mes)) + "");
            tvYearSalida.setText(year + "");
            tvNombreDiaSalida.setText(Configuraciones.nombreDia(nameday));
            dateSalida = (day < 10 ? "0" + day : day) + "/" +
                    (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "/" +
                    year;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
