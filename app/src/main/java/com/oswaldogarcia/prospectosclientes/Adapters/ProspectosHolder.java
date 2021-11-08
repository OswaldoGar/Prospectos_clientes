package com.oswaldogarcia.prospectosclientes.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.oswaldogarcia.prospectosclientes.R;
import androidx.recyclerview.widget.RecyclerView;

public class ProspectosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView NombreProspecto;
    TextView EstatusProspecto;
    Button ButtonDetalles;

    public ProspectosHolder(View itemView){
        super(itemView);

        NombreProspecto = (TextView) itemView.findViewById(R.id.NombreProspecto);
        EstatusProspecto = (TextView) itemView.findViewById(R.id.EstatusProspecto);
        ButtonDetalles = (Button) itemView.findViewById(R.id.BtnVerDetalles);
    }

    @Override
    public void onClick(View v) {

    }
}
