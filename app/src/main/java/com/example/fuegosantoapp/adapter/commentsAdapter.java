package com.example.fuegosantoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.entidades.Comentarios;

import java.util.List;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.ViewHolderComments> {

    // i create a reference of my list with data
     List<Comentarios> listaComentarios;

     // To create a constructor of this list
    public commentsAdapter(List<Comentarios> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    // What this method does is to link  this adapter with the comments.xml where i have the components
    @NonNull
    @Override
    public ViewHolderComments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // To inflate the view and return the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments, parent, false);

        return new ViewHolderComments(view);
    }

    // This methods take charge of communicating between our adapter and the class ViewHolderComments
    @Override
    public void onBindViewHolder(@NonNull ViewHolderComments holder, int position) {

        holder.nombre_usuario.setText(listaComentarios.get(position).getEmail());
        holder.txcomentario.setText(listaComentarios.get(position).getComentario());
        holder.txtfecha.setText(listaComentarios.get(position).getFecha());
        holder.txthora.setText(listaComentarios.get(position).getHora());


    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }

    // To take the reference od the components into my comments.xml where i'll take the information to pt into my recyceles view
    public class ViewHolderComments extends RecyclerView.ViewHolder {

         // Text view of my comments.xml
        TextView  txtfecha,txthora,nombre_usuario,txcomentario;

        // Image view of my comments.xml
        ImageView Imagecover, imagen_usuario,img_avatar_comentario;


// I make the instance of the reference with the itemView.id
        public ViewHolderComments(@NonNull View itemView) {
            super(itemView);

            // instance of my text view
            nombre_usuario = itemView.findViewById(R.id.txt_nombre_usuario);
            txtfecha = itemView.findViewById(R.id.txtFecha);
            txthora = itemView.findViewById(R.id.txtHora);
            txcomentario = itemView.findViewById(R.id.Comentarios);

            // instance of my Image view
            img_avatar_comentario = itemView.findViewById(R.id.imgAvatarComentario);
            imagen_usuario = itemView.findViewById(R.id.imgAvatar);

        }


    }
}
