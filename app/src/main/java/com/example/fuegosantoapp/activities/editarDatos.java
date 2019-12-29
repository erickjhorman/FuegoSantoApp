package com.example.fuegosantoapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.fuegosantoapp.Constants;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.RequestHandler;
import com.example.fuegosantoapp.SharedPrefManager;
import com.example.fuegosantoapp.mCloud.Myconfiguration;

import org.cloudinary.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.PendingIntent.getActivity;
import static android.graphics.BitmapFactory.decodeFile;

public class editarDatos extends AppCompatActivity implements View.OnClickListener {

    //Route to save the images
    private final String CARPETA_RAIZ = "Imagenes_usuarios/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + "misFotos";

    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 20;

    String path;

    private TextView textViewUsername;
    private TextView getTextViewId;
    private ImageView imageViewUserAvatar;

    Button botonCargar;
    Button btnUpdate;
    Toolbar toolbar;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    Bitmap bitmap;
    File imagen;
    Uri file;
    Uri miImagen;
    File filePath;
    String nombreImagen = "";

    private String currentUrl = null;
    JSONObject Result;
    Object object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);

        MediaManager.init(this, Myconfiguration.getMyconfigs());
        /*
        Map config = new HashMap();
        config.put("cloud_name", "dequvdgav");
        MediaManager.init(this, config);

         */


        textViewUsername = (TextView) findViewById(R.id.editTextNombre);
        getTextViewId = (TextView) findViewById(R.id.txtIdUsuario);
        imageViewUserAvatar = (ImageView) findViewById(R.id.ImagenEditarDatos);


        botonCargar = (Button) findViewById(R.id.btnCargarImagen);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setEnabled(false);
        btnUpdate.setOnClickListener(this);
        botonCargar.setOnClickListener(this);

        if (validaPermisos()) {

            botonCargar.setEnabled(true);
        } else {
            botonCargar.setEnabled(false);
        }


        request = Volley.newRequestQueue(getApplicationContext());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUserEmail());
        getTextViewId.setText(Integer.toString(SharedPrefManager.getInstance(this).getUserId()));
        String urlImagen = SharedPrefManager.getInstance(this).getUseAvatar();
        initializeToolbar();
        setearUrlImagen(urlImagen);
    }

    private boolean validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                botonCargar.setEnabled(true);
            } else
                solicitarPermisoManual();
        }

    }

    private void solicitarPermisoManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(editarDatos.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    botonCargar.setEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados ", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();

    }


    private void cargarDialogoRecomendacion() {


        AlertDialog.Builder dialogo = new AlertDialog.Builder(editarDatos.this);
        dialogo.setTitle("Permisos Desactivados ");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });

        dialogo.show();
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


        // urlImagen = urlImagen.replace(" ", "%20");  //To remove the spaces in my image

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
                //Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
            }
        });
        request.add(imageRequest);


    }



/*
    public void onClick(View view) {

        cargarImagen();
    }
*/

    @Override
    public void onClick(View view) {


        if (view == botonCargar)
            cargarImagen();
        if (view == btnUpdate)
            uploadImage();


    }


    private void cargarImagen() {

        final CharSequence[] opciones = {"Tomar foto", "Cargar Imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(editarDatos.this);
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


    private void tomarFotografia() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean iscreada = fileImagen.exists();


        //String nombreImagen = "";

        if (iscreada == false) {
            iscreada = fileImagen.mkdirs();
        }

        if (iscreada == true) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";

        }

        //Route to save the image
        path = Environment.getExternalStorageDirectory() + File.separator + RUTA_IMAGEN + File.separator + nombreImagen; // we  indicate the path of the storage

        File imagen = new File(path);
        Toast.makeText(getApplicationContext(), "Path inicial" + imagen, Toast.LENGTH_LONG).show();


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = getApplicationContext().getPackageName() + ".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));

        }


        startActivityForResult(intent, COD_FOTO);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        filePath = null;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri uri = data.getData();


                    imageViewUserAvatar.setImageURI(uri);
                    Log.i("Ruta de almacenamiento", "Path:" + uri);

                    //MediaManager.get().upload(Uri.fromFile(filePath)).dispatch();

                    try {

                        //Here i need to do the same as Bitmap with a file

                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                        imageViewUserAvatar.setImageBitmap(bitmap);
                        filePath = new File(getRealPathFromURI(uri));
                        Log.i("File creado", "Path:" + filePath);
                        btnUpdate.setEnabled(true);

                        Toast.makeText(getApplicationContext(), "Path inicial" + bitmap, Toast.LENGTH_LONG).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    break;

                case COD_FOTO:
                   /*
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta de almacenamiento", "Path:" + path);


                        }
                    });
*/
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta de almacenamiento", "Path:" + path);
                            filePath = new File(getRealPathFromURI(uri));


                        }
                    });

                    //Uri uri = Uri.parse(path);


                    // filePath = new File(getRealPathFromURI(_mipath,Photo));
                    //Log.i("File creado", "Path:" + filePath);
                    bitmap = decodeFile(path);
                    imageViewUserAvatar.setImageBitmap(bitmap);


                    break;

            }


        }


    }


    public String getRealPathFromURI(Uri uri) {

        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();

            cursor.close();
            return result;
        }

        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        result = cursor.getString(idx);
        cursor.close();
        return result;


    }


    public void SubirImagenCloudinary(String nombre) {

        //MediaManager.get().upload(Uri.fromFile(filePath)).dispatch();
        String nombreUsuario = nombre;

        try {
            String requestUrl = MediaManager.get().upload(Uri.fromFile(filePath)).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    currentUrl = (String) resultData.get("url");
                    Log.i("Url final", "Path:" + currentUrl);
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                }
            })
                    .option("resource_type", "image")
                    .option("folder", "FuegoSanto/avatar/")
                    .option("public_id", "My_dog")
                    .option("overwrite", true)
                    .dispatch();


        } catch (JSONException e) {

        }

        /*

        try {
            String requestId = MediaManager.get().upload(Uri.fromFile(filePath))
                    .option("resource_type", "image")
                    .option("folder", "FuegoSanto/avatar/")
                    .option("use_filename", "true")
                    //.option("public_id",  nombreImagen)

                    .option("overwrite", true)

                    .dispatch();
        } catch (JSONException e) {

        }
*/


        //Log.i("Response", "Path:" + Url);
        //https://res.cloudinary.com/dequvdgav/image/upload/v1577411853/fahutrqpm7oixw2jrf4n.jpg



       /*
        String Url = MediaManager.get().url().generate("my_dog");
        Log.i("Url final", "Path:" + Url);
*/





        /*
        MediaManager.get().upload(R.drawable.sample_1)
                .option("resource_type", "imagen")
                .option("folder", "FuegoSanto/avatar/")
                .option("public_id", "avatar")
                .option("overwrite", true)
                .option("notification_url", "https://mysite.example.com/notify_endpoint")
                .dispatch();
*/

    }


    public void uploadImage() {


        progreso = new ProgressDialog(getApplicationContext());
        progreso.setMessage("Cargando...");


        textViewUsername = (TextView) findViewById(R.id.editTextNombre);

        getTextViewId = (TextView) findViewById(R.id.txtIdUsuario);
        imageViewUserAvatar = (ImageView) findViewById(R.id.ImagenEditarDatos);


        final String documento = getTextViewId.getText().toString().trim();
        Log.i("documento ", "" + documento);
        final String nombre = textViewUsername.getText().toString().trim();


        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        } else if (imageViewUserAvatar.getDrawable() == null) {
            Toast.makeText(this, "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();

        } else {
            try {
                MediaManager.get().upload(Uri.fromFile(filePath)).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        currentUrl = (String) resultData.get("url");
                        Log.i("Url final", "Path:" + currentUrl);
                        String avatar = currentUrl;
                        Log.i("Url ", "avatar" + currentUrl);

                        updateSubscriptor(nombre, documento, currentUrl);

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                })

                        .option("resource_type", "image")
                        .option("folder", "FuegoSanto/avatar/")
                        .option("public_id", "My_dog")
                        .option("overwrite", true)
                        .dispatch();


            } catch (JSONException e) {

            }
        }


    }

    public void updateSubscriptor(String nombre, String documento, String avatar) {


        //Bitmap image =((BitmapDrawable)imageViewUserAvatar.getDrawable()).getBitmap();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.i("Response ", "" + response);
                    JSONObject obj = new JSONObject(response);

                    if (!obj.getBoolean("error")) {
                        SharedPrefManager.getInstance(getApplicationContext())
                                .getUserUpdated(
                                        obj.getInt("id"),
                                        obj.getString("nombre"),
                                        obj.getString("email"),
                                        obj.getString("avatar")

                                );
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
                    }


                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();


                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("imagen", avatar);
                params.put("id", documento);


                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


}







