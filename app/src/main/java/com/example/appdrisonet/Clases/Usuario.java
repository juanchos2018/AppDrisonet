package com.example.appdrisonet.Clases;

public class Usuario {
    String id_usuario;
    String nombre;
    String correo;
    String image_usuario;

    public  Usuario(){

    }

    public Usuario(String id_usuario, String nombre, String correo, String image_usuario) {
        this.id_usuario=id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.image_usuario = image_usuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImage_usuario() {
        return image_usuario;
    }

    public void setImage_usuario(String image_usuario) {
        this.image_usuario = image_usuario;
    }
}
