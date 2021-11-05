package com.oswaldogarcia.prospectosclientes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oswaldogarcia.prospectosclientes.BD.BDHelper;
import com.oswaldogarcia.prospectosclientes.Models.Prospecto;
import com.oswaldogarcia.prospectosclientes.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_prospecto);

        ButtonGuardar = (Button)findViewById(R.id.buttonAgregar);
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


    }

    private void Cancel() {

    }

    private void AddProspecto(View v) {
        try {
            if (!Validations())
                return;


            Prospecto prospecto = new Prospecto(EdtNombre.getText().toString(),
                    EdtPrimerApellido.getText().toString(),
                    EdtSegundoApellido.getText().toString(),
                    EdtCalle.getText().toString(),
                    EdtNumero.getText().toString(),
                    EdtColonia.getText().toString(),
                    EdtCodigoPostal.getText().toString(),
                    EdtTelefono.getText().toString(),
                    EdtRFC.getText().toString());
            //long id = bdHelper.InsertProspecto(prospecto);
        } catch (Exception e){
            Toast.makeText(this, "Ourrió un error al registra al prospecto, reintenta más tarde.", Toast.LENGTH_SHORT).show();
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