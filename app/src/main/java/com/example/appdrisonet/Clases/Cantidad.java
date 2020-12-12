package com.example.appdrisonet.Clases;

public class Cantidad implements  Comparable<Cantidad> {

    String id_usuario;
    String nombre;
    String img_usuario;

    Integer cantidad_pendiente;
    int totalpapeletas;

        public  Cantidad(){

        }

    public Cantidad(String id_usuario, String nombre, String img_usuario, Integer cantidad_pendiente, int totalpapeletas) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.img_usuario = img_usuario;
        this.cantidad_pendiente = cantidad_pendiente;
        this.totalpapeletas = totalpapeletas;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public Integer getCantidad_pendiente() {
        return cantidad_pendiente;
    }

    public void setCantidad_pendiente(Integer cantidad_pendiente) {
        this.cantidad_pendiente = cantidad_pendiente;
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

    public String getImg_usuario() {
        return img_usuario;
    }

    public void setImg_usuario(String img_usuario) {
        this.img_usuario = img_usuario;
    }



    public int getTotalpapeletas() {
        return totalpapeletas;
    }

    public void setTotalpapeletas(int totalpapeletas) {
        this.totalpapeletas = totalpapeletas;
    }

    @Override
    public int compareTo(Cantidad o) {
      // return nombre.compareTo(o.getNombre());
        return  cantidad_pendiente.compareTo(o.getCantidad_pendiente());
      //  return  String.valueOf( cantidad_pendiente).compareTo(String.valueOf(o.cantidad_pendiente));
          //  return cantidad_pendiente.compareTo(o.getCantidad_pendiente());
    }
}
