package com.example.fuegosantoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Poner el icono en action bar

        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);*/

         //Crear logica del toolbar y asignarle un titulo
         toolbar=(Toolbar) findViewById(R.id.toolbar_inicio);
         setSupportActionBar(toolbar);
         //toolbar.setTitle("Fuego Santo");
         toolbar.setSubtitle("Dios les bendiga");

    }
}
