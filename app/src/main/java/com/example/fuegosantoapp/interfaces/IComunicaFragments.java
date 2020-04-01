package com.example.fuegosantoapp.interfaces;

import com.example.fuegosantoapp.entidades.Publicacion;

public interface IComunicaFragments {

    public void enviarPublicacion(Publicacion publicacion);

    public void comentarPublicacion(String comentario, String publicacion_id,String subscriptor_id);

    public void sharePublicacion();

    public void detallePublicacion();

    public void sendIdPublication(String position);


}
