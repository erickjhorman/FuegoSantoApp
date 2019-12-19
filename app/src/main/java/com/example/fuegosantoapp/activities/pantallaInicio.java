package com.example.fuegosantoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;

public class pantallaInicio extends Activity {

    private Handler mHandler;
    private Runnable mRunnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        //Crear un handler post delayed para asignarte un tiempo a esta actividad y luego pase a la siguiente actividad
        mRunnable  = new Runnable() {
            @Override
            public void run(){
                //Se instancia el clase intent para pasar de una activity a otra
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

                /*
                Intent intent = new Intent(pantallaInicio.this, MainActivity.class);
                startActivity(intent);
                finish();

                 */

            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 4000);
       }

    @Override

    //Crear un handler post delayed para asignarte un tiempo a esta actividad y luego pase a la siguiente actividad
    protected  void onDestroy(){
        super.onDestroy();
        if(mHandler != null && mRunnable != null)
            new Handler().removeCallbacks(mRunnable);
    }
    }




