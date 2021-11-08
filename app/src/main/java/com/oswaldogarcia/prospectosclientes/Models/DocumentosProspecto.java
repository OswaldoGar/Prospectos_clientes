package com.oswaldogarcia.prospectosclientes.Models;

import java.sql.Blob;

public class DocumentosProspecto {
    private String IDUsuario;
    private String NombreDocumento;
    private byte[] ImageDocumento;


    public DocumentosProspecto(String idUsuario, String nombreDocumento, byte[] imageDocumento){
        this.IDUsuario = idUsuario;
        this.NombreDocumento = nombreDocumento;
        this.ImageDocumento = imageDocumento;
    }

    public String getIDUsuario(){
        return IDUsuario;
    }
    public String getNombreDocumento(){
        return NombreDocumento;
    }
    public byte[] getImageDocumento(){
        return ImageDocumento;
    }

}
