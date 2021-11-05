package com.oswaldogarcia.prospectosclientes.BD;

import android.provider.BaseColumns;

public class DocumentoSchema {
    public abstract class DocumentoEntry implements BaseColumns{
        public static final String TABLE_NAME = "Documentos";

        public static final String ID_USUARIO = "id_usuario";
        public static final String NOMBRE = "nombre";
        public static final String TIPO = "tipo";
        public static final String IMG_DOCUMENTO = "documento";

    }
}
