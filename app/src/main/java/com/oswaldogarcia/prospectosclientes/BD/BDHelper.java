package com.oswaldogarcia.prospectosclientes.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.oswaldogarcia.prospectosclientes.Models.DocumentosProspecto;
import com.oswaldogarcia.prospectosclientes.Models.Prospecto;

public class BDHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Prospectos.db";

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ProspectoSchema.ProspectEntry.TABLE_NAME +
                "(" +
                ProspectoSchema.ProspectEntry.ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProspectoSchema.ProspectEntry.NOMBRE + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.PRIMER_APELLIDO + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.SEGUNDO_APELLIDO + "TEXT," +
                ProspectoSchema.ProspectEntry.CALLE + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.NUMERO + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.COLONIA + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.CODIGO_POSTAL + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.TELEFONO + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.RFC + "TEXT NOT NULL,"+
                ProspectoSchema.ProspectEntry.ESTATUS + "TEXT NOT NULL," +
                ProspectoSchema.ProspectEntry.COMENTARIO_RECHAZO + "TEXT" +
                ")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DocumentoSchema.DocumentoEntry.TABLE_NAME +
                "(" +
                DocumentoSchema.DocumentoEntry.ID_USUARIO + "INTEGER NOT NULL," +
                DocumentoSchema.DocumentoEntry.NOMBRE + "TEXT NOT NULL," +
                DocumentoSchema.DocumentoEntry.IMG_DOCUMENTO + "BLOB NOT NULL"+
                ")"
        );

    }

    /**
     * INSERTA UN PROSPECTO Y DEVUELVE EL ID INSERTADO
     * @param prospecto
     * @return long
     */
    public long InsertProspecto(Prospecto prospecto){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProspectoSchema.ProspectEntry.ID, prospecto.getID());
        contentValues.put(ProspectoSchema.ProspectEntry.NOMBRE, prospecto.getNombre());
        contentValues.put(ProspectoSchema.ProspectEntry.PRIMER_APELLIDO, prospecto.getPrimerApellido());
        contentValues.put(ProspectoSchema.ProspectEntry.SEGUNDO_APELLIDO, prospecto.getSegundoApellido());
        contentValues.put(ProspectoSchema.ProspectEntry.CALLE, prospecto.getCalle());
        contentValues.put(ProspectoSchema.ProspectEntry.NUMERO, prospecto.getNumero());
        contentValues.put(ProspectoSchema.ProspectEntry.COLONIA, prospecto.getColonia());
        contentValues.put(ProspectoSchema.ProspectEntry.CODIGO_POSTAL, prospecto.getCodigoPostal());
        contentValues.put(ProspectoSchema.ProspectEntry.TELEFONO, prospecto.getTelefono());
        contentValues.put(ProspectoSchema.ProspectEntry.RFC, prospecto.getRFC());
        contentValues.put(ProspectoSchema.ProspectEntry.ESTATUS, String.valueOf(Prospecto.ESTATUS_PROSPECTO.ENVIADO));
        long id = db.insert(ProspectoSchema.ProspectEntry.TABLE_NAME, null, contentValues);
        return id;
    }

    /**
     * INSERTA UN NUEVO DOCUMENTO ASOCIANDOLO CON EL ID DE USUARIO
     * @param documentosProspecto
     * @param idUsuario
     * @return long
     */
    public boolean InsertDocumento(DocumentosProspecto documentosProspecto, long idUsuario){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DocumentoSchema.DocumentoEntry.ID_USUARIO, idUsuario);
        contentValues.put(DocumentoSchema.DocumentoEntry.NOMBRE, documentosProspecto.getNombreDocumento());
        contentValues.put(DocumentoSchema.DocumentoEntry.IMG_DOCUMENTO, documentosProspecto.getImageDocumento());

        long res = db.insert(DocumentoSchema.DocumentoEntry.TABLE_NAME, null, contentValues);

        return res != -1;
    }

    /**
     * OBTIENE TODOS LOS PROSPECTOS
     * @return Cursor
     */
    public Cursor GetAllProspectos(){
        SQLiteDatabase db = getReadableDatabase();

        return  db.query(ProspectoSchema.ProspectEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * OBTIENE UN PROSPECTO POR EL ID
     * @param idUsuario
     * @return Cursor
     */
    public Cursor GetProspectoByID(long idUsuario){
        SQLiteDatabase db = getReadableDatabase();
        String selection = ProspectoSchema.ProspectEntry.ID + " WHERE ?";
        String[] selectionArgs = new String[]{"" + idUsuario};
        return  db.query(ProspectoSchema.ProspectEntry.TABLE_NAME, null,
                selection,
                selectionArgs,
                null,
                null,
                null
                );
    }

    /**
     * ACTUALIZA LA INFORMACION DE UN PROSPECTO
     * @param idUsuario
     * @param estatus
     * @param comentarioRechazo
     * @return BOOLEAN
     */
    public boolean UpdateProspecto(long idUsuario, String estatus, String comentarioRechazo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String selection = ProspectoSchema.ProspectEntry.ID + " WHERE ?";
        String[] selectionArgs = new String[]{"" + idUsuario};

        contentValues.put(ProspectoSchema.ProspectEntry.ESTATUS, estatus);
        contentValues.put(ProspectoSchema.ProspectEntry.COMENTARIO_RECHAZO, comentarioRechazo);

        long res = db.update(ProspectoSchema.ProspectEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        return res > 0;
    }

    /**
     * OBTIENE LOS DOCUMENTOS REGISTRADOS POR UN PROSPECTO
     * @param idUsuario
     * @return
     */
    public Cursor GetDocumentoByidUsuario(long idUsuario){
        SQLiteDatabase db = getReadableDatabase();
        String selection = DocumentoSchema.DocumentoEntry.ID_USUARIO + " WHERE ?";
        String[] selectionArgs = new String[]{"" + idUsuario};
        return  db.query(DocumentoSchema.DocumentoEntry.TABLE_NAME, null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ProspectoSchema.ProspectEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DocumentoSchema.DocumentoEntry.TABLE_NAME);
        onCreate(db);
    }
}
