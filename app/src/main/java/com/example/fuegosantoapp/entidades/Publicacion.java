package com.example.fuegosantoapp.entidades;

import java.io.Serializable;

public class Publicacion implements Serializable {

    private int id_publicaciones;
    private String publicaciones;
    private String descripcion;
    private String cover;
    private String fecha_publicacion;
    private String autor;
    private String ftitulo;
    private String comentario;
    private String hora;
    private String fecha;
    private String email;
    private String avatar;


    public int getId_publicaciones() {
        return id_publicaciones;
    }

    public void setId_publicaciones(int id_publicaciones) {
        this.id_publicaciones = id_publicaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(String publicaciones) {
        this.publicaciones = publicaciones;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFtitulo() {
        return ftitulo;
    }

    public void setFtitulo(String ftitulo) {
        this.ftitulo = ftitulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
