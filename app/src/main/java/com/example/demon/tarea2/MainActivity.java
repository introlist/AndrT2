package com.example.demon.tarea2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.demon.tarea2.AlumnoDataSource;
import com.example.demon.tarea2.Alumno;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOGIN= "login";
    List<Alumno> alumnos;
    ArrayList<String> nombresAlumnos = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, nombresAlumnos);
        fillList();
    }

    private void fillList() {
        readDB();
        ListView list = (ListView)findViewById(R.id.listViewStudents);
        list.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombre, apellido, matricula, licenciatura;
                nombre = alumnos.get(i).getNombre();
                apellido = alumnos.get(i).getApellido();
                matricula = alumnos.get(i).getMatricula();
                licenciatura = alumnos.get(i).getLicenciatura();
                Context context = getApplicationContext();

                AlertDialog.Builder datosDiag =  new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Datos del alumno")
                        .setMessage("Nombre: " + nombre + "\n"
                                + "Apellido: " + apellido + "\n"
                                + "Matricula: " + matricula + "\n"
                                + "Lic.: " + licenciatura + "\n"
                        )
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert);

                datosDiag.show();
            }
        });


    }

    private void readDB() {
        AlumnoDataSource alumnosDS = new AlumnoDataSource(getApplicationContext());
        alumnosDS.open();
        if(alumnosDS.getAllAlumnos().isEmpty()){
            alumnosDS.insertAlumno("10003074","Roberto","Gil","LIS");
        }
        alumnos = alumnosDS.getAllAlumnos();
        int size = alumnos.size();
        int index = 0;
        while(index < size){
            nombresAlumnos.add(alumnos.get(index).getNombre());
            index++;
        }

        alumnosDS.close();
    }


    public void exit(View view) {

        SharedPreferences sharedPrefs = getSharedPreferences(LOGIN,0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        ActivityCompat.finishAffinity(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(this);
    }

    public void registerStudent(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
