package com.example.appdrisonet.Clases;

public class SubChat {

    String id_empresa;
    String nombre_usuario;
    String id_usuario;
    String fecha;
    String image_usuario;
    String mensaje;

    public  SubChat(){

    }
    public SubChat(String id_empresa, String nombre_usuario, String id_usuario, String fecha,String image_usuario,String mensaje) {
        this.id_empresa = id_empresa;
        this.nombre_usuario = nombre_usuario;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.image_usuario=image_usuario;
        this.mensaje=mensaje;

    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
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

    public String getImage_usuario() {
        return image_usuario;
    }

    public void setImage_usuario(String image_usuario) {
        this.image_usuario = image_usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
