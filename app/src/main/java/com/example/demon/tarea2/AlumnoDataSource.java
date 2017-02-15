package com.example.demon.tarea2;

/**
 * Created by Roberto Gil on 11/02/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlumnoDataSource {
    private SQLiteDatabase database;
    private AlumnoDBHelper alumnoDBHelper;
    private String[] allColumns = {AlumnoContract.COLUMN_NAME_MATRICULA,
            AlumnoContract.COLUMN_NAME_NOMBRE,
            AlumnoContract.COLUMN_NAME_APELLIDO,
            AlumnoContract.COLUMN_NAME_LICENCIATURA,
    };

    public AlumnoDataSource(Context context){
        alumnoDBHelper = new AlumnoDBHelper(context);
    }

    public void open() throws SQLException {
        database = alumnoDBHelper.getWritableDatabase();
    }

    public void close(){
        alumnoDBHelper.close();
    }

    /**
     * Function oriented to insert a new Alumno into DataBase
     * @param matricula
     * @param nombre
     * @param apellido
     * @param licenciatura
     * @return row ID of the newly Alumno inserted row, or -1
     */
    public long insertAlumno(String matricula, String nombre, String apellido, String licenciatura){
        ContentValues values = new ContentValues();
        values.put(AlumnoContract.COLUMN_NAME_MATRICULA, matricula);
        values.put(AlumnoContract.COLUMN_NAME_NOMBRE, nombre);
        values.put(AlumnoContract.COLUMN_NAME_APELLIDO, apellido);
        values.put(AlumnoContract.COLUMN_NAME_LICENCIATURA,licenciatura);
        long newRowId;
        newRowId = database.insert(AlumnoContract.TABLE_NAME,null,values);
        return newRowId;
    }

    /**
     * Function oriented to recovery all Alumno's rows from Database
     * @return List of Alumnos from Database
     */
    public List<Alumno> getAllAlumnos(){
        List<Alumno> alumnos = new ArrayList<Alumno>();
        Alumno alumno;
        Cursor cursor = database.query(AlumnoContract.TABLE_NAME,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            alumno = cursorToAlumno(cursor);
            alumnos.add(alumno);
            cursor.moveToNext();
        }
        cursor.close();
        return alumnos;
    }

    private Alumno cursorToAlumno(Cursor cursor){
        Alumno alumno = new Alumno();
        alumno.setMatricula(cursor.getString(0));
        alumno.setNombre(cursor.getString(1));
        alumno.setApellido(cursor.getString(2));
        alumno.setLicenciatura(cursor.getString(3));
        return alumno;
    }

}
