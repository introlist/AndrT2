package com.example.demon.tarea2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.UUID;*/
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText editTextE, editTextP;
    List<User> users;
    private static final String LOGIN= "login";
   // private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // beaconManager = new BeaconManager(getApplicationContext());


        editTextE = (EditText) findViewById(R.id.input_usuario);
        editTextP = (EditText) findViewById(R.id.input_contrasena);
        getFileData();
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN,0);
           if(sharedPreferences.contains("usuario")){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            System.out.print("Pref ok");
            this.finishActivity(0);
        }

       /* beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                setContentView(R.layout.login_linear_layout);
            }
            @Override
            public void onExitedRegion(Region region) {
               LoginActivity.super.finish();
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("63B4D01E-9D57-F74E-B4FE-1713BE20EC0F"),
                        63463, 21120));
//                UUID.fromString("CB806A42-320A-033A-CA3D-7A45DC5025E6"),
//                        56576, 58978));
//                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
//                        65074, 39598));
            }
        });*/


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
        if(!users.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Acceso Denegado")
                    .setMessage("Las credenciales no concuerdan con la base de datos" +
                            "\n Correctas: \n" + users.get(0).getEmail() + " \n y " + users.get(0).getPassword())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearLogin();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            clearLogin();
        }else{
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
    }


    private void clearLogin(){
        editTextE.setText("");
        editTextP.setText("");
    }

    private boolean checkCredentials() {
          for (int i = 0; i<users.size();i++) {
              if (editTextE.getText().toString().trim().equals(users.get(i).getEmail())) {
                  if (editTextP.getText().toString().trim().equals(users.get(i).getPassword())) {
                      sharedPreferencesEdit(editTextE.getText().toString().trim(),editTextP.getText().toString().trim() );
                      return true;
                  }
              }
          }
    return false;    }

    private void getFileData() {
        UserDataSource userDS = new UserDataSource(getApplicationContext());
        userDS.open();
        if(userDS.getAllUsers().isEmpty()){
            String email1 = "gilflo01@hotmail.com";
            String password1 = "123456";
            String email2 = "noemail";
            String password2 = "123456";
            validateCredentialsCreation(email1, password1, userDS);
            validateCredentialsCreation(email2, password2, userDS);

            
        }
        //userDS.insertUser("Roberto","Gil");
        users = userDS.getAllUsers();
        userDS.close();

    }

    private void
     validateCredentialsCreation(String email,String password, UserDataSource uds ) {
        Pattern p;
        Matcher m;
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        p = Pattern.compile(EMAIL_PATTERN);
        m = p.matcher(email);
            if(m.matches()){
                uds.insertUser(email,password);
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Dato no correcto")
                        .setMessage("Una de las credenciales no se introdujo a Base de datos - Email: "+email+
                                "\nPassword: "+password)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        }


    private void sharedPreferencesEdit(String user, String pass){

        SharedPreferences settings = getSharedPreferences(LOGIN,Context.MODE_PRIVATE); //0 means private mode
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("usuario",user);
        editor.putString("pass",pass);
        editor.commit();
    }

}
