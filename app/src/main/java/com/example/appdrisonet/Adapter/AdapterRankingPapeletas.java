package com.example.appdrisonet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.Clases.Cantidad;
import com.example.appdrisonet.R;

import java.util.ArrayList;

public class AdapterRankingPapeletas   extends RecyclerView.Adapter<AdapterRankingPapeletas.ViewHolderDatos> {

    ArrayList<Cantidad> listaranking;

    public AdapterRankingPapeletas(ArrayList<Cantidad> listaranking) {
        this.listaranking = listaranking;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rankingpapeletas,parent,false);

        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos items =(ViewHolderDatos)holder;
            items.nombretaxista.setText(listaranking.get(position).getNombre());

            Glide.with(holder.imgtaxista.getContext())
                    .load(listaranking.get(position).getImg_usuario())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(items.imgtaxista);
        }
    }

    @Override
    public int getItemCount() {
        return listaranking.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imgtaxista;
        TextView nombretaxista;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            imgtaxista=(ImageView)itemView.findViewById(R.id.fototaxista);
            nombretaxista=(TextView)itemView.findViewById(R.id.nombretaxista);
        }
    }
}
