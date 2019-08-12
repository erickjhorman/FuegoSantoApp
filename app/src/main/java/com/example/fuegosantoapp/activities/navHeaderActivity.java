package com.example.fuegosantoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.SharedPrefManager;

public class navHeaderActivity extends AppCompatActivity  {

    private Button buttonlogin;
    private TextView navHeaderComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        navHeaderComentario = (TextView) findViewById(R.id.navHeaderComentario);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            navHeaderComentario.setVisibility(View.GONE);
            return;
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        buttonlogin = (Button) findViewById(R.id.buttonlogin);


    }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }






}
