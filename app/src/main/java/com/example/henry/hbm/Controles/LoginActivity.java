package com.example.henry.hbm.Controles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.hbm.Daos.ClienteDAO;
import com.example.henry.hbm.Modelo.Cliente;
import com.example.henry.hbm.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText etUsuario, etPassword;
    private Button btnIngresar;
    private TextView tvRegistrarse;
    private static final int REQUEST_SIGNUP = 0;
    ClienteDAO clienteDAO;
    private String usuario, contraseña, nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsuario = (EditText) findViewById(R.id.login_edUsuario);
        etPassword = (EditText) findViewById(R.id.login_edPassword);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
        tvRegistrarse = (TextView) findViewById(R.id.login_tvRegistrar);
        btnIngresar.setOnClickListener(this);
        tvRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btnIngresar) {
            login();
        }
        if (v.getId() == R.id.login_tvRegistrar) {
            Intent intent = new Intent(this, RegistrarseActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            this.limpiar(etUsuario);
            this.limpiar(etPassword);
        }
    }

    /**
     * Metodo que ve la autentificacino de usuario, si el llamado es correcto ejecuta el metodo onLoginSuccess()
     * y se despliega la actividad principal, sino se ejecuta el metodo onLoginFailed()
     */
    private void login() {
        if (validate() == false) {
            onLoginFailed();
            return;
        } else {
            btnIngresar.setEnabled(false);
            final ProgressDialog progressDialog = new ProgressDialog(this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Autenticando...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // Si se completa la llamada correctamente ejecuta el metodo onLoginSuccess
                            onLoginSuccess();
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 5000);

        }
    }

    /**
     * Metodo que se encarga de hacer todas las validaciones
     * Retorna true o false , true si la validacion fue exitosa y false si no lo fue
     *
     * @return
     */
    private boolean validate() {
        boolean valid;
        usuario = etUsuario.getText().toString();
        contraseña = etPassword.getText().toString();
        if (usuario.isEmpty() || usuario.length() <= 7 || usuario.length() >= 9) {
            etUsuario.setError("Ingresa un dni válido");
            etUsuario.requestFocus();
            valid = false;
        } else {
            etUsuario.setError(null);
            if (contraseña.isEmpty()) {
                etPassword.setError("Ingrese un password");
                etPassword.requestFocus();
                valid = false;
            } else {
                etPassword.setError(null);
                if (contraseña.length() <= 3 || contraseña.length() > 10) {
                    etPassword.setError("Minimo 4 y  maximo 9 caracteres");
                    etPassword.requestFocus();
                    valid = false;
                } else {
                    etPassword.setError(null);
                    Cliente c = usuario(usuario, contraseña);
                    if (c == null) {
                        Toast.makeText(this, "El usuario no existe o ingreso datos incorrectos", Toast.LENGTH_SHORT).show();
                        etUsuario.requestFocus();
                        valid = false;
                    } else {
                        nombre = c.getNombre() + " " + c.getApellido();
                        etUsuario.setError(null);
                        etPassword.setError(null);
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * Metodo que consulta la BD para traer un objeto del tipo Cliente con su respectivo y clave
     *
     * @param usuario parametro que recive de los EditText; valor que es usado para una busqueda por de clientes existente
     * @param clave   parametro que recive de los EditText; valor que es usado para una busqueda por de clientes existente
     * @return retorna el objeto Cliente si hubieran datos; sino retornara un null
     */
    public Cliente usuario(String usuario, String clave) {
        Cliente cliente = null;
        try {
            clienteDAO = new ClienteDAO(this);
            clienteDAO.openDataBase();
            cliente = clienteDAO.validarLogin(usuario, clave);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clienteDAO.closeDataBase();
        }
        return cliente;
    }

    /**
     * metodo que muestra un mensaje de estado indicando que el inicio de sesion a fallado
     */
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Inicio de sesion fallida", Toast.LENGTH_SHORT).show();
        btnIngresar.setEnabled(true);
    }

    /**
     * metodo que se ejecuta si el logeo a sido un exito
     */
    public void onLoginSuccess() {
        btnIngresar.setEnabled(true);
        Intent i = new Intent(this, MenuActivity.class);
        SharedPreferences prefs =
                getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario + "");
        editor.putString("nombre", nombre);
        editor.commit();
        // metodo();
        startActivity(i);
        this.limpiar(etUsuario);
        this.limpiar(etPassword);
        etUsuario.requestFocus();
    }

    // Metodo que se activa al momento de pulsar "atras" en esta actividad
    // Mensaje de confirmacion para salir de la aplicacion (si o no)
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirmacion");
        dialog.setMessage("¿Desea salir de HBM?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                String dni = data.getStringExtra("dni");
                etUsuario.setText(dni);
            }
        }
    }

    /**
     * @param x metodo que recive TextView para poder limpiar las cajas de texto
     */
    public void limpiar(TextView x) {
        x.setText(null);
    }
}