package com.oswaldogarcia.prospectosclientes.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oswaldogarcia.prospectosclientes.Adapters.DocumentoAdapter;
import com.oswaldogarcia.prospectosclientes.BD.BDHelper;
import com.oswaldogarcia.prospectosclientes.BD.DocumentoSchema;
import com.oswaldogarcia.prospectosclientes.BD.ProspectoSchema;
import com.oswaldogarcia.prospectosclientes.Models.DocumentosProspecto;
import com.oswaldogarcia.prospectosclientes.Models.Prospecto;
import com.oswaldogarcia.prospectosclientes.R;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetallesProspecto extends AppCompatActivity {
    String IdSeleccionado;
    TextView NombreProspecto;
    TextView DireccionProspecto;
    TextView RFCProspecto;
    TextView TelefonoProspecto;
    TextView TituloDocumentos;
    TextView ComentarioRechazo;
    LinearLayout LinearComentario;
    LinearLayout LinearBotones;
    Button ButtonAceptar;
    Button ButtonRechazar;
    BDHelper HelperBD;
    RecyclerView RecyclerDocumentos;
    DocumentoAdapter DocumentoAdapter;
    ArrayList<DocumentosProspecto> DocumentosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_prospecto);

        HelperBD = new BDHelper(this);

        IdSeleccionado = getIntent().getStringExtra("idSeleccionado");


        RecyclerDocumentos = (RecyclerView) findViewById(R.id.recyclerDocumentos);
        DocumentoAdapter = new DocumentoAdapter(this, DocumentosList);
        RecyclerDocumentos.setLayoutManager(new LinearLayoutManager(this));
        RecyclerDocumentos.setAdapter(DocumentoAdapter);

        NombreProspecto = (TextView) findViewById(R.id.NombreProspecto);
        DireccionProspecto = (TextView) findViewById(R.id.DireccionProspecto);
        RFCProspecto = (TextView) findViewById(R.id.RfcProspecto);
        TelefonoProspecto = (TextView) findViewById(R.id.TelefonoProspecto);
        TituloDocumentos = (TextView) findViewById(R.id.DocumentosTitulo);
        ComentarioRechazo = (TextView) findViewById(R.id.ComentarioProspecto);
        
        LinearComentario = (LinearLayout) findViewById(R.id.LinearComentario);
        LinearBotones = (LinearLayout) findViewById(R.id.LinearBotones);

        ButtonAceptar = (Button) findViewById(R.id.BtnAceptar);
        ButtonRechazar = (Button) findViewById(R.id.BtnRechazar);

        ButtonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceptarProspecto();
            }
        });

        ButtonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechazarProspecto();
            }
        });

        LoadInfoProspecto(IdSeleccionado);

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void RechazarProspecto() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        alert.setMessage("Ingresa un comentario de rechazo para el prospecto");
        alert.setTitle("Rechazar prospecto");

        alert.setView(edittext);

        alert.setPositiveButton("Rechazar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText())){
                    Toast.makeText(getApplicationContext(), "Debes ingresar un comentario de rechazo", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean res = HelperBD.UpdateProspecto(IdSeleccionado, Prospecto.ESTATUS_PROSPECTO.RECHAZADO.ordinal(), edittext.getText().toString());

                if (res){
                    Toast.makeText(getApplicationContext(), "Prospecto rechazado", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Ocurrió un error al rechazar el prospecto", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancelar", null);
        alert.show();
    }

    private void AceptarProspecto() {
        boolean res = HelperBD.UpdateProspecto(IdSeleccionado,
                Prospecto.ESTATUS_PROSPECTO.ACEPTADO.ordinal(),
                null);

        if (res){
            Toast.makeText(this, "Prospecto aceptado con exito", Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(this, "Ocurrió un error al aceptar el prospecto", Toast.LENGTH_SHORT).show();

    }

    private void LoadInfoProspecto(String idSeleccionado) {
        Cursor cursorProspecto = HelperBD.GetProspectoByID(idSeleccionado);

        if (!cursorProspecto.moveToFirst()){
            cursorProspecto.close();
            return;
        }

        Cursor cursorDocumentos = HelperBD.GetDocumentoByidUsuario(idSeleccionado);
        if (!cursorDocumentos.moveToFirst()){
            cursorDocumentos.close();
            return;
        }

        do {
            String nombreDocumento = cursorDocumentos.getString(cursorDocumentos.getColumnIndexOrThrow(DocumentoSchema.DocumentoEntry.NOMBRE));
            byte[] imagenDocumento = cursorDocumentos.getBlob(cursorDocumentos.getColumnIndexOrThrow(DocumentoSchema.DocumentoEntry.IMG_DOCUMENTO));
            DocumentosProspecto documentosProspecto = new DocumentosProspecto(idSeleccionado, nombreDocumento, imagenDocumento);
            DocumentosList.add(documentosProspecto);
        } while (cursorDocumentos.moveToNext());
        DocumentoAdapter.notifyDataSetChanged();

        String nombre = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.NOMBRE));
        String primerA = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.PRIMER_APELLIDO));
        String segundoA = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.SEGUNDO_APELLIDO));

        String calle = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.CALLE));
        String numero = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.NUMERO));
        String colonia = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.COLONIA));
        String codigoP = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.CODIGO_POSTAL));

        String rfc = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.RFC));
        String telefono = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.TELEFONO));

        int estatus = cursorProspecto.getInt(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.ESTATUS));
        String comentario = cursorProspecto.getString(cursorProspecto.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.COMENTARIO_RECHAZO));

        if (estatus == Prospecto.ESTATUS_PROSPECTO.ENVIADO.ordinal()){
            LinearBotones.setVisibility(View.VISIBLE);
            LinearComentario.setVisibility(View.INVISIBLE);
        } else if (estatus == Prospecto.ESTATUS_PROSPECTO.ACEPTADO.ordinal()){
            LinearBotones.setVisibility(View.INVISIBLE);
            LinearComentario.setVisibility(View.INVISIBLE);
        } else{
            LinearBotones.setVisibility(View.INVISIBLE);
            LinearComentario.setVisibility(View.VISIBLE);
            ComentarioRechazo.setText(comentario);
        }

        NombreProspecto.setText(nombre + " " + primerA + " " + segundoA);
        DireccionProspecto.setText(calle + ", " + numero + ", " + colonia + ", " + codigoP);
        RFCProspecto.setText(rfc);
        TelefonoProspecto.setText(telefono);

    }
}