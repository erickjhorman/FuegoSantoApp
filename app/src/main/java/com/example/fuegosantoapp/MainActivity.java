package com.example.fuegosantoapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fuegosantoapp.Slide_images.CustomSwipeAdapter;
import com.example.fuegosantoapp.fragmentos.Fragmento_Mensaje;
import com.example.fuegosantoapp.fragmentos.Fragmento_articulo;
import com.example.fuegosantoapp.fragmentos.Fragmento_biblia;
import com.example.fuegosantoapp.fragmentos.Fragmento_favoritos;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer; //Variable to make the funcionality of the navbar
    Toolbar toolbar;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        //Crear logica del toolbar y asignarle un titulo
        toolbar = (Toolbar) findViewById(R.id.toolbar_inicio);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Fuego Santo");
        toolbar.setSubtitle("Dios les bendiga");
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        //Crar funcionalidad del toolbar que abre y cierra la barra de navegacion
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setVerticalScrollBarEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Code for showing the articulos fragment at the start of the app

        /*if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragmento_articulo()).commit();

        }*/
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);  //To add the menu to the toolbar

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {

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
        Toast.makeText(this, msg + "Checked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    //To select the item of the navbar and open a specific fragmen
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_mensajes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_Mensaje()).commit();
                break;
            case R.id.nav_publicaciones:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_articulo()).commit();
                break;

            case R.id.favoritos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_favoritos()).commit();
                break;

            case R.id.biblia:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_biblia()).commit();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Compartido", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_send:
                Toast.makeText(this, "Enviado", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}