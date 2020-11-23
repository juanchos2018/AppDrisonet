package com.example.appdrisonet.Clases;

public class Solicitud {
    String nombre_usuario;
    String id_usuario;
    String fecha;
    String dni_usuario;
    String key_solicitud;
    String img_usuario;
    String token;
    String estado;
    public Solicitud(String nombre_usuario, String id_usuario, String fecha, String dni_usuario, String key_solicitud,String img_usuario,String token,String estado) {
        this.nombre_usuario = nombre_usuario;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.dni_usuario = dni_usuario;
        this.key_solicitud = key_solicitud;
        this.img_usuario=img_usuario;
        this.token=token;
        this.estado=estado;

    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDni_usuario() {
        return dni_usuario;
    }

    public void setDni_usuario(String dni_usuario) {
        this.dni_usuario = dni_usuario;
    }

    public String getKey_solicitud() {
        return key_solicitud;
    }

    public void setKey_solicitud(String key_solicitud) {
        this.key_solicitud = key_solicitud;
    }

    public String getImg_usuario() {
        return img_usuario;
    }

    public void setImg_usuario(String img_usuario) {
        this.img_usuario = img_usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
