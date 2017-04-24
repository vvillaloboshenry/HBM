package com.example.henry.hbm.Controles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.henry.hbm.Adaptador.AdaptadorMisReservas;
import com.example.henry.hbm.Daos.ReservaDAO;
import com.example.henry.hbm.Modelo.Categoria;
import com.example.henry.hbm.Modelo.Cliente;
import com.example.henry.hbm.Modelo.Habitacion;
import com.example.henry.hbm.Modelo.Reservas;
import com.example.henry.hbm.R;

import java.util.ArrayList;


public class MisReservasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView tvReservaEstado;
    private Toolbar toolbar;
    ListView listView;
    ReservaDAO reservaDAO;
    ArrayList<Reservas> arraydir;
    AdaptadorMisReservas adapter;
    Cursor c = null;
    Reservas reservas;
    Cliente cliente;
    Habitacion habitacion;
    Categoria categoria;
    String estado;
    int dni;
    private static final int REQUEST_MISRESERVASDETALLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);
        //Agrego la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarMisReservas);
        toolbar.setTitle("Mis Reservas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvReservaEstado = (TextView) findViewById(R.id.mis_reservas_tvReservasEstado);
        listView = (ListView) findViewById(R.id.lista);

        listView.setOnItemClickListener(this);
        arraydir = new ArrayList<>();
        reservaDAO = new ReservaDAO(this);
        reservaDAO.openDataBase();
        SharedPreferences prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);
        dni = Integer.parseInt(prefs.getString("usuario", ""));
        c = reservaDAO.consultarReservasActivas(dni);
        prepareData();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Reservas reservas = arraydir.get(position);
        Intent i = new Intent(this, MisReservasDetalleActivity.class);
        i.putExtra("Reservas", reservas);
        startActivityForResult(i, REQUEST_MISRESERVASDETALLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opc_mis_reservas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        arraydir = new ArrayList<>();
        reservaDAO = new ReservaDAO(this);
        reservaDAO.openDataBase();
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_recientes:
                estado = "Recientes";
                c = reservaDAO.consultarReservasActivas(dni);
                break;
            case R.id.action_activas:
                estado = "Activas";
                c = reservaDAO.consultarReservasActivas(dni);
                break;
            case R.id.action_finalizadas:
                estado = "Finalizadas";
                c = reservaDAO.consultarReservasFinalizadas(dni);
                break;
            case R.id.action_canceladas:
                estado = "Canceladas";
                c = reservaDAO.consultarReservasCanceladas(dni);
                break;
            case R.id.action_eliminadas:
                estado = "Eliminadas";
                c = reservaDAO.consultarReservasPapelera(dni);
                break;
            case R.id.action_all_disponibles:
                estado = "Todas las Disponibles";
                c = reservaDAO.consultarAllReservas(dni);
                break;
        }
        tvReservaEstado.setText(estado);
        prepareData();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Trae los datos deacuerdo al cursor que se definio al aviso de la clase
     */
    public void prepareData() {
        try {
            if (c != null) {
                while (c.moveToNext()) {
                    reservas = new Reservas();
                    cliente = new Cliente();
                    habitacion = new Habitacion();
                    categoria = new Categoria();
                    reservas.set_id(c.getInt(c.getColumnIndex("_id")));
                    reservas.setFecha(c.getString(c.getColumnIndex("Fecha")));
                    reservas.setFechaInicio(c.getString(c.getColumnIndex("FechaInicio")));
                    reservas.setFechaSalida(c.getString(c.getColumnIndex("FechaSalida")));
                    cliente.setDniCliente(c.getInt(c.getColumnIndex("DniCliente")));
                    cliente.setNombre(c.getString(c.getColumnIndex("Nombre")));
                    cliente.setApellido(c.getString(c.getColumnIndex("Apellido")));
                    habitacion.setHabitacionID(c.getInt(c.getColumnIndex("HabitacionID")));
                    categoria.setTitulo(c.getString(c.getColumnIndex("Titulo")));
                    categoria.setDescripcion(c.getString(c.getColumnIndex("Descripcion")));
                    habitacion.setCategoria(categoria);
                    habitacion.setPrecio(c.getDouble(c.getColumnIndex("Precio")));
                    habitacion.setNCuartos(c.getInt(c.getColumnIndex("NCuartos")));
                    habitacion.setNDormitorios(c.getInt(c.getColumnIndex("NDormitorios")));
                    habitacion.setNBanos(c.getInt(c.getColumnIndex("NBanos")));
                    reservas.setEstado(c.getInt(c.getColumnIndex("estado")));
                    reservas.setDropState(c.getInt(c.getColumnIndex("dropState")));
                    reservas.setCliente(cliente);
                    reservas.setHabitacion(habitacion);
                    reservas.setImg(Configuraciones.getRandomReservaDrawable());
                    arraydir.add(reservas);
                }
            }
        } catch (Exception e) {
    
        } finally {
            c.close();
            reservaDAO.closeDataBase();
        }
        adapter = new AdaptadorMisReservas(this, arraydir, R.layout.list_item_mis_reservas, R.id.item_list_mis_reservas_tvHabitacionId, R.id.item_list_mis_reservas_tvTitulo,
                R.id.item_list_mis_reservas_tvFechaInicio, R.id.item_list_mis_reservas_tvFechaFin, R.id.item_list_mis_reservas_tvPrecio, R.id.item_list_mis_reservas_img);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MISRESERVASDETALLE) {
            if (resultCode == 1) {
                estado = "Activas";
                tvReservaEstado.setText(estado);
                arraydir = new ArrayList<>();
                reservaDAO = new ReservaDAO(this);
                reservaDAO.openDataBase();
                c = reservaDAO.consultarReservasActivas(dni);
                prepareData();
            }
        }
    }

    // Metodo que se activa al momento de pulsar "atras" en esta actividad
    @Override
    public void onBackPressed() {
        this.finish();
    }
}


