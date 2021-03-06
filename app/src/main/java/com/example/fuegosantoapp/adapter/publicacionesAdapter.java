package com.example.fuegosantoapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.RequestHandler;
import com.example.fuegosantoapp.SharedPrefManager;
import com.example.fuegosantoapp.activities.loginActivity;
import com.example.fuegosantoapp.entidades.Comentarios;
import com.example.fuegosantoapp.entidades.Publicacion;
import com.example.fuegosantoapp.interfaces.IComunicaFragments;
import com.example.fuegosantoapp.picasso.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.fuegosantoapp.Constants.URL_COMENTAR_PUBLICACION;

public class publicacionesAdapter extends RecyclerView.Adapter<publicacionesAdapter.publicacionesHolder> implements View.OnClickListener, Response.Listener, ErrorListener {


    List<Publicacion> listaPublicaiones;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Context context;
    //EditText txtTextComentario;
    String publicaciones_id;
    private OnItemClickListener mlistener;
    ProgressDialog progreso;


    ProgressDialog progressDialog;
    IComunicaFragments interfaceComunicaFragments;



    private View.OnClickListener listener;


    public publicacionesAdapter(List<Publicacion> listaPublicaiones,  Context context, IComunicaFragments interfaceComunicaFragments) {
        this.listaPublicaiones = listaPublicaiones;
        this.context = context;
        this.interfaceComunicaFragments = interfaceComunicaFragments;
        request = Volley.newRequestQueue(context);


    }


    @NonNull
    @Override

    public publicacionesAdapter.publicacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicaciones, parent, false);
        vista.setOnClickListener(this);


        // Instantiate the RequestQueue.
        request = Volley.newRequestQueue(context);
        //txtTextComentario = vista.findViewById(R.id.txtComentario);


        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new publicacionesAdapter.publicacionesHolder(vista);
    }


    public interface OnItemClickListener{
      void onItemClick(int position);
    }

    public void SetOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }


    public void setOnclickListener(OnClickListener listener) {
        this.listener = listener;
    }


    private void CargarWebservices() {

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Cargando");
        progressDialog.show();

        //final String comentario = editTextComentario.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_COMENTAR_PUBLICACION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    //editTextComentario.getText().clear();


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
                //params.put("email", comentario);
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

        //Toast.makeText(context, "lista Comentarios" + listaComentarios.get(position).getComentario(), Toast.LENGTH_LONG).show();

        holder.txtidPublicacion.setText((listaPublicaiones.get(position).getId_publicaciones()));
        holder.txtidPublicacion.setVisibility(View.GONE);
        holder.txttituloPublicacion.setText(listaPublicaiones.get(position).getFtitulo());
        //holder.txtPublicacion.setText(listaPublicaiones.get(position).getPublicaciones());
        holder.txtfechaPublicacion.setText(listaPublicaiones.get(position).getFecha_publicacion());
        holder.txtautor.setText(listaPublicaiones.get(position).getAutor());
        holder.txtDecripcion.setText(listaPublicaiones.get(position).getDescripcion());

        /*
        holder.nombre_usuario.setText(listaComentarios.get(position).getEmail());
        holder.txcomentario.setText(listaComentarios.get(position).getComentario());
        holder.txtfecha.setText(listaComentarios.get(position).getFecha());
        holder.txthora.setText(listaComentarios.get(position).getHora());
*/

        if (listaPublicaiones.get(position).getCover() != null) {
            cargarImagenUrl(listaPublicaiones.get(position).getCover(), holder);
        }


        //String imagenUser = listaComentarios.get(position).getAvatar();
        //Toast.makeText(context, "Avatar imagenes" + imagenUser , Toast.LENGTH_LONG).show();
        String  url_imagen_usuario =(SharedPrefManager.getInstance(context).getUseAvatar());

/*
        Picasso.get().load(imagenUser)
                .fit()
                .centerCrop()
                .transform(new CircleTransform())
                .into(holder.imagen_usuario);
*/


        if (SharedPrefManager.getInstance(context).isLoggedIn()) {



            Picasso.get().load(url_imagen_usuario)
                    .fit()
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(holder.img_avatar_comentario);

        } else {
            holder.imagen_usuario.setImageResource(R.mipmap.ic_launcher);

        }












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
    public void onClick(View view) {

        if (listener != null) {
            listener.onClick(view);
        }


        switch (view.getId()) {
            case R.id.verDetalle:
                Toast.makeText(context, "Ver detalle", Toast.LENGTH_SHORT).show();


                break;
            case R.id.txtComentar:

                break;

            case R.id.txtShare:
                break;

            case R.id.btnSendComentario:

                break;


        }


    }


    @Override
    public int getItemCount() {
        return listaPublicaiones.size();
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {

    }


    //Only changed public by static
    public class publicacionesHolder extends RecyclerView.ViewHolder {


        //text views
        TextView txtidPublicacion, txttituloPublicacion, txtPublicacion, txtDecripcion, txtfechaPublicacion, txtautor, txcomentario, verDetalle, comentar, share,
                txtfecha,txthora,nombre_usuario;

        ImageView Imagecover, imagen_usuario,img_avatar_comentario;

        //image buttons
        ImageButton btnComentar;

        //edit text
        EditText txtTextComentario;


        public publicacionesHolder(@NonNull View itemView) {
            super(itemView);
            txtidPublicacion = itemView.findViewById(R.id.idPublicacion);
            txttituloPublicacion = itemView.findViewById(R.id.tituloPublicacion);
            Imagecover = itemView.findViewById(R.id.idImagenPublicacion);
            txtDecripcion = itemView.findViewById(R.id.Description);
            txtfechaPublicacion = itemView.findViewById(R.id.fechaPublicacion);
            txtautor = itemView.findViewById(R.id.autor);
            txcomentario = itemView.findViewById(R.id.Comentarios);
            verDetalle = itemView.findViewById(R.id.verDetalle);
            comentar = itemView.findViewById(R.id.txtComentar);
            share = itemView.findViewById(R.id.txtShare);
            txtfecha = itemView.findViewById(R.id.txtFecha);
            txthora = itemView.findViewById(R.id.txtHora);
            btnComentar = itemView.findViewById(R.id.btnSendComentario);
            txtTextComentario = itemView.findViewById(R.id.txtComentario);
            img_avatar_comentario = itemView.findViewById(R.id.imgAvatarComentario);
            imagen_usuario = itemView.findViewById(R.id.imgAvatar);
            nombre_usuario = itemView.findViewById(R.id.txt_nombre_usuario);




            /*
            if (SharedPrefManager.getInstance(context).isLoggedIn()) {


                nombre_usuario.setText(SharedPrefManager.getInstance(context).getUserEmail());

                Picasso.get().load(url_imagen_usuario)
                        .fit()
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(imagen_usuario);

                Picasso.get().load(url_imagen_usuario)
                        .fit()
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(img_avatar_comentario);

            } else {
                imagen_usuario.setImageResource(R.mipmap.ic_launcher);

            }
*/


                btnComentar.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (SharedPrefManager.getInstance(context).isLoggedIn()) {



                            progreso = new ProgressDialog(context);
                            progreso.setMessage("Cargando...");
                            progreso.show();

                        final String comentario = txtTextComentario.getText().toString().trim();
                        final String publicacion_id = txtidPublicacion.getText().toString().trim();
                        final String subscriptor_id = String.valueOf((SharedPrefManager.getInstance(context).getUserId()));

                        if (comentario.isEmpty()) {
                            Toast.makeText(context, "Ingrese un comentario", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(itemView.getContext(), "Ver comentario" + comentario + "Publicacion id" + publicacion_id + "Sucriptir id" + subscriptor_id, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.comentarPublicacion(comentario, publicacion_id, subscriptor_id);
                            txtTextComentario.getText().clear();
                        }

                        progreso.hide();


                    }
                        else {
                            Toast.makeText(context, "Debes logearte para poder comentar", Toast.LENGTH_SHORT).show();
                        }


            }
                });



            verDetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        int position = getAdapterPosition();
                        Toast.makeText(context, "Mensaje desde MainActivity:" + position, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Desde publicaciones adapater", Toast.LENGTH_SHORT).show();
                        interfaceComunicaFragments.enviarPublicacion(listaPublicaiones.get(position));


                }
            });

        }


    }







}
