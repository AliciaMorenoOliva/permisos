package com.example.permisosapp;

import android.accounts.Account;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CuentasViewHolder extends RecyclerView.ViewHolder {
    private TextView text_view_nombre;
    private TextView text_view_tipo;

    public CuentasViewHolder(View itemView) {

        super(itemView);
        text_view_nombre = (TextView) itemView.findViewById(R.id.lblnombre);
        text_view_tipo = (TextView) itemView.findViewById(R.id.lbltipo);

    }


    public void cargarCuentaEnHolder(Account ac) {
        text_view_nombre.setText(ac.name);
        text_view_tipo.setText(ac.type);

    }
}
