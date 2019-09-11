package com.example.fuegosantoapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.android.volley.RequestQueue;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.SharedPrefManager;

import java.io.File;

import static com.example.fuegosantoapp.R.color.BlueViolet;

public class editarDatos extends AppCompatActivity {

    //Route to save the images
    private final String CARPETA_RAIZ = "Imagenes_usuarios/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + "misFotos";

    final int COD_SELECCIONA = 10;
    final  int COD_FOTO = 20;

    String path;
    private TextView textViewUsername;
    private ImageView imageViewUserAvatar;

    Toolbar toolbar;
    RequestQueue request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);




        textViewUsername = (TextView) findViewById(R.id.editTextNombre);
        imageViewUserAvatar = (ImageView) findViewById(R.id.ImagenEditarDatos);

        request = Volley.newRequestQueue(getApplicationContext());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUserEmail());
        String urlImagen = SharedPrefManager.getInstance(this).getUseAvatar();
        initializeToolbar();
        setearUrlImagen(urlImagen);

    }



    private void initializeToolbar() {
        //Crear logica del toolbar y asignarle un titulo
        toolbar = (Toolbar) findViewById(R.id.tlb_edt_profile);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Editar perfil");
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                finish();
                Intent intent = new Intent(editarDatos.this, ProfileActivity.class);
                startActivity(intent);

            }
        });
    }


    private void setearUrlImagen(String urlImagen) {


        urlImagen = urlImagen.replace(" ", "%20");  //To remove the spaces in my image

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                //To pit the image avator rounded

                //creamos el drawable redondeado
                RoundedBitmapDrawable roundedDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), response);

                roundedDrawable.setCornerRadius(response.getHeight());
                imageViewUserAvatar.setImageDrawable(roundedDrawable);
                //imageViewUserAvatar.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
            }
        });
        request.add(imageRequest);


    }


    public void onClick(View view) {

        cargarImagen();
    }




    private void cargarImagen(){

        final CharSequence[] opciones = {"Tomar foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones = new  AlertDialog.Builder(editarDatos.this);
        alertOpciones.setTitle("Selecciones una opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Tomar foto")) {
                   tomarFotografia();
                } else {
                    if (opciones[i].equals("Cargar Imagen")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), COD_SELECCIONA);
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }



    private void tomarFotografia(){
        File fileImagen = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean iscreada = fileImagen.exists();
        String nombreImagen = "";
        if(iscreada == false){
            iscreada = fileImagen.mkdirs();
        }

        if(iscreada == true){
            nombreImagen = (System.currentTimeMillis()/1000)+ ".jpg";

        }

        //Route to save the image
         path = Environment.getExternalStorageDirectory()+File.separator + RUTA_IMAGEN + File.separator + nombreImagen;

        File imagen = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent, COD_FOTO);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri mipath = data.getData();
                    imageViewUserAvatar.setImageURI(mipath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                           Log.i("Ruta de almacenamiento","Path:"+ path );
                        }
                    });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imageViewUserAvatar.setImageBitmap(bitmap);
                    break;

            }


        }
    }


}



