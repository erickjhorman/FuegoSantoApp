package com.example.fuegosantoapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.Constants;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.RequestHandler;
import com.example.fuegosantoapp.SharedPrefManager;
import com.example.fuegosantoapp.activities.loginActivity;
import com.example.fuegosantoapp.entidades.Comentarios;
import com.example.fuegosantoapp.entidades.Publicacion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.fuegosantoapp.Constants.URL_COMENTAR_PUBLICACION;

public class publicacionesAdapter extends RecyclerView.Adapter<publicacionesAdapter.publicacionesHolder> implements View.OnClickListener, Response.Listener, ErrorListener {


    List<Publicacion> listaPublicaiones;
    List<Comentarios> listaComentarios;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Context context;
    private TextView verDetalle;
    private TextView comentar;
    private TextView share;
    private EditText editTextComentario;
    private ImageButton btnComentar;
    ProgressDialog progressDialog;



    private View.OnClickListener listener;


    public publicacionesAdapter(List<Publicacion> listaPublicaiones, List<Comentarios> listaComentarios, Context context) {
        this.listaPublicaiones = listaPublicaiones;
        this.listaComentarios = listaComentarios;
        this.context = context;
        request = Volley.newRequestQueue(context);
    }


    @NonNull
    @Override

    public publicacionesAdapter.publicacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicaciones, parent, false);
        vista.setOnClickListener(this);

        verDetalle = (TextView) vista.findViewById(R.id.verDetalle);
        comentar = (TextView) vista.findViewById(R.id.txtComentar);
        share = (TextView) vista.findViewById(R.id.txtShare);
        btnComentar =  (ImageButton) vista.findViewById(R.id.btnSendComentario);
        editTextComentario = (EditText) vista.findViewById(R.id.txtComentario);

      // Instantiate the RequestQueue.
       request = Volley.newRequestQueue(context);



        verDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Boton detalle ", Toast.LENGTH_LONG).show();
            }
        });

        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Boton Comentar ", Toast.LENGTH_LONG).show();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Boton Compartir ", Toast.LENGTH_LONG).show();
            }
        });


        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarWebservices();
            }
        });



        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new publicacionesAdapter.publicacionesHolder(vista);
    }

    private void CargarWebservices() {

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Cargando");
        progressDialog.show();

        final String comentario = editTextComentario.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_COMENTAR_PUBLICACION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    editTextComentario.getText().clear();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", comentario);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    /*
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "No se consultar" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("error : ", error.toString());
        progressDialog.hide();
    }

     */


/*
    public void onResponse(JSONObject response) {
        editTextComentario.getText().clear();
        progressDialog.hide();
        Toast.makeText(context, "Mensaje:" + response, Toast.LENGTH_SHORT).show();

    }

 */


    @Override
    public void onBindViewHolder(@NonNull publicacionesHolder holder, int position) {

        Toast.makeText(context, "lista Comentarios" + listaComentarios.get(position).getComentario(), Toast.LENGTH_LONG).show();

        //holder.txtidPublicacion.setText(( listaPublicaiones.get(position).getId_publicaciones()));
        holder.txttituloPublicacion.setText(listaPublicaiones.get(position).getFtitulo());
        //holder.txtPublicacion.setText(listaPublicaiones.get(position).getPublicaciones());
        holder.txtfechaPublicacion.setText(listaPublicaiones.get(position).getFecha_publicacion());
        holder.txtautor.setText(listaPublicaiones.get(position).getAutor());
        holder.txtDecripcion.setText(listaPublicaiones.get(position).getDescripcion());
        holder.txcomentario.setText(listaComentarios.get(position).getComentario());


        if (listaPublicaiones.get(position).getCover() != null) {
            cargarImagenUrl(listaPublicaiones.get(position).getCover(), holder);
        }


        String urlImagen = listaPublicaiones.get(position).getCover();
        //Toast.makeText(context, "Url" + urlImagen , Toast.LENGTH_LONG).show();

    }

    private void cargarImagenUrl(String getCover, final publicacionesHolder holder) {

        //String urlImagen = getCover.replace("", "%20");

        ImageRequest imageRequest = new ImageRequest(getCover, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.Imagecover.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error al cargar la imagen"  , Toast.LENGTH_LONG).show();
            }
        });
        request.add(imageRequest);


    }


    @Override
    public int getItemCount() {
        return listaPublicaiones.size();
    }

    @Override
    public void onClick(View view) {

        if (listener != null) {
            listener.onClick(view);
        }

        /*
        if (view == comentar)
            detallePublicacion();

        if (view == comentar)
            comentarPublicacion();

        if (view == share)
            sharePublicacion();
            */

    }




    private void sharePublicacion() {
        Toast.makeText(context, "Boton detalle ", Toast.LENGTH_LONG).show();
    }

    private void comentarPublicacion() {
        Toast.makeText(context, "Boton Comentar ", Toast.LENGTH_LONG).show();
    }

    private void detallePublicacion() {
        Toast.makeText(context, "Boton Compartir ", Toast.LENGTH_LONG).show();
    }

    public void onResponse(Object response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


    public class publicacionesHolder extends RecyclerView.ViewHolder {

        TextView txtidPublicacion, txttituloPublicacion, txtPublicacion, txtDecripcion, txtfechaPublicacion, txtautor, txcomentario;
        ImageView Imagecover;

        public publicacionesHolder(@NonNull View itemView) {
            super(itemView);
            txtidPublicacion = itemView.findViewById(R.id.idPublicacion);
            txttituloPublicacion = itemView.findViewById(R.id.tituloPublicacion);
            Imagecover = itemView.findViewById(R.id.idImagenPublicacion);
            txtDecripcion = itemView.findViewById(R.id.Description);
            txtfechaPublicacion = itemView.findViewById(R.id.fechaPublicacion);
            txtautor = itemView.findViewById(R.id.autor);
            txcomentario = itemView.findViewById(R.id.Comentarios);


        }
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;

    }


}
