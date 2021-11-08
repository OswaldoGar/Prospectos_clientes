package com.oswaldogarcia.prospectosclientes.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.oswaldogarcia.prospectosclientes.Adapters.ProspectosAdapter;
import com.oswaldogarcia.prospectosclientes.BD.BDHelper;
import com.oswaldogarcia.prospectosclientes.BD.ProspectoSchema;
import com.oswaldogarcia.prospectosclientes.Models.Prospecto;
import com.oswaldogarcia.prospectosclientes.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BDHelper HelperBD;
    RecyclerView _RecyclerProspectos;
    ProspectosAdapter _ProspectosAdapter;
    ArrayList<Prospecto> ProspectosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

        HelperBD = new BDHelper(this);

        _ProspectosAdapter = new ProspectosAdapter(this, ProspectosList);
        _RecyclerProspectos = (RecyclerView) findViewById(R.id.RecyclerProspectos);
        _RecyclerProspectos.setLayoutManager(new LinearLayoutManager(this));
        _RecyclerProspectos.setAdapter(_ProspectosAdapter);


        //LoadProspectos();

        final Button button = (Button) findViewById(R.id.buttonAgregar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenAdd(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    private void LoadProspectos() {
        Cursor c = HelperBD.GetAllProspectos();

        if (!c.moveToFirst()){
            c.close();
            return;
        }
        ProspectosList.clear();
        do {
            int id = c.getInt(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.ID));
            String nombre = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.NOMBRE));
            String primerA = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.PRIMER_APELLIDO));
            String segundoA = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.SEGUNDO_APELLIDO));
            String calle = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.CALLE));
            String numero = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.NUMERO));
            String colonia = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.COLONIA));
            String codigoPostal = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.CODIGO_POSTAL));
            String telefono = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.TELEFONO));
            String rfc = c.getString(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.RFC));
            int estatus = c.getInt(c.getColumnIndexOrThrow(ProspectoSchema.ProspectEntry.ESTATUS));
            Prospecto prospecto = new Prospecto(String.valueOf(id), nombre, primerA, segundoA, calle, numero,colonia,codigoPostal,telefono,rfc,estatus);
      /*      boolean exists = ProspectosList.stream().anyMatch(i -> i.getID().equals(prospecto.getID()));
            if (!exists)*/
                ProspectosList.add(prospecto);
        } while (c.moveToNext());

        _ProspectosAdapter.notifyDataSetChanged();
    }

    private void OpenAdd(View v) {
        Intent intent = new Intent(this, AgregarProspecto.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        //ProspectosList = new ArrayList<>();
        LoadProspectos();
        super.onResume();
    }
}