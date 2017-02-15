package com.example.demon.tarea2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    String matricula,nombre,apellido,licenciatura;

    EditText editTextNombre;
    EditText editTextApellido;
    EditText editTextMatricula;
    Spinner spinnerLicenciatura;
    private ArrayList<String> spinnerArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        startLics();
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
        nombre = editTextNombre.getText().toString();
        apellido= editTextApellido.getText().toString();
        licenciatura = spinnerLicenciatura.getSelectedItem().toString();
        matricula = editTextMatricula.getText().toString();

        addDB();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    private void addDB() {
        AlumnoDataSource studentDS = new AlumnoDataSource(getApplicationContext());
        studentDS.open();
        studentDS.insertAlumno(matricula,nombre,apellido,licenciatura);
        studentDS.close();
    }
}
