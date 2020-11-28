package com.example.appdrisonet.Clases;

public class Notificacion {

    String id_usuario;
    String nombre_empresa;
    String image_empresa;
    String fecha;
    String mensaje;
    String detalle;
    String  ruta_documento;
    String estado;
    public Notificacion(){

    }
    public Notificacion(String id_usuario, String nombre_empresa, String image_empresa, String fecha, String mensaje, String detalle, String ruta_documento,String estado) {
        this.id_usuario = id_usuario;
        this.nombre_empresa = nombre_empresa;
        this.image_empresa = image_empresa;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.detalle = detalle;
        this.ruta_documento = ruta_documento;
        this.estado=estado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getImage_empresa() {
        return image_empresa;
    }

    public void setImage_empresa(String image_empresa) {
        this.image_empresa = image_empresa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getRuta_documento() {
        return ruta_documento;
    }

    public void setRuta_documento(String ruta_documento) {
        this.ruta_documento = ruta_documento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
