package com.example.permisosapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoActivity extends AppCompatActivity {

    private static final String GUARDAR_FOTO = "prefs";

    public static final int CODIGO_PETICION_SELECCIONAR_FOTO = 100;
    public static final int CODIGO_PETICION_PERMISOS = 150;
    public static final int CODIGO_PETICION_HACER_FOTO = 200;

    private ImageView imageView;//la imagen seleccionado o la foto
    private Uri photo_uri;//para almacenar la ruta de la imagen

    private static final String[] PERMISOS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //creamos las variables para guardar la foto que capturamos
    private static final String PREFIJO_FOTOS = "curso_PIC";
    private static final String SUFIJO_FOTOS = ".jpg";
    private String ruta_foto; //nombre fichero creado
    private static final String uriImagen = "uriImagen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        this.imageView = findViewById(R.id.imegeView);

        registerForContextMenu(this.imageView);

        ActivityCompat.requestPermissions(this, PERMISOS, CODIGO_PETICION_PERMISOS);

        SharedPreferences prefs = getSharedPreferences(GUARDAR_FOTO, MODE_PRIVATE);
        String guardaUriImagen = prefs.getString(uriImagen,null );
        if (guardaUriImagen != null) {
            photo_uri = Uri.parse( guardaUriImagen);
        }
        this.imageView.setImageURI(photo_uri);//recojo la foto
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_contextual, menu);
        menu.setHeaderTitle("Seleccione Menu");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Log.d("MIAPP", "tocó" +  item.getTitle());

        //dibujar un ALERT Dialog de confirmacion
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirme por favor");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                imageView.setImageURI(null);

                File f = new File(photo_uri.toString());
                f.setWritable(true);
                boolean b = f.delete();
              //  File borra_foto = new File(ruta_foto);// otra forma de eliminar la foto son estas 2 lineas
              //  borra_foto.delete();
               //eliminarFoto(new File(ruta_foto) ); //llama al metodo eliminarFoto



            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("MIAPP", "El usuario canceló");

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return super.onContextItemSelected(item);
    }

   /* public void eliminarFoto(File borra_foto) {
        borra_foto.delete();

    }*/

    //nos devuelve los permisos concedidos y en este caso sino acepta los permisos se sale de la aplicacion y le sale un Toast
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            Log.d("MIAPP", "me ha concedido permisos");
        } else {
            Log.d("MIAPP", "No me ah concedido los permisos");
            Toast.makeText(this, "NO PUEDES SEGUIR", Toast.LENGTH_SHORT);
            this.finish();
        }
    }

    //creamos el fichero donde ira la imagen
    private Uri crearFicheroImagen() {

        Uri uri_destino = null;
        String momento_actual = null;
        String nombre_fichero = PREFIJO_FOTOS + momento_actual + SUFIJO_FOTOS;//nombre que nos va a poner en el fichero
        File file = null;
        momento_actual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //ruta donde se guarda la foto
        ruta_foto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + nombre_fichero;

        Log.d("MIAPP", "RUTA FOTO" + ruta_foto);
        file = new File(ruta_foto);


        try { // intenta esto


            if (file.createNewFile()) {
                Log.d("MIAPP", "FICHERO CREADO");
            } else {
                Log.d("MIAPP", "FICHERO NO CREADO");
            }
        } catch (Exception e) {//si falla se mete por aqui
            Log.d("MIAPP", "Error al crear el fichero", e);//esto es un error de excepcion
        }

        uri_destino = Uri.fromFile(file);
        Log.d("MIAPP", "URI" + uri_destino.toString());
        return uri_destino;
    }

    //metodo para guardar la foto donde yo quiera
    private void desactivarModoEstricto()
    {
        if (Build.VERSION.SDK_INT >= 24)
        {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
        }catch (Exception e)
            {

            }

        }

    }


    public void tomarFoto(View view) {
        Log.d("MIAPP", "quiere hacer una foto");

        Intent inten_foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//quiero tomar una foto
        this.photo_uri = crearFicheroImagen();
        inten_foto.putExtra(MediaStore.EXTRA_OUTPUT, this.photo_uri);//guardala aqui
        desactivarModoEstricto();
        startActivityForResult(inten_foto, CODIGO_PETICION_HACER_FOTO);
    }


    public void seleccionarFoto(View view) {
        Log.d("MIAPP", "quiere selecionar una foto");
        Intent intent_pide_foto = new Intent();
        //intent_pide_foto.setAction(Intent.ACTION_PICK); //seteo la accion
        intent_pide_foto.setAction(Intent.ACTION_GET_CONTENT);
        intent_pide_foto.setType("image/*");//tipo mime

        startActivityForResult(intent_pide_foto, CODIGO_PETICION_SELECCIONAR_FOTO); //Invocando actividad que te devuelve resultado en este caso se va a ir a la galeria o la camara y volverá


    }

    private void setearImagenDesdeArchivo(int resultado, Intent data) {
        switch (resultado) {
            case RESULT_OK:
                Log.d("MIAPP", "la foto ha sido seleccionada");
                this.photo_uri = data.getData();
                this.imageView.setImageURI(photo_uri);//recojo la foto
                this.imageView.setScaleType(ImageView.ScaleType.FIT_XY);//expande la foto a los limites de la caja
                break;
            case RESULT_CANCELED:
                Log.d("MIAPP", "la foto NO ha sido seleccionada");
                break;
        }
    }

    private void setearImagenDesdeCamara(int resultado, Intent intent) {
        switch (resultado) {
            case RESULT_OK:
                Log.d("MIAPP", "Tiro la foto bien");
                imageView.setImageURI(this.photo_uri);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photo_uri));
                break;
            case RESULT_CANCELED:
                Log.d("MIAPP", "Cancelo la foto");
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); //depende de la accion se llama al padre o no
        Log.d("MIAPP", "metiendo la imagen");
        if (requestCode == CODIGO_PETICION_SELECCIONAR_FOTO) {
            setearImagenDesdeArchivo(resultCode, data);
        } else if (requestCode == CODIGO_PETICION_HACER_FOTO) {
            setearImagenDesdeCamara(resultCode, data);
        }

    }
    @Override
    protected void onStop() {

        SharedPreferences.Editor editor = getSharedPreferences(GUARDAR_FOTO, MODE_PRIVATE).edit();
        editor.putString(uriImagen, photo_uri.toString());//edita los cambios
        editor.apply();//guarda los cambios en el entorno android
        /**
         * recoger la uri de la imagen ya esta en photo_uri
         * guardarla en shared preferences
         */

        super.onStop();
    }

}


