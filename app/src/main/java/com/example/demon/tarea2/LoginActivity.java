package com.example.demon.tarea2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editTextU, editTextP;
    List<String> users = new ArrayList<>();
    List<String> passwords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_linear_layout);
        writeCredentialsFile();
        editTextU = (EditText) findViewById(R.id.input_usuario);
        editTextP = (EditText) findViewById(R.id.input_contrasena);
        getFileData();
    }

    public void clickLogin(View v){
        if(checkCredentials()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            clearLogin();
            return;
        }else{
            alertaCredenciales();
        }
    }

    private void alertaCredenciales(){
        new AlertDialog.Builder(this)
                .setTitle("Acceso Denegado")
                .setMessage("Las credenciales no concuerdan con la base de datos")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        clearLogin();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        clearLogin();
    }

    private void writeCredentialsFile() {
        String eol = System.getProperty("line.separator");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("myfile.txt", MODE_PRIVATE)));
            writer.write("user,");
            writer.write("password" + eol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearLogin(){
        editTextU.setText("");
        editTextP.setText("");
    }

    private boolean checkCredentials() {
          for (int i = 0; i<users.size();i++) {
              if (editTextU.getText().toString().equals(users.get(i))) {
                  if (editTextP.getText().toString().equals(passwords.get(i))) {
                      return true;
                  }
              }
          }
    return false;
    }

    private void getFileData() {
        String eol = System.getProperty("line.separator");
        BufferedReader input = null;
        List<String> parts = new ArrayList();
        try {
            input = new BufferedReader(new InputStreamReader(openFileInput("myfile.txt")));
            String line;
            while ((line = input.readLine()) != null) {
               parts = Arrays.asList(line.split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        users.add(parts.get(0));
        //editTextU.setText(users.get(0));
        passwords.add(parts.get(1));
       // editTextP.setText(passwords.get(0));
    }

}
