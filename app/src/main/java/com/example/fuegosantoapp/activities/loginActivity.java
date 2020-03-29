package com.example.fuegosantoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.Constants;
import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.RequestHandler;
import com.example.fuegosantoapp.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class loginActivity extends AppCompatActivity implements  View.OnClickListener   {

    private EditText editTextCorreo;
    private Button buttonlogin;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initializeToolbar();

        editTextCorreo = (EditText) findViewById(R.id.editTextCorreo);
        buttonlogin = (Button) findViewById(R.id.buttonlogin);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage("Por favor espere...");
        buttonlogin.setOnClickListener(this);


    }

    private void initializeToolbar(){
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
                finish();
                //onBackPressed();
            }
        });
    }


    public void subscriptorLogin(){
        final String email = editTextCorreo.getText().toString().trim();
        progressDialog.show();
        System.out.println(email);
        StringRequest  stringRequest = new StringRequest(

                Request.Method.POST,
                Constants.URL_LOGIN,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .subsciptorLogin(
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonlogin)
            subscriptorLogin();
            startActivity(new Intent(this, ProfileActivity.class));
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==R.id.backArrowLogin){
           startActivity(new Intent(this, MainActivity.class));
           finish();
       }
        return true;
    }
    */






    private void enviarTokenToServer(final String token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_TOKEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se registro exitosamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("Token", token);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
