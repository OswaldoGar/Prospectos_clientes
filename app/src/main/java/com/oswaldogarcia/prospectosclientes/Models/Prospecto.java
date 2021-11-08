package com.oswaldogarcia.prospectosclientes.Models;

import java.util.UUID;

public class Prospecto {
    private String ID;
    private String Nombre;
    private String PrimerApellido;
    private String SegundoApellido;
    private String Calle;
    private String Numero;
    private String Colonia;
    private String CodigoPostal;
    private String Telefono;
    private String RFC;
    private int Estatus;

    public Prospecto(String id, String nombre, String primerApellido, String segundoApellido, String calle,
                     String numero, String colonia, String codigoPostal, String telefono, String rfc,
                     int estatus){

        this.ID = id;
        this.Nombre = nombre;
        this.PrimerApellido = primerApellido;
        this.SegundoApellido = segundoApellido;
        this.Calle = calle;
        this.Numero = numero;
        this.Colonia = colonia;
        this.CodigoPostal = codigoPostal;
        this.Telefono = telefono;
        this.RFC = rfc;
        this.Estatus = estatus;
    }

    public String getID(){
        return ID;
    }
    public String getNombre(){
        return Nombre;
    }
    public String getPrimerApellido(){
        return PrimerApellido;
    }
    public String getSegundoApellido(){
        return SegundoApellido;
    }
    public String getCalle(){
        return Calle;
    }
    public String getNumero(){
        return Numero;
    }
    public String getColonia(){
        return Colonia;
    }
    public String getTelefono(){
        return Telefono;
    }
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    public String getRFC(){
        return RFC;
    }
    public int getEstatus(){return Estatus;}

    public enum ESTATUS_PROSPECTO{
        RECHAZADO,
        ACEPTADO,
        ENVIADO
    }
}
