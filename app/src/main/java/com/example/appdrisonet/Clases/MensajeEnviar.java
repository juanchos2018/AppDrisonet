package com.example.appdrisonet.Clases;

import java.util.Map;

public class MensajeEnviar extends Mensaje {
    private String hora;

   // public MensajeEnviar() {
   //     this.hora="10:20";
   // }
//
   // public MensajeEnviar(String hora) {
   //     this.hora = hora;
   // }

    public MensajeEnviar(String mensaje, String nombre, String fotoPerfil, String type_mensaje,String id_usuario) {
        super(mensaje, nombre, fotoPerfil, type_mensaje,id_usuario);
        this.hora = hora;
    }


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
