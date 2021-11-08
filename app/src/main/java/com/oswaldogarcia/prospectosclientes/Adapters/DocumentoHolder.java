package com.oswaldogarcia.prospectosclientes.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.oswaldogarcia.prospectosclientes.R;

public class DocumentoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView NombreDocumento;
    Button ButtonVerDetalles;

    public DocumentoHolder(View itemView){
        super(itemView);

        NombreDocumento = (TextView) itemView.findViewById(R.id.NombreDocumento);
        ButtonVerDetalles = (Button) itemView.findViewById(R.id.buttonVerDocumento);
    }

    @Override
    public void onClick(View v) {

    }
}
