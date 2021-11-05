package com.oswaldogarcia.prospectosclientes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.oswaldogarcia.prospectosclientes.BD.BDHelper;
import com.oswaldogarcia.prospectosclientes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BDHelper b = new BDHelper(this);

        final Button button = (Button) findViewById(R.id.buttonAgregar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenAdd(v);
            }
        });


    }

    private void OpenAdd(View v) {
        Intent intent = new Intent(this, AgregarProspecto.class);
        startActivity(intent);
    }
}