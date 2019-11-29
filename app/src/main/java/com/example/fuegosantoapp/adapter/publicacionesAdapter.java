package com.example.fuegosantoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.SharedPrefManager;
import com.example.fuegosantoapp.activities.loginActivity;
import com.example.fuegosantoapp.entidades.Comentarios;
import com.example.fuegosantoapp.entidades.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class publicacionesAdapter extends RecyclerView.Adapter<publicacionesAdapter.publicacionesHolder> implements View.OnClickListener {


    List<Publicacion> listaPublicaiones;
    List<Comentarios> listaComentarios;
    RequestQueue request;
    Context context;

    private  View.OnClickListener listener;


    public publicacionesAdapter(List<Publicacion> listaPublicaiones,List<Comentarios> listaComentarios, Context context) {
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


        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new publicacionesAdapter.publicacionesHolder(vista);
    }




    @Override
    public void onBindViewHolder(@NonNull publicacionesHolder holder, int position) {

        Toast.makeText(context, "lista Comentarios" + listaComentarios.get(position).getComentario() , Toast.LENGTH_LONG).show();

        //holder.txtidPublicacion.setText(( listaPublicaiones.get(position).getId_publicaciones()));
        holder.txttituloPublicacion.setText(listaPublicaiones.get(position).getFtitulo());
        //holder.txtPublicacion.setText(listaPublicaiones.get(position).getPublicaciones());
        holder.txtfechaPublicacion.setText(listaPublicaiones.get(position).getFecha_publicacion());
        holder.txtautor.setText(listaPublicaiones.get(position).getAutor());
        holder.txtDecripcion.setText(listaPublicaiones.get(position).getDescripcion());
        holder.txcomentario.setText(listaComentarios.get(position).getComentario());


        if(listaPublicaiones.get(position).getCover()!= null){
               cargarImagenUrl(listaPublicaiones.get(position).getCover(), holder);
        }




        String urlImagen = listaPublicaiones.get(position).getCover();
        //Toast.makeText(context, "Url" + urlImagen , Toast.LENGTH_LONG).show();

    }

    private void cargarImagenUrl(String getCover , final publicacionesHolder holder ) {

        //String urlImagen = getCover.replace("", "%20");

        ImageRequest imageRequest = new ImageRequest(getCover, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.Imagecover.setImageBitmap(response);
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
    public int getItemCount() {
        return listaPublicaiones.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }



    public class publicacionesHolder extends RecyclerView.ViewHolder {

        TextView txtidPublicacion, txttituloPublicacion, txtPublicacion, txtDecripcion, txtfechaPublicacion, txtautor,txcomentario;
        ImageView Imagecover ;

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

    public void setOnclickListener(View.OnClickListener listener){
        this.listener  = listener;

    }




}
