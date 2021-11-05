package com.oswaldogarcia.prospectosclientes.BD;

import android.provider.BaseColumns;

public class ProspectoSchema {

    public abstract class ProspectEntry implements BaseColumns{
        public static final String TABLE_NAME = "Prospecto";


        public static final String ID = "ID";
        public static final String NOMBRE = "nombre";
        public static final String PRIMER_APELLIDO = "primer_apellido";
        public static final String SEGUNDO_APELLIDO = "segundo_apellido";
        public static final String CALLE = "calle";
        public static final String NUMERO = "numero";
        public static final String COLONIA = "colonia";
        public static final String CODIGO_POSTAL = "cp";
        public static final String TELEFONO = "telefono";
        public static final String RFC = "rfc";
        public static final String ESTATUS = "estatus";
        public static final String COMENTARIO_RECHAZO = "comentario_rechazo";



    }

}
