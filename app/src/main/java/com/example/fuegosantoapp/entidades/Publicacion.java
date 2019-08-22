package com.example.fuegosantoapp.entidades;

public class Publicacion {

    private int id_publicaciones;
    private String publicaciones;
    private String cover;
    private String fecha_publicacion;
    private String autor;
    private String ftitulo;

    public int getId_publicaciones() {
        return id_publicaciones;
    }

    public void setId_publicaciones(int id_publicaciones) {
        this.id_publicaciones = id_publicaciones;
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
}
