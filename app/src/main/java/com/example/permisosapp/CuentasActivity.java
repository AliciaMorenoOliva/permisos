package com.example.permisosapp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CuentasActivity extends AppCompatActivity {

    private static final String[] PERMISOS = {Manifest.permission.GET_ACCOUNTS};
    private static final int COD_PERMISO_CUENTA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        ActivityCompat.requestPermissions(this,PERMISOS,COD_PERMISO_CUENTA);


    }



    private void mostrarCuentas() {

        AccountManager ac = (AccountManager) getSystemService(ACCOUNT_SERVICE); // proporciona servicio a las cuentas
        Account[] array_cuentas = ac.getAccounts();

        for (Account account : array_cuentas)
        {
            Log.d("MIAPP", account.name + "" + account.type);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Log.d("MIAPP", "Permiso concedido");
            mostrarCuentas();


        }else{

            Log.d("MIAPP", "Permiso No concedido");
        }
    }


}
