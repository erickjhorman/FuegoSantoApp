package com.example.fuegosantoapp.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

public class navHeaderActivity extends loginActivity  implements View.OnClickListener {

    private ImageView imageViewUserAvatar;
    private Button buttonlogin;
    private TextView textViewUserCorreo;
    private TextView navHeaderComentario;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);


        navHeaderComentario = (TextView) findViewById(R.id.navHeaderComentario);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            navHeaderComentario.setVisibility(View.GONE);
            return;
        }




        buttonlogin = (Button) findViewById(R.id.btnlogin);
        buttonlogin.setOnClickListener(this);


        //To put an the avatar of the user in my nav_header
        imageViewUserAvatar = (ImageView) findViewById(R.id.avatarUsuario);

        request = Volley.newRequestQueue(getApplicationContext());

        String urlImagen = SharedPrefManager.getInstance(this).getUseAvatar();
        setearUrlImagen(urlImagen);

    }


    public void onClick(View v) {
        String msg = "";

        if (v == buttonlogin)
            Toast.makeText(this, msg + "Checked", Toast.LENGTH_SHORT).show();
          subscriptorLogin();
          startActivity(new Intent(this, ProfileActivity.class));
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

}
