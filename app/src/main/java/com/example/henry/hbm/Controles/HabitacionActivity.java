package com.example.henry.hbm.Controles;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.henry.hbm.Adaptador.AdaptadorRoom;
import com.example.henry.hbm.Adaptador.JSONParser;
import com.example.henry.hbm.Daos.HabitacionDAO;
import com.example.henry.hbm.Modelo.Categoria;
import com.example.henry.hbm.Modelo.Habitacion;
import com.example.henry.hbm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HabitacionActivity extends AppCompatActivity {
    HabitacionDAO habitacionDAO;
    ArrayList<Habitacion> arraydir;
    private Toolbar toolbar;
    private static String url_all_habitaciones = "http://hotelbm.esy.es/hotelconnect/get_all_habitaciones.php";
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    // identificadores de los JSON
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_HABITACIONES = "habitacionArray";
    private static final String TAG_ID = "id";
    private static final String TAG_CATEGORIAID = "categoriaid";
    private static final String TAG_TITULO = "titulo";
    private static final String TAG_DESCRIPCION = "descripcion";
    private static final String TAG_PRECIO = "precio";
    private static final String TAG_NCUARTOS = "numcuartos";
    private static final String TAG_NDORMITORIOS = "numdormitorios";
    private static final String TAG_NBANOS = "numbanos";
    public Habitacion habitacion;
    public Categoria categoria;
    ListView lista;
    // habitaciones JSONArray - Objeto que almacenara los datos del JSON
    JSONArray hab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion);
        toolbar = (Toolbar) findViewById(R.id.toolbarHabitacion);
        toolbar.setTitle("Habitaciones - HBM");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lista = (ListView) findViewById(R.id.habitacion_Lista);
    }

    // Metodo que se ejecuta por el onResume de la actividad; trae constantemente la lista cuando la actividad llega a su ciclo de vida onResume
    @Override
    public void onResume() {
        super.onResume();
        prepareData();
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

    /**
     * @return Metodo que retorna un boolean true si hubiera internet, en caso contrario false.
     */
    public boolean validaInternet() {
        boolean conexion = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            conexion = true;
            return conexion;
        }
        return conexion;
    }

    /**
     * Metodo que se encarga de traer la data a la Actividad; Si hay internet trae data del HOST sino de la BD Local SQLite
     */
    private void prepareData() {
        if (validaInternet() == true) {
            arraydir = new ArrayList<>();
            new LoadAllhab().execute();
        } else {
            arraydir = new ArrayList<>();
            habitacionDAO = new HabitacionDAO(this);
            habitacionDAO.openDataBase();

            Cursor c = habitacionDAO.leer();
            try {
                if (c != null) {
                    while (c.moveToNext()) {
                        habitacion = new Habitacion();
                        categoria = new Categoria();
                        habitacion.setHabitacionID(c.getInt(c.getColumnIndex("HabitacionID")));
                        categoria.setTitulo(c.getString(c.getColumnIndex("Titulo")));
                        categoria.setDescripcion(c.getString(c.getColumnIndex("Descripcion")));
                        habitacion.setCategoria(categoria);
                        habitacion.setPrecio(c.getDouble(c.getColumnIndex("Precio")));
                        habitacion.setNCuartos(c.getInt(c.getColumnIndex("NCuartos")));
                        habitacion.setNDormitorios(c.getInt(c.getColumnIndex("NDormitorios")));
                        habitacion.setNBanos(c.getInt(c.getColumnIndex("NBanos")));
                        habitacion.setImagen(Configuraciones.getRandomReservaDrawable());
                        arraydir.add(habitacion);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.close();
                habitacionDAO.closeDataBase();
                AdaptadorRoom adapter = new AdaptadorRoom(this, arraydir, R.layout.list_item_habitaciones, R.id.item_list_habitaciones_tvTitulo, R.id.item_list_habitaciones_tvDescripcion, R.id.item_list_habitaciones_imageView);
                lista.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

    }

    // AsynTask tarea sincronizada
    public class LoadAllhab extends AsyncTask<String, String, String> {
        // Ejecuta un hilo donde mostrara un progressDialog que empezara para cargar los datos
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HabitacionActivity.this);
            pDialog.setMessage("Cargando habitaciones. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todas las habitaciones, este metodo es llamado cuando termina el onPreExecute
         */
        protected String doInBackground(String... args) {
            List params = new ArrayList();
            // Consigue o llama la cadena JSON desde una URL
            JSONObject json = jParser.makeHttpRequest(url_all_habitaciones, "GET", params);
            // Comprueba si el JSON trae datos mostrandolo en consola
            Log.d("msj habitaciones: : ", json.toString());
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // Trae los datos del array JSON
                    //JSONArray hab
                    hab = json.getJSONArray(TAG_HABITACIONES);
                    //Se lanza el bucle para traer dato por dato
                    for (int i = 0; i < hab.length(); i++) {
                        JSONObject c = hab.getJSONObject(i);
                        habitacion = new Habitacion();
                        categoria = new Categoria();
                        habitacion.setHabitacionID(Integer.parseInt(c.getString(TAG_ID)));
                        categoria.setCategoriaID(Integer.parseInt(c.getString(TAG_CATEGORIAID)));
                        categoria.setTitulo(c.getString(TAG_TITULO));
                        categoria.setDescripcion(c.getString(TAG_DESCRIPCION));
                        habitacion.setCategoria(categoria);
                        habitacion.setPrecio(Double.parseDouble(c.getString(TAG_PRECIO)));
                        habitacion.setNCuartos(Integer.parseInt(c.getString(TAG_NCUARTOS)));
                        habitacion.setNDormitorios(Integer.parseInt(c.getString(TAG_NDORMITORIOS)));
                        habitacion.setNBanos(Integer.parseInt(c.getString(TAG_NBANOS)));
                        habitacion.setImagen(Configuraciones.getRandomReservaDrawable());
                        habitacionDAO = new HabitacionDAO(HabitacionActivity.this);
                        habitacionDAO.openDataBase();
                        habitacionDAO.crearHabitacion(Integer.parseInt(c.getString(TAG_ID)), Double.parseDouble(c.getString(TAG_PRECIO)), Integer.parseInt(c.getString(TAG_NCUARTOS)),
                                Integer.parseInt(c.getString(TAG_NDORMITORIOS)), Integer.parseInt(c.getString(TAG_NBANOS)), Integer.parseInt(c.getString(TAG_CATEGORIAID)));
                        arraydir.add(habitacion);
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(HabitacionActivity.this, "Fallo al traer datos", Toast.LENGTH_SHORT).show();
            } finally {
                habitacionDAO.closeDataBase();
            }
            return null;
        }

        // Metodo que se ejecuta al momento de terminar el metodo doInBackground de la AsynTask
        protected void onPostExecute(String file_url) {
            // Damos por culminado el pDialog luego de recivir todas las habitaciones puestas en el adapter que retorna un view que se le asigna a la lista
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    AdaptadorRoom adapter = new AdaptadorRoom(HabitacionActivity.this, arraydir, R.layout.list_item_habitaciones, R.id.item_list_habitaciones_tvTitulo, R.id.item_list_habitaciones_tvDescripcion, R.id.item_list_habitaciones_imageView);
                    lista.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    // Metodo que se activa al momento de pulsar "atras" en esta actividad
    @Override
    public void onBackPressed() {
        this.finish();
    }
}