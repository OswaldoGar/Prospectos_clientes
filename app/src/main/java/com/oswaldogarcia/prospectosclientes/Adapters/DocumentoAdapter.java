package com.oswaldogarcia.prospectosclientes.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oswaldogarcia.prospectosclientes.Models.DocumentosProspecto;
import com.oswaldogarcia.prospectosclientes.R;

import java.util.ArrayList;
import java.util.List;

public class DocumentoAdapter extends RecyclerView.Adapter<DocumentoHolder> {
    Context c;
    ArrayList<DocumentosProspecto> Documentos;
    Dialog DialogVerDetalles;

    public DocumentoAdapter(Context c, ArrayList<DocumentosProspecto> documentos){
        this.c = c;
        this.Documentos = documentos;
    }

    @NonNull
    @Override
    public DocumentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_documento_prospecto, parent, false);

        return new DocumentoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentoHolder holder, int position) {
        holder.NombreDocumento.setText(Documentos.get(position).getNombreDocumento());

        Button buttonVerDocumento = holder.ButtonVerDetalles;
        buttonVerDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerDetallesDocumento(holder.getAdapterPosition());
            }
        });

    }

    private void VerDetallesDocumento(int position) {
        DocumentosProspecto doc = Documentos.get(position);
        DialogVerDetalles = new Dialog(c);
        DialogVerDetalles.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogVerDetalles.setCancelable(false);
        DialogVerDetalles.setContentView(R.layout.dialog_ver_documento);

        ((TextView)DialogVerDetalles.findViewById(R.id.NombreDocumento)).setText(doc.getNombreDocumento());

        ImageView imageD = (ImageView)DialogVerDetalles.findViewById(R.id.ImageDocumento);

        byte[] arrayD = doc.getImageDocumento();

        Bitmap bMap = BitmapFactory.decodeByteArray(arrayD, 0, arrayD.length);
        imageD.setImageBitmap(bMap);

        ((Button)DialogVerDetalles.findViewById(R.id.buttonCerrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogVerDetalles.hide();
            }
        });

        DialogVerDetalles.show();

    }

    @Override
    public int getItemCount() {
        return Documentos.size();
    }

}
