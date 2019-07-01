package com.example.fuegosantoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         //Crear logica del toolbar y asignarle un titulo
         toolbar=(Toolbar) findViewById(R.id.toolbar_inicio);
         setSupportActionBar(toolbar);
         //toolbar.setTitle("Fuego Santo");
         toolbar.setSubtitle("Dios les bendiga");
         toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);  //To add the menu to the toolbar

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()){

            case R.id.search:
                msg = "search";
                break;

            case R.id.settings:
                msg = "settings";
                break;

            case R.id.edit:
                msg = "edit";
                break;

            case R.id.Salir:
                msg = "salir";
                break;

        }
        Toast.makeText(this, msg +  "Checked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
