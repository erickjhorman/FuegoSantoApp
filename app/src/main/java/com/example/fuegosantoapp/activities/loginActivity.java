package com.example.fuegosantoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fuegosantoapp.Constants;
import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.RequestHandler;
import com.example.fuegosantoapp.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity implements  View.OnClickListener   {

    private EditText editTextCorreo;
    private Button buttonlogin;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextCorreo = (EditText) findViewById(R.id.editTextCorreo);
        buttonlogin = (Button) findViewById(R.id.buttonlogin);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage("Por favor espere...");
        buttonlogin.setOnClickListener(this);


    }

    private void subscriptorLogin(){
        final String email = editTextCorreo.getText().toString().trim();
        progressDialog.show();
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
                                                obj.getString("email")
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
            startActivity(new Intent(this, loginActivity.class));
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
}
