package com.example.permisosapp;


import android.accounts.Account;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class AdapterCuentas extends RecyclerView.Adapter<CuentasViewHolder> {

    private ArrayList<Account> datos;

    @NonNull
    @Override
    public CuentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       CuentasViewHolder cuentasViewHolder = null;

       LayoutInflater inflater = LayoutInflater.from(parent.getContext());

       View itemView = inflater.inflate(R.layout.layout_cuentas_item, parent, false);

       cuentasViewHolder = new CuentasViewHolder(itemView);

        return cuentasViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CuentasViewHolder holder, int position) {

        Account account = datos.get(position);
        holder.cargarCuentaEnHolder(account);

    }
    @Override
    public int getItemCount() {
        return datos.size();
    }

    public AdapterCuentas (ArrayList<Account> accounts){ this.datos = accounts;}
}
