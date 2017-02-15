package com.example.demon.tarea2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    String matricula,nombre,apellido,licenciatura;
    ActionBar actionBar;
    EditText editTextNombre;
    EditText editTextApellido;
    EditText editTextMatricula;
    Spinner spinnerLicenciatura;
    private ArrayList<String> spinnerArray = new ArrayList<String>();
    private ArrayList<String> matriculas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        startLics();
        getMatriculas();
        editTextNombre=(EditText) findViewById(R.id.editTextName);
        editTextApellido=(EditText) findViewById(R.id.editTextLast);
        spinnerLicenciatura=(Spinner) findViewById(R.id.spinnerCareerR);
        editTextMatricula=(EditText) findViewById(R.id.editTextMatricula);


        ArrayAdapter<String> staticAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML


        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLicenciatura.setAdapter(staticAdapter);
        spinnerLicenciatura.setSelection(0);

    }

    private void startLics() {
        spinnerArray.add("LIS");
        spinnerArray.add("LCC");
        spinnerArray.add("LIC");
    }

    public void register(View view) {
        nombre = editTextNombre.getText().toString().trim();
        apellido= editTextApellido.getText().toString().trim();
        licenciatura = spinnerLicenciatura.getSelectedItem().toString().trim();
        matricula = editTextMatricula.getText().toString().trim();
        if(nombre.isEmpty()||apellido.isEmpty()||matricula.isEmpty()){
            AlertDialog.Builder datosDiag =  new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Alguno de los datos no se intrudujo")
                    .setMessage("Nombre: " + nombre + "\n"
                            + "Apellido: " + apellido + "\n"
                            + "Matricula: " + matricula + "\n"
                    )
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert);

            datosDiag.show();
        }else if(checkMatricula(matricula)) {
            AlertDialog.Builder datosDiag =  new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Matricula ya utilizada")
                    .setMessage(
                             "Matricula: " + matricula + "\n"
                    )
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert);

            datosDiag.show();

        }

        else{
            addDB();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkMatricula(String matr) {
        for(String m: matriculas){
            if(m.equals(matr)){
                return true;
            }
        }
        return false;
    }

    private void addDB() {
        AlumnoDataSource studentDS = new AlumnoDataSource(getApplicationContext());
        studentDS.open();
        studentDS.insertAlumno(matricula,nombre,apellido,licenciatura);
        studentDS.close();
    }

    private void getMatriculas(){
        AlumnoDataSource ads = new AlumnoDataSource(getApplicationContext());
        ads.open();
        if(!ads.getAllAlumnos().isEmpty()){
            for(Alumno a : ads.getAllAlumnos()){
                matriculas.add(a.getMatricula());
            }
        }
        ads.close();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
