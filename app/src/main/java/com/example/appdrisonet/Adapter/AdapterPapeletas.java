package com.example.appdrisonet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.Clases.Noticias;
import com.example.appdrisonet.Clases.Papeleta;
import com.example.appdrisonet.R;

import java.util.ArrayList;

public class AdapterPapeletas extends RecyclerView.Adapter<AdapterPapeletas.ViewHolderDatos> {


    ArrayList<Papeleta> listaPapeleta;
    Context context;

    public AdapterPapeletas(Context context,ArrayList<Papeleta> listaPapeleta) {
        this.listaPapeleta = listaPapeleta;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterPapeletas.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_papeleta,parent,false);

        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPapeletas.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos items =(ViewHolderDatos)holder;
            items.tvestado.setText(listaPapeleta.get(position).getEstado_deuda());
            items.tvfecha.setText(listaPapeleta.get(position).getFecha());
            items.tvpropietario.setText(listaPapeleta.get(position).getPropietario());

        }
    }

    @Override
    public int getItemCount() {
        return listaPapeleta.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView tvestado,tvfecha,tvpropietario;
        ImageView imgnoticia,imgperfil;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvestado=(TextView)itemView.findViewById(R.id.estado);
            tvfecha=(TextView)itemView.findViewById(R.id.fecha);
            tvpropietario=(TextView)itemView.findViewById(R.id.propietario);
        }
    }
}
