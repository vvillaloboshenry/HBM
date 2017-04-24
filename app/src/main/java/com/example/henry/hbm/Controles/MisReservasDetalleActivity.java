package com.example.henry.hbm.Controles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.hbm.Daos.GastoDAO;
import com.example.henry.hbm.Daos.ReservaDAO;
import com.example.henry.hbm.Modelo.Reservas;
import com.example.henry.hbm.R;

public class MisReservasDetalleActivity extends AppCompatActivity {
    private static final int Activos = 1;
    ImageView img;
    TextView tvHabitacionPrecio, tvCantidadDias, tvSubTotal, tvTotal, tvHabitacionID,
            tvCategoriaTitulo, tvCategoriaDescripcion, tvHabitacionNCuartos,
            tvHabitacionNDormitorios, tvHabitacionNBanos, tvReservaId,
            tvReservaFEntrada, tvReservaFSalida, tvClienteNombre,
            tvClienteDni;
    ReservaDAO reservaDAO;
    GastoDAO gastoDAO;
    Toolbar toolbar;
    String numDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas_detalle);
        toolbar = (Toolbar) findViewById(R.id.toolbarMisReservasDetalle);
        toolbar.setTitle("Detalles Mis Reservas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        img = (ImageView) findViewById(R.id.image_paralax);

        tvHabitacionID = (TextView) findViewById(R.id.mis_reservas_detalle_tvHabitacionID);
        tvCategoriaTitulo = (TextView) findViewById(R.id.mis_reservas_detalle_tvCategoriaTitulo);
        tvCategoriaDescripcion = (TextView) findViewById(R.id.mis_reservas_detalle_tvCategoriaDescripcion);
        tvHabitacionNCuartos = (TextView) findViewById(R.id.mis_reservas_detalle_tvHabitacionNCuartos);
        tvHabitacionNDormitorios = (TextView) findViewById(R.id.mis_reservas_detalle_tvHabitacionNDormitorios);
        tvHabitacionNBanos = (TextView) findViewById(R.id.mis_reservas_detalle_tvHabitacionNBanos);
        tvHabitacionPrecio = (TextView) findViewById(R.id.mis_reservas_detalle_tvHabitacionPrecio);
        tvReservaId = (TextView) findViewById(R.id.mis_reservas_detalle_tvReservaId);
        tvReservaFEntrada = (TextView) findViewById(R.id.mis_reservas_detalle_tvReservaFEntrada);
        tvReservaFSalida = (TextView) findViewById(R.id.mis_reservas_detalle_tvReservaFSalida);
        tvClienteNombre = (TextView) findViewById(R.id.mis_reservas_detalle_tvClienteNombre);
        tvClienteDni = (TextView) findViewById(R.id.mis_reservas_detalle_tvClienteDni);
        tvCantidadDias = (TextView) findViewById(R.id.mis_reservas_detalle_tvCantidadDias);
        tvSubTotal = (TextView) findViewById(R.id.mis_reservas_detalle_tvSubTotal);
        tvTotal = (TextView) findViewById(R.id.mis_reservas_detalle_tvTotal);

        Reservas reservas = getIntent().getParcelableExtra("Reservas");
        img.setBackgroundResource(reservas.getImg());
        tvHabitacionID.setText(reservas.getHabitacion().getHabitacionID() + "");
        tvCategoriaTitulo.setText(reservas.getHabitacion().getCategoria().getTitulo());
        tvCategoriaDescripcion.setText(reservas.getHabitacion().getCategoria().getDescripcion());
        tvHabitacionNCuartos.setText(reservas.getHabitacion().getNCuartos() + "");
        tvHabitacionNDormitorios.setText(reservas.getHabitacion().getNDormitorios() + "");
        tvHabitacionNBanos.setText(reservas.getHabitacion().getNBanos() + "");
        tvHabitacionPrecio.setText(reservas.getHabitacion().getPrecio() + "");
        tvReservaId.setText(reservas.get_id() + "");
        tvReservaFEntrada.setText(reservas.getFechaInicio() + "");
        tvReservaFSalida.setText(reservas.getFechaSalida() + "");
        tvClienteNombre.setText(reservas.getCliente().getNombre() + " " + reservas.getCliente().getApellido());
        tvClienteDni.setText(reservas.getCliente().getDniCliente() + "");
        gastoDAO = new GastoDAO(this);
        gastoDAO.openDataBase();
        Cursor c = gastoDAO.consultarGastoporReservaID(Integer.parseInt(tvReservaId.getText().toString()));
        if (c != null) {
            while (c.moveToNext()) {
                numDias = String.valueOf(c.getInt(c.getColumnIndex("Cantidad")));
            }
        }
        tvCantidadDias.setText(numDias);
        double subTotal = Double.parseDouble(tvHabitacionPrecio.getText().toString()) * Integer.parseInt(numDias);
        double total = subTotal + (subTotal * 0.18);
        tvSubTotal.setText(subTotal + "");
        tvTotal.setText(total + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opc_mis_reservas_detalles, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        reservaDAO = new ReservaDAO(this);
        reservaDAO.openDataBase();
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activos);
                this.finish();
                break;
            case R.id.action_cancelar:
                accionCancelar();
                break;
            case R.id.action_papelera:
                accionPapelera();
                break;
            case R.id.action_eliminar:
                accionEliminar();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que se encarga de enviar a la papelera
     */
    public void accionPapelera() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Papelera ...");
        dialog.setMessage("¿Esta seguro que desea enviarla a papelera?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int codReserva = Integer.parseInt(tvReservaId.getText().toString());
                    int rpt = reservaDAO.enviarPapeleraReserva(codReserva);
                    if (rpt > 0) {
                        Toast.makeText(MisReservasDetalleActivity.this, "Reserva enviada a papelera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MisReservasDetalleActivity.this, "No se pudo enviar a papelera la reserva", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reservaDAO.closeDataBase();
                    setResult(Activos);
                    finish();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    /**
     * Metodo que se encarga de eliminar la reserva
     */
    public void accionEliminar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Eliminando ...");
        dialog.setMessage("Si ejecuta esta accion usted podria tener inconvenientes en su reserva, no podra volver a recuperarla\n" +
                " ¿Esta seguro que desea eliminarla?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int codReserva = Integer.parseInt(tvReservaId.getText().toString());
                    int rpt = reservaDAO.eliminarReserva(codReserva);
                    if (rpt > 0) {
                        Toast.makeText(MisReservasDetalleActivity.this, "Reserva eliminada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MisReservasDetalleActivity.this, "No se pudo eliminar esta reserva", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reservaDAO.closeDataBase();
                    setResult(Activos);
                    finish();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    /**
     * Metodo que se encarga de cancelar mis reservas realizadas
     */
    public void accionCancelar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Cancelando ...");
        dialog.setMessage("Si ejecuta esta accion usted podria tener inconvenientes en su reserva, esta proceso es irreversible\n" +
                " ¿Esta seguro que desea cancelar su reserva?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int codReserva = Integer.parseInt(tvReservaId.getText().toString());
                    int codHabitacion = Integer.parseInt(tvHabitacionID.getText().toString());
                    int rpt = reservaDAO.cancelarReserva(codReserva, codHabitacion);
                    if (rpt > 0) {
                        Toast.makeText(MisReservasDetalleActivity.this, "Reserva Cancelada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MisReservasDetalleActivity.this, "No se puede cancelar esta reserva, revise el estado de su reserva", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reservaDAO.closeDataBase();
                    setResult(Activos);
                    finish();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    // Metodo que se activa al momento de pulsar "atras" en esta actividad
    @Override
    public void onBackPressed() {
        setResult(Activos);
        this.finish();
    }
}

