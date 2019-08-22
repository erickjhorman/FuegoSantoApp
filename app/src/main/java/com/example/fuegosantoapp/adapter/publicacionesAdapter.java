package com.example.fuegosantoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.entidades.Publicacion;

import java.util.List;

public class publicacionesAdapter extends RecyclerView.Adapter<publicacionesAdapter.publicacionesHolder> {

    List<Publicacion> listaPublicaiones;

    public publicacionesAdapter(List<Publicacion> listaPublicaiones) {
        this.listaPublicaiones = listaPublicaiones;
    }

    @NonNull
    @Override

    public publicacionesAdapter.publicacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicaciones, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new publicacionesAdapter.publicacionesHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull publicacionesAdapter.publicacionesHolder holder, int position) {
        //holder.txtidPublicacion.setText(( listaPublicaiones.get(position).getId_publicaciones()));
        holder.txttituloPublicacion.setText(listaPublicaiones.get(position).getFtitulo());
        holder.txtPublicacion.setText(listaPublicaiones.get(position).getPublicaciones());
        holder.txtfechaPublicacion.setText(listaPublicaiones.get(position).getFecha_publicacion());
        holder.txtautor.setText(listaPublicaiones.get(position).getAutor());

    }

    @Override
    public int getItemCount() {
        return listaPublicaiones.size();
    }

    public class publicacionesHolder extends RecyclerView.ViewHolder {

        TextView txtidPublicacion, txttituloPublicacion, txtPublicacion, txtfechaPublicacion, txtautor;

        public publicacionesHolder(@NonNull View itemView) {
            super(itemView);
            txtidPublicacion = (TextView)itemView.findViewById(R.id.idPublicacion);
            txttituloPublicacion = itemView.findViewById(R.id.tituloPublicacion);
            txtPublicacion = itemView.findViewById(R.id.Publicacion);
            txtfechaPublicacion = itemView.findViewById(R.id.fechaPublicacion);
            txtautor = itemView.findViewById(R.id.autor);
        }
    }
}
