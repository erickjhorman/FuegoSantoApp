package com.example.fuegosantoapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.example.fuegosantoapp.Slide_images.CustomSwipeAdapter;
import com.example.fuegosantoapp.activities.ProfileActivity;
import com.example.fuegosantoapp.activities.loginActivity;
import com.example.fuegosantoapp.adapter.publicacionesAdapter;
import com.example.fuegosantoapp.entidades.Comentarios;
import com.example.fuegosantoapp.entidades.Imagenes;
import com.example.fuegosantoapp.entidades.Publicacion;
import com.example.fuegosantoapp.fragmentos.DetallePublicacionesFragment;
import com.example.fuegosantoapp.fragmentos.Fragmento_Mensaje;
import com.example.fuegosantoapp.fragmentos.Fragmento_biblia;
import com.example.fuegosantoapp.fragmentos.Fragmento_favoritos;
import com.example.fuegosantoapp.fragmentos.Fragmento_publicaciones;
import com.example.fuegosantoapp.interfaces.IComunicaFragments;
import com.example.fuegosantoapp.interfaces.IFragments;
import com.google.android.material.navigation.NavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.cloudinary.json.JSONString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.fuegosantoapp.Constants.URL_GETPUBLICACIONFS;
import static com.example.fuegosantoapp.Constants.URL_IMAGENES;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, IFragments, IComunicaFragments, Response.ErrorListener, Response.Listener<JSONObject> {

    //Edit texts for the database
    private EditText editTextCorreo;
    private Button buttonRegistrar;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    DetallePublicacionesFragment detalleFragment;
    private Button btnloginNavbar;
    private TextView navHeaderComentario;
    private ImageView imageViewUserAvatar;
    private TextView textViewUserCorreo;
    ArrayList<Imagenes> listaImagenes;
    ProgressDialog progress;
    CarouselView carouselView;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private DrawerLayout drawer; //Variable to make the funcionality of the navbar
    Toolbar toolbar;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    Imagenes imagenes = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //Toast.makeText(MainActivity.this, "Firebase conncetion Succes", Toast.LENGTH_LONG).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //viewPager = (ViewPager) findViewById(R.id.view_pager);
        //pager = findViewById(R.id.view_pager);
        onNewIntent(getIntent());
        adapter = new CustomSwipeAdapter(this);
        request = Volley.newRequestQueue(getApplicationContext());

        //viewPager.setAdapter(adapter);
        //viewPager.setAdapter(adapter);

        //Crear logica del toolbar y asignarle un titulo
        toolbar = (Toolbar) findViewById(R.id.toolbar_inicio);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fuego Santo");
        toolbar.setSubtitle("Dios les bendiga");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        //Crar funcionalidad del toolbar que abre y cierra la barra de navegacion
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setVerticalScrollBarEnabled(false);



        listaImagenes = new ArrayList<>();
        getImagenes();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Code for showing the articulos fragment at the start of the app

        /*if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragmento_articulo()).commit();

        }*/


        //Inflate the nav_header
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        headerView.findViewById(R.id.nav_header);

        //Declaring the variables for the nav_header


        //imageViewUserAvatar = (ImageView) headerView.findViewById(R.id.imageViewUserAvatar);

        btnloginNavbar = (Button) headerView.findViewById(R.id.btnlogin);
        navHeaderComentario = (TextView) headerView.findViewById(R.id.navHeaderComentario);
        textViewUserCorreo = (TextView) headerView.findViewById(R.id.textViewUserCorreo);
        textViewUserCorreo.setVisibility(View.GONE);
        imageViewUserAvatar = (ImageView) headerView.findViewById(R.id.avatarUsuario);
        btnloginNavbar.setOnClickListener(this);

        //String urlImagen = SharedPrefManager.getInstance(this).getUseAvatar();
        //setearUrlImagen(urlImagen);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            imageViewUserAvatar = (ImageView) headerView.findViewById(R.id.avatarUsuario);
            textViewUserCorreo.setText(SharedPrefManager.getInstance(this).getUserEmail());
            textViewUserCorreo.setVisibility(View.VISIBLE);
            String urlImagen = SharedPrefManager.getInstance(this).getUseAvatar();
            //Toast.makeText(getApplicationContext(), "Url" + urlImagen, Toast.LENGTH_LONG).show();


            navHeaderComentario.setVisibility(View.GONE);
            btnloginNavbar.setVisibility(View.GONE);


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
                    //Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
                }
            });
            request.add(imageRequest);


            return;
        }


        //Declaring the variables for the suscription
        editTextCorreo = (EditText) findViewById(R.id.editTextCorreo);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        buttonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        progressDialog = new ProgressDialog(this);
        buttonRegistrar.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);


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

            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        Toast.makeText(this, msg + "Checked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    //To select the item of the navbar and open a specific fragmen
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.inicio:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.act_cuenta:
                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                    finish();
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(this, loginActivity.class));

                }

                break;
            case R.id.nav_mensajes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_Mensaje()).commit();
                break;
            case R.id.nav_publicaciones:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_publicaciones()).commit();
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


    public void getImagenes() {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.URL_IMAGENES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                //JSONObject jsonObject = new JSONObject(response);

                JSONArray json = response.optJSONArray("imagenes");
                //Toast.makeText(getApplicationContext(),"Mensaje:" + json, Toast.LENGTH_SHORT).show();
                //To create Carousel
                //carouselView.setPageCount(json.length());
                Toast.makeText(getApplicationContext(),"Mensaje:" + json.length(), Toast.LENGTH_SHORT).show();

                try {
                    for (int i = 0; i < json.length(); i++) {




                        //carouselView.setPageCount(json.length());
                        imagenes = new Imagenes();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);


                        imagenes.setTitulo(jsonObject.optString("Titulo"));
                        imagenes.setDescription(jsonObject.optString("Descripcion"));
                        imagenes.setImagen(jsonObject.optString("Imagen"));


                        listaImagenes.add(imagenes);
                        //Toast.makeText(getApplicationContext(), "lista" + listaImagenes.get(i).getTitulo() , Toast.LENGTH_LONG).show();
                        //String urlImagen = listaImagenes.get(i).getImagen();
                        cargarImagenUrl(listaImagenes.get(i).getImagen());
                        //Toast.makeText(getApplicationContext(), "lista" + urlImagen , Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No se ha podido establecer una relacion con el servidor  " + response.toString(), Toast.LENGTH_LONG).show();
                    //System.out.println();
                    Log.d("error : ", response.toString());
                    progress.hide();

                    //Log.d("error : ", error.toString());
                    progress.hide();
                }
                //Toast.makeText(getApplicationContext(),"Mensaje:" + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });


        request.add(jsonObjectRequest);






        //String url = "http://localhost/Android/v1/getImagenes.php";

/*
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_IMAGENES, null, this, this);
        request.add(jsonObjectRequest);
*/


    }
    private void cargarImagenUrl(String getImagen ) {

        //String urlImagen = getCover.replace("", "%20");

        ImageRequest imageRequest = new ImageRequest(getImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //Imagecover.setImageBitmap(response);
            }
        }, 0,0 , ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error al cargar la imagen"  , Toast.LENGTH_LONG).show();
            }
        });
        request.add(imageRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se consultar" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        //Log.d("error : ", error.toString());
        progress.hide();
    }

    public void onResponse(JSONObject response) {


        Toast.makeText(getApplicationContext(), "Mensaje:" + response, Toast.LENGTH_SHORT).show();
    }


    //Method to create a subscription of any user
    public void suscripcionUsuario() {

        //To validate the email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


        Pattern exp2 = Pattern.compile("^([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");


        final String email = editTextCorreo.getText().toString().trim();
        Matcher mather = pattern.matcher(email);
        Matcher mather2 = exp2.matcher(email);

        if (email.isEmpty()) {
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
        } else if (!mather.find()) {
            Toast.makeText(this, "Email ingresado es invalido", Toast.LENGTH_SHORT).show();
        } else if (!mather2.find()) {
            Toast.makeText(this, "Caracteres no permitidos ", Toast.LENGTH_SHORT).show();
        } else {
            //To create the conexion to the database through
            progressDialog.setMessage("Registrando usuario...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        editTextCorreo.getText().clear();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }



        /*
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        */

    }

    //To get the accion of the button


    @Override
    public void onClick(View view) {

        String msg = "";
        if (view == buttonRegistrar)
            suscripcionUsuario();
        if (view == textViewLogin)
            startActivity(new Intent(this, loginActivity.class));
    }


    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void enviarPublicacion(Publicacion publicacion) {
        detalleFragment = new DetallePublicacionesFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", publicacion);
        detalleFragment.setArguments(bundleEnvio);

        //Cargar el fragment en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detalleFragment).addToBackStack(null).commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("menuFragment")) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Fragmento_publicaciones()).commit();
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}