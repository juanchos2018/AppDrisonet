package com.example.appdrisonet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdrisonet.Clases.Noticias;
import com.example.appdrisonet.R;

import java.util.ArrayList;

public class AdapterNoticias  extends RecyclerView.Adapter<AdapterNoticias.ViewHolderDatos> {


    ArrayList<Noticias> listaNoticias;

    public AdapterNoticias(ArrayList<Noticias> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias,parent,false);

        return new  ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos items =(ViewHolderDatos)holder;
            items.tvnombre_usu.setText(listaNoticias.get(position).getNombre_usuario());
            items.tvdescripcionnoticia.setText(listaNoticias.get(position).getDescripcion_noticia());



        }
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView tvnombre_usu,tvdescripcionnoticia;
        ImageView imgnoticia,imgperfil;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvnombre_usu=(TextView)itemView.findViewById(R.id.tvnombreusu);
            tvdescripcionnoticia=(TextView)itemView.findViewById(R.id.tvdescripcion);
        }
    }
}
