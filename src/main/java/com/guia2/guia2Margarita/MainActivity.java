package com.guia2.guia2Margarita;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtURL;
    private TextView lblEstado;
    private Button btnDescargar;
    private ProgressBar progressBar;
    private RadioButton CambioNombre;
    private RadioButton NoCambioNombre;
    private  EditText Nombre;
    private  Boolean Cambia=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicializar
        txtURL       = (EditText) findViewById(R.id.txtURL);
        lblEstado    = (TextView) findViewById(R.id.lblEstado);
        btnDescargar = (Button)   findViewById(R.id.btnDescargar);
        progressBar = findViewById(R.id.progresbar);
        CambioNombre = findViewById(R.id.cambianombre);
        NoCambioNombre = findViewById(R.id.nocambia);
        Nombre = findViewById(R.id.Nombre);

        //evento onClick
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtURL.getText().toString().length()>0){
                    if(Nombre.getText().toString().length()>0 && Cambia){
                        new Descargar(
                                MainActivity.this,
                                lblEstado,
                                btnDescargar, progressBar, CambioNombre
                        ).execute(txtURL.getText().toString(), Nombre.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(),"Por Favor Digite un Nombre para el archivo",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No deje la url Vacia",Toast.LENGTH_SHORT).show();
                }
            }
        });
        CambioNombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Nombre.setEnabled(true);
                    Cambia = true;
                }
            }
        });
        NoCambioNombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Nombre.setEnabled(false);
                    Cambia=false;
                }
            }
        });
        verifyStoragePermissions(this);
    }

    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
