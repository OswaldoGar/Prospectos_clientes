package com.oswaldogarcia.prospectosclientes.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oswaldogarcia.prospectosclientes.Adapters.DocumentoAdapter;
import com.oswaldogarcia.prospectosclientes.Adapters.DocumentoHolder;
import com.oswaldogarcia.prospectosclientes.BD.BDHelper;
import com.oswaldogarcia.prospectosclientes.Models.DocumentosProspecto;
import com.oswaldogarcia.prospectosclientes.Models.Prospecto;
import com.oswaldogarcia.prospectosclientes.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AgregarProspecto extends AppCompatActivity {

    EditText EdtNombre;
    EditText EdtPrimerApellido;
    EditText EdtSegundoApellido;
    EditText EdtTelefono;
    EditText EdtRFC;
    EditText EdtCalle;
    EditText EdtNumero;
    EditText EdtColonia;
    EditText EdtCodigoPostal;
    Button ButtonGuardar;
    Button ButtonCancelar;
    Button ButtonAgregarDocumento;
    Dialog DialogAgregarDoc = null;
    byte[] DocumentoAgregado = null;
    Uri imageUri;
    ArrayList<DocumentosProspecto> DocumentosAgregadosList = new ArrayList<>();
    Context Context;
    RecyclerView RecyclerDocumentos;
    DocumentoAdapter _DocumentoAdapter;
    BDHelper HelperBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context = this;

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_agregar_prospecto);
        HelperBD = new BDHelper(this);

        _DocumentoAdapter = new DocumentoAdapter(Context, DocumentosAgregadosList);
        RecyclerDocumentos = (RecyclerView)findViewById(R.id.recyclerDocumentos);
        RecyclerDocumentos.setLayoutManager(new LinearLayoutManager(Context));
        RecyclerDocumentos.setAdapter(_DocumentoAdapter);
        ButtonGuardar = (Button)findViewById(R.id.buttonGuardar);
        ButtonAgregarDocumento = (Button)findViewById(R.id.buttonAgregarDocumento);
        ButtonCancelar = (Button)findViewById(R.id.buttonCancelar);
        EdtNombre = (EditText)findViewById(R.id.EditNombre);
        EdtPrimerApellido = (EditText)findViewById(R.id.EditPrimerA);
        EdtSegundoApellido = (EditText)findViewById(R.id.EditSegundoA);
        EdtTelefono = (EditText)findViewById(R.id.EditTelefono);
        EdtRFC = (EditText)findViewById(R.id.EditRFC);
        EdtCalle = (EditText)findViewById(R.id.EditCalle);
        EdtNumero = (EditText)findViewById(R.id.EditNumero);
        EdtColonia = (EditText)findViewById(R.id.EditColonia);
        EdtCodigoPostal = (EditText)findViewById(R.id.EditCP);

        ButtonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProspecto(v);
            }
        });

        ButtonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });

        ButtonAgregarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDocumento(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            assert data != null;
            imageUri = data.getData();
            InputStream iStream = null;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.WEBP, 50, stream);
                DocumentoAgregado = stream.toByteArray();

                //iStream = getContentResolver().openInputStream(imageUri);
                //DocumentoAgregado = getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    /**
     * OBTIENE UN ARREGLO DE BYTES PARA GUARDARLO EN LA LISTA DE DOCUMENTOS
     * @param inputStream
     * @return BYTES[]
     * @throws IOException
     */
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    /**
     * ABRE UN DIALOG PARA AGREAGAR UN NUEVO DOCUMENTO Y LO AGREGA A UNA LISTA
     * @param v
     */
    private void AddDocumento(View v) {
        DialogAgregarDoc = new Dialog(this);
        DialogAgregarDoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogAgregarDoc.setCancelable(false);
        DialogAgregarDoc.setContentView(R.layout.dialog_agregar_documento);

        ((Button)DialogAgregarDoc.findViewById(R.id.ButtonSeleccionarDocumento)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);
            }
        });

        ((Button)DialogAgregarDoc.findViewById(R.id.buttonCancelar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAgregarDoc.hide();
            }
        });

        ((Button)DialogAgregarDoc.findViewById(R.id.buttonAgregar)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                EditText NombreDoc = (EditText)DialogAgregarDoc.findViewById(R.id.EditNombreDocumento);

                if (TextUtils.isEmpty(NombreDoc.getText())){
                    Toast.makeText(DialogAgregarDoc.getContext(), "Debes ingresar el nombre del documento", Toast.LENGTH_SHORT).show();
                    NombreDoc.requestFocus();
                    return;
                }

                if (DocumentoAgregado == null){
                    Toast.makeText(DialogAgregarDoc.getContext(), "Debes seleccionar un documento", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentosProspecto documento = new DocumentosProspecto("", NombreDoc.getText().toString(), DocumentoAgregado);
                DocumentosAgregadosList.add(documento);
                _DocumentoAdapter.notifyDataSetChanged();
                DocumentoAgregado = null;
                DialogAgregarDoc.hide();
            }
        });

        DialogAgregarDoc.show();
    }

    /**
     * CANCELA EL REGISTRO DE UN PROSPECTO
     */
    private void Cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de salir, los cambios no se guardarán?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("No", null);
        builder.create();
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Cancel();
        return false;
    }

    /**
     * REGISTRA UN NUEVO PROSPECTO
     * @param v
     */
    private void AddProspecto(View v) {
        try {
            if (!Validations())
                return;
            Prospecto prospecto = new Prospecto(null,
                    EdtNombre.getText().toString(),
                    EdtPrimerApellido.getText().toString(),
                    EdtSegundoApellido.getText().toString(),
                    EdtCalle.getText().toString(),
                    EdtNumero.getText().toString(),
                    EdtColonia.getText().toString(),
                    EdtCodigoPostal.getText().toString(),
                    EdtTelefono.getText().toString(),
                    EdtRFC.getText().toString(),
                    Prospecto.ESTATUS_PROSPECTO.ENVIADO.ordinal());
            long idRegister = HelperBD.InsertProspecto(prospecto);

            if (idRegister != -1){
                for(int i = 0; i < DocumentosAgregadosList.size(); i ++){
                    DocumentosProspecto documentosProspecto = new DocumentosProspecto(Long.toString(idRegister),
                            DocumentosAgregadosList.get(i).getNombreDocumento(),
                            DocumentosAgregadosList.get(i).getImageDocumento());

                    HelperBD.InsertDocumento(documentosProspecto, idRegister);
                }
                Toast.makeText(this, "Prospecto insertado con exito.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ourrió un error al registrar al prospecto, reintenta más tarde.", Toast.LENGTH_SHORT).show();
            }



        } catch (Exception e){
            Toast.makeText(this, "Ourrió un error al registrar al prospecto, reintenta más tarde.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * VALIDA QUE LOS CAMPOS OBLIGATORIOS ESTEN LLENOS
     * @return Boolean
     */
    private boolean Validations() {
        if (TextUtils.isEmpty(EdtNombre.getText())){
            Toast.makeText(this, "Debes ingresar el nombre del prospecto", Toast.LENGTH_SHORT).show();
            EdtNombre.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtPrimerApellido.getText())){
            Toast.makeText(this, "Debes ingresar el primer apellido del prospecto", Toast.LENGTH_SHORT).show();
            EdtPrimerApellido.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtTelefono.getText())){
            Toast.makeText(this, "Debes ingresar el teléfono del prospecto", Toast.LENGTH_SHORT).show();
            EdtTelefono.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtRFC.getText())){
            Toast.makeText(this, "Debes ingresar el RFC del prospecto", Toast.LENGTH_SHORT).show();
            EdtRFC.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtCalle.getText())){
            Toast.makeText(this, "Debes ingresar la calle del prospecto", Toast.LENGTH_SHORT).show();
            EdtCalle.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtNumero.getText())){
            Toast.makeText(this, "Debes ingresar el número de domicilio del prospecto", Toast.LENGTH_SHORT).show();
            EdtNumero.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtColonia.getText())){
            Toast.makeText(this, "Debes ingresar la colonia del prospecto", Toast.LENGTH_SHORT).show();
            EdtColonia.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(EdtCodigoPostal.getText())){
            Toast.makeText(this, "Debes ingresar el código postal del prospecto", Toast.LENGTH_SHORT).show();
            EdtCodigoPostal.requestFocus();
            return false;
        }
        return true;
    }
}