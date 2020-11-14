package com.example.appdrisonet.Clases;

public class MensajeRecibir extends Mensaje {
    private String hora;

    public MensajeRecibir() {
    }

    public MensajeRecibir(String hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, String hora) {
        super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
