package com.example.permisosapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DespertarMovilReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
        Log.d("MIAPP", "MI MOVIL SE HA INICIADO");
        context.startActivity(new Intent(context, MainActivity.class));
        Toast mensaje_toast = Toast.makeText(context,"HOLA",Toast.LENGTH_LONG);//creando el mensaje donde nos dice el tiempo que hemos tardado
        mensaje_toast.show(); //informo
    }
}
