package com.example.fuegosantoapp.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.android.volley.RequestQueue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.SharedPrefManager;


public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewUserCorreo;
    private ImageView imageViewUserAvatar;
    Toolbar toolbar;
    private Button btnEditarPerfil;
    RequestQueue request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUserCorreo = (TextView) findViewById(R.id.textViewUserCorreo);
        imageViewUserAvatar = (ImageView) findViewById(R.id.imageViewUserAvatar);
        request = Volley.newRequestQueue(getApplicationContext());
        btnEditarPerfil = (Button) findViewById(R.id.btnEditarPerfil);
        initializeToolbar();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }

        if(SharedPrefManager.getInstance(this).isUpdated()) {
            imageViewUserAvatar.setImageResource(R.drawable.ic_android_black_navbar);
        }

         if(!TextUtils.isEmpty(SharedPrefManager.getInstance(this).getUserEmail())){
             textViewUserCorreo.setText(SharedPrefManager.getInstance(this).getUserEmail());

         } else {
             textViewUserCorreo.setText("Invitado");

         }



        String urlImagen = SharedPrefManager.getInstance(this).getUseAvatar();
        //Toast.makeText(getApplicationContext(), "Url en profile Activity" + urlImagen, Toast.LENGTH_LONG).show();
        setearUrlImagen(urlImagen);


        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Desde button Editar Perfil",Toast.LENGTH_SHORT).show();
                Intent miIntent = new Intent(ProfileActivity.this, editarDatos.class);
                startActivity(miIntent);
            }
        });

    }

    private void initializeToolbar() {
        //Crear logica del toolbar y asignarle un titulo
        toolbar = (Toolbar) findViewById(R.id.toolbar_Profile);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fuego Santo");
        toolbar.setSubtitle("Dios les bendiga");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                finish();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }


    private void setearUrlImagen(String urlImagen) {
        //urlImagen = urlImagen.replace("", "%20");  //To remove the spaces in my image}

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
        return;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;


        }
        return true;
    }


}
