package com.example.appdrisonet.Clases;

public class Reniec_Datos {

    private String id_ren ;
    private String nombre ;
    private String apellido ;
    private String dni ;


    public Reniec_Datos(String id_ren, String nombre, String apellido, String dni) {
        this.id_ren = id_ren;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public String getId_ren() {
        return id_ren;
    }

    public void setId_ren(String id_ren) {
        this.id_ren = id_ren;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
