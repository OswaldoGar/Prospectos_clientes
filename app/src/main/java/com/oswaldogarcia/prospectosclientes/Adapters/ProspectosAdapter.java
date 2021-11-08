package com.oswaldogarcia.prospectosclientes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oswaldogarcia.prospectosclientes.Activities.AgregarProspecto;
import com.oswaldogarcia.prospectosclientes.Activities.DetallesProspecto;
import com.oswaldogarcia.prospectosclientes.Models.Prospecto;
import com.oswaldogarcia.prospectosclientes.R;

import java.util.ArrayList;

public class ProspectosAdapter extends RecyclerView.Adapter<ProspectosHolder> {
    Context c;
    ArrayList<Prospecto> Prospectos;

    public ProspectosAdapter(Context c, ArrayList<Prospecto> prospectos){
        this.c = c;
        this.Prospectos = prospectos;
    }

    @NonNull
    @Override
    public ProspectosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_prospecto, parent, false);

        return new ProspectosHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProspectosHolder holder, int position) {
        Button buttonDetalle = holder.ButtonDetalles;
        buttonDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerDetallesProspecto(holder.getAdapterPosition());
            }
        });
        String nombreConc = Prospectos.get(position).getNombre() + " " + Prospectos.get(position).getPrimerApellido() + " " + Prospectos.get(position).getSegundoApellido();
        String estatus = "";

        if (Prospectos.get(position).getEstatus() == Prospecto.ESTATUS_PROSPECTO.ENVIADO.ordinal())
            estatus = "Enviado";
        else if (Prospectos.get(position).getEstatus() == Prospecto.ESTATUS_PROSPECTO.ACEPTADO.ordinal())
            estatus = "Aceptado";
        else
            estatus = "Rechazado";

        holder.NombreProspecto.setText(nombreConc);
        holder.EstatusProspecto.setText(estatus);
    }

    private void VerDetallesProspecto(int position) {
        Prospecto p = Prospectos.get(position);
        Intent intent = new Intent(c, DetallesProspecto.class);
        intent.putExtra("idSeleccionado", p.getID());
        c.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return Prospectos.size();
    }
}
