package com.example.henry.hbm.Controles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.hbm.Daos.ClienteDAO;
import com.example.henry.hbm.R;

public class RegistrarseActivity extends Activity {
    Button btnRegistrarse;
    TextView tvLinkLogin;
    EditText etClienteDni, etClienteNombre, etClienteApellido, etClientePass;
    ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        btnRegistrarse = (Button) findViewById(R.id.registrarse_btnRegistrarse);
        tvLinkLogin = (TextView) findViewById(R.id.registrarse_tvLinkLogin);
        etClienteDni = (EditText) findViewById(R.id.registrarse_etClienteDni);
        etClienteNombre = (EditText) findViewById(R.id.registrarse_etClienteNombre);
        etClienteApellido = (EditText) findViewById(R.id.registrarse_etClienteApellido);
        etClientePass = (EditText) findViewById(R.id.registrarse_etClientePass);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tvLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        } else {
            btnRegistrarse.setEnabled(false);
            final ProgressDialog progressDialog = new ProgressDialog(RegistrarseActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creando Cliente ...");
            progressDialog.show();
            String dni = etClienteDni.getText().toString();
            String nombre = etClienteNombre.getText().toString();
            String apellido = etClienteApellido.getText().toString();
            String password = etClientePass.getText().toString();
            try {
                clienteDAO = new ClienteDAO(this);
                clienteDAO.openDataBase();
                long rpt = clienteDAO.crearCliente(Integer.parseInt(dni), nombre, apellido, password);
                if (rpt != -1) {
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    onSignupSuccess();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                } else {
                    Toast.makeText(this, "No se admiten usuarios con el mismo DNI", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                clienteDAO.closeDataBase();
            }
        }
    }

    public void onSignupSuccess() {
        btnRegistrarse.setEnabled(true);
        Intent i = new Intent();
        i.putExtra("dni", etClienteDni.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Fallo al intentar registrarse", Toast.LENGTH_LONG).show();
        btnRegistrarse.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String dni = etClienteDni.getText().toString();
        String nombre = etClienteNombre.getText().toString();
        String apellido = etClienteApellido.getText().toString();
        String password = etClientePass.getText().toString();

        if (dni.isEmpty() || dni.length() <= 7 || dni.length() >= 9) {
            etClienteDni.setError("Ingrese un DNI valido");
            valid = false;
        } else {
            etClienteDni.setError(null);
        }

        if (nombre.isEmpty()) {
            etClienteNombre.setError("Debe ingresar un nombre");
            valid = false;
        } else {
            etClienteNombre.setError(null);
        }

        if (apellido.isEmpty()) {
            etClienteApellido.setError("Debe ingresar un apellido");
        } else {
            etClienteApellido.setError(null);
        }

        if (password.isEmpty() || password.length() <= 3 || password.length() > 10) {
            etClientePass.setError("Debe ingresar una contraseña de 4 - 9 caracteres");
            valid = false;
        } else {
            etClientePass.setError(null);
        }

        return valid;
    }
}
