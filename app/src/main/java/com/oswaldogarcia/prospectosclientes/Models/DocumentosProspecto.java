package com.oswaldogarcia.prospectosclientes.Models;

import java.sql.Blob;

public class DocumentosProspecto {
    private String IDUsuario;
    private String NombreDocumento;
    private String Tipo;
    private byte[] ImageDocumento;


    public DocumentosProspecto(String idUsuario, String nombreDocumento,
                               String tipo, byte[] imageDocumento){
        this.IDUsuario = idUsuario;
        this.NombreDocumento = nombreDocumento;
        this.Tipo = tipo;
        this.ImageDocumento = imageDocumento;
    }

    public String getIDUsuario(){
        return IDUsuario;
    }
    public String getNombreDocumento(){
        return NombreDocumento;
    }
    public String getTipo(){
        return Tipo;
    }
    public byte[] getImageDocumento(){
        return ImageDocumento;
    }

}
