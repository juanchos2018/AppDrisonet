package com.example.appdrisonet.Clases;

public class Noticias {

    String key_usuario;
    String nombre_usuario;
    String img_usuario;
    String key_noticia;
    String titulo_noticia;
    String descripcion_noticia;
    String fecha_noticia;
    String img_noticia;

    public Noticias(String key_usuario, String nombre_usuario, String img_usuario, String key_noticia, String titulo_noticia, String descripcion_noticia, String fecha_noticia, String img_noticia) {
        this.key_usuario = key_usuario;
        this.nombre_usuario = nombre_usuario;
        this.img_usuario = img_usuario;
        this.key_noticia = key_noticia;
        this.titulo_noticia = titulo_noticia;
        this.descripcion_noticia = descripcion_noticia;
        this.fecha_noticia = fecha_noticia;
        this.img_noticia = img_noticia;
    }

    public Noticias(){

    }
    public String getKey_usuario() {
        return key_usuario;
    }

    public void setKey_usuario(String key_usuario) {
        this.key_usuario = key_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getImg_usuario() {
        return img_usuario;
    }

    public void setImg_usuario(String img_usuario) {
        this.img_usuario = img_usuario;
    }

    public String getKey_noticia() {
        return key_noticia;
    }

    public void setKey_noticia(String key_noticia) {
        this.key_noticia = key_noticia;
    }

    public String getTitulo_noticia() {
        return titulo_noticia;
    }

    public void setTitulo_noticia(String titulo_noticia) {
        this.titulo_noticia = titulo_noticia;
    }

    public String getDescripcion_noticia() {
        return descripcion_noticia;
    }

    public void setDescripcion_noticia(String descripcion_noticia) {
        this.descripcion_noticia = descripcion_noticia;
    }

    public String getFecha_noticia() {
        return fecha_noticia;
    }

    public void setFecha_noticia(String fecha_noticia) {
        this.fecha_noticia = fecha_noticia;
    }

    public String getImg_noticia() {
        return img_noticia;
    }

    public void setImg_noticia(String img_noticia) {
        this.img_noticia = img_noticia;
    }
}
