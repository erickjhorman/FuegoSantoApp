package com.example.fuegosantoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.RequestHandler;
import com.example.fuegosantoapp.SharedPrefManager;
import com.example.fuegosantoapp.entidades.Publicacion;
import com.example.fuegosantoapp.fragmentos.Fragmento_publicaciones;
import com.example.fuegosantoapp.interfaces.IComunicaFragments;
import com.example.fuegosantoapp.interfaces.IFragments;
import com.example.fuegosantoapp.picasso.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.fuegosantoapp.Constants.URL_COMENTAR_PUBLICACION;


public class DetailsCommentsPost extends  AppCompatActivity implements Fragmento_publicaciones.OnFragmentInteractionListener, IComunicaFragments, IFragments {

    private static final int AUTO_SIZE_TEXT_TYPE_NONE = 0 ;

    //  To declare a variable to the layout
  LinearLayout layout;
  LinearLayout.LayoutParams params;

  private int txtNewSizes;
  private int withd;
  private int higth;

  private TextView comment;

  // variable for comments layout
    private ImageView commentsImageUser;
    private String commentsUser;
    private Button commentBtnUser;
    private String publicacion_id;
    private String subscriptor_id;


    // I declare the field for my publications_fragment
    Fragment Fragmento_publicaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comments);

        Fragmento_publicaciones = new Fragmento_publicaciones();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Fragmento_publicaciones()).commit();

        // Gets linearlayout
        layout = findViewById(R.id.commentsLayout);
        comment = (TextView) findViewById(R.id.Comentarios);


        TextViewCompat.setAutoSizeTextTypeWithDefaults(comment, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        Log.e("size" ,"size "+ txtNewSizes);
        // Gets the layout params that will allow you to resize the layout
         params = (LinearLayout.LayoutParams) layout.getLayoutParams();


         // Get the reference of my components in layout comments
        commentsImageUser = (ImageView) findViewById(R.id.imgAvatarComentario);
        commentsUser  = String.valueOf((EditText) findViewById(R.id.txtComentario));
        commentBtnUser = (Button) findViewById(R.id.btnSendComentario);

       Log.i("BTN", "User" + commentBtnUser);


        // To get and set the image from the logged  user
        String  url_imagen_usuario =(SharedPrefManager.getInstance(this).getUseAvatar());

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Picasso.get().load(url_imagen_usuario)
                    .fit()
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(commentsImageUser);
        } else {
            commentsImageUser.setImageResource(R.mipmap.ic_launcher);

        }


        commentBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hola desde Detail ", Toast.LENGTH_LONG).show();
                comentarPublicacion(commentsUser);
            }
        });

    }

    public void comentarPublicacion(String commentsUser) {

       // To validate the information
        final String subscriptor_id = String.valueOf((SharedPrefManager.getInstance(this).getUserId()));

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_COMENTAR_PUBLICACION, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                //editTextCorreo.getText().clear();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("subscriptor_id", subscriptor_id);
                params.put("publicacion_id", publicacion_id);
                params.put("comentario", commentsUser);
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }


    @Override
    public void enviarPublicacion(Publicacion publicacion) {

    }

    @Override
    public void comentarPublicacion(String comentario, String publicacion_id, String subscriptor_id) {

    }

    @Override
    public void sharePublicacion() {

    }

    @Override
    public void detallePublicacion() {
        Toast.makeText(this,"Hey from Detail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendIdPublication(String position) {
           Toast.makeText(this,"Hey from Detail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
}
