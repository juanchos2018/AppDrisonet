package com.example.appdrisonet.Clases;

import java.util.ArrayList;

public class Papeleta {

    private String conductor;
    private String estado_deuda;
    private String fecha;
    private String importe;
    private String infraccion;
    private String propietario;
    private String pt_cod_papeleta;
    private String pt_dni_conductor;
    private String pt_dni_propietario;
    private String pt_numero_licencia;
    private String serie_papeleta;
    private String id_usuario;
    public Papeleta() {
    }
    public Papeleta(String estado_deuda, String fecha, String importe, String propietario,String conductor,String id_usuario) {
        this.estado_deuda = estado_deuda;
        this.fecha = fecha;
        this.importe = importe;
        this.propietario = propietario;
        this.conductor=conductor;
        this.id_usuario=id_usuario;

    }

    public Papeleta(String conductor, String estado_deuda, String fecha, String importe, String infraccion, String propietario, String pt_cod_papeleta, String pt_dni_conductor, String pt_dni_propietario, String pt_numero_licencia, String serie_papeleta) {
        this.conductor = conductor;
        this.estado_deuda = estado_deuda;
        this.fecha = fecha;
        this.importe = importe;
        this.infraccion = infraccion;
        this.propietario = propietario;
        this.pt_cod_papeleta = pt_cod_papeleta;
        this.pt_dni_conductor = pt_dni_conductor;
        this.pt_dni_propietario = pt_dni_propietario;
        this.pt_numero_licencia = pt_numero_licencia;
        this.serie_papeleta = serie_papeleta;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getEstado_deuda() {
        return estado_deuda;
    }

    public void setEstado_deuda(String estado_deuda) {
        this.estado_deuda = estado_deuda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getPt_cod_papeleta() {
        return pt_cod_papeleta;
    }

    public void setPt_cod_papeleta(String pt_cod_papeleta) {
        this.pt_cod_papeleta = pt_cod_papeleta;
    }

    public String getPt_dni_conductor() {
        return pt_dni_conductor;
    }

    public void setPt_dni_conductor(String pt_dni_conductor) {
        this.pt_dni_conductor = pt_dni_conductor;
    }

    public String getPt_dni_propietario() {
        return pt_dni_propietario;
    }

    public void setPt_dni_propietario(String pt_dni_propietario) {
        this.pt_dni_propietario = pt_dni_propietario;
    }

    public String getPt_numero_licencia() {
        return pt_numero_licencia;
    }

    public void setPt_numero_licencia(String pt_numero_licencia) {
        this.pt_numero_licencia = pt_numero_licencia;
    }

    public String getSerie_papeleta() {
        return serie_papeleta;
    }

    public void setSerie_papeleta(String serie_papeleta) {
        this.serie_papeleta = serie_papeleta;
    }
}
