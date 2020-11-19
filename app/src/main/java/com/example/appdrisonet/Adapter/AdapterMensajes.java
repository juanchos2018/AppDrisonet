package com.example.appdrisonet.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.Clases.HolderMensaje;
import com.example.appdrisonet.Clases.MensajeRecibir;
import com.example.appdrisonet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterMensajes   extends RecyclerView.Adapter<HolderMensaje>{

    private List<MensajeRecibir> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(MensajeRecibir m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_chat,parent,false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
    //    holder.ge
        if(listMensaje.get(position).getType_mensaje().equals("2")){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        }else if(listMensaje.get(position).getType_mensaje().equals("1")){
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

     //   if (listMensaje.get(position).getFotoPerfil().equals("default_image")){
     //       Log.e("fogo","defautl ime");
     //
     //       holder.getFotoMensajePerfil().setImageResource(R.drawable.default_profile_image);
     //
     //   }else{
     //     //  Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensaje());
             Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
     //   }

      //  Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensaje());
      //  Long codigoHora = listMensaje.get(position).getHora();
      //  Date d = new Date(codigoHora);
      //  SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm o am
      //  holder.getHora().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
