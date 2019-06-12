package com.example.permisosapp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class CuentasActivity extends AppCompatActivity {

    private static final String[] PERMISOS = {Manifest.permission.GET_ACCOUNTS};
    private static final int COD_PERMISO_CUENTA = 100;

    private RecyclerView recView;

    private ArrayList<Account> datos;

    private AdapterCuentas adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        ActivityCompat.requestPermissions(this, PERMISOS, COD_PERMISO_CUENTA);


    }


    private void mostrarCuentas() {

        AccountManager ac = (AccountManager) getSystemService(ACCOUNT_SERVICE); // proporciona servicio a las cuentas
        Account[] array_cuentas = ac.getAccounts();

        datos = new ArrayList<Account>();
        for (Account account : array_cuentas) {
            Log.d("MIAPP", account.name + "" + account.type);
            datos.add(account);
        }
        recView = (RecyclerView) findViewById(R.id.RecView);

        adaptador = new AdapterCuentas(datos);

        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Log.d("MIAPP", "Permiso concedido");
            mostrarCuentas();


        } else {

            Log.d("MIAPP", "Permiso No concedido");
        }
    }


}
