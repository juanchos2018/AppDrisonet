package com.example.appdrisonet.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.Acitity.ChatActivity;
import com.example.appdrisonet.Acitity.MapaActivity;
import com.example.appdrisonet.Clases.Noticias;
import com.example.appdrisonet.Clases.Notificacion;
import com.example.appdrisonet.DialogoFratment.BottonSheetFragment;
import com.example.appdrisonet.DialogoFratment.BottonShetFragment2;
import com.example.appdrisonet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificacionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String user_id;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference referenceNoticia;
    public FirebaseUser currentUser;
    RecyclerView recycler;

    public NotificacionesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NotificacionesFragment newInstance(String param1, String param2) {
        NotificacionesFragment fragment = new NotificacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        recycler=vista.findViewById(R.id.numnotificaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        user_id =  mAuth.getCurrentUser().getUid();
        referenceNoticia= FirebaseDatabase.getInstance().getReference("MisNotificaciones").child(user_id);
        return vista;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Notificacion> recyclerOptions = new FirebaseRecyclerOptions.Builder<Notificacion>()
                .setQuery(referenceNoticia, Notificacion.class).build();
        FirebaseRecyclerAdapter<Notificacion, Items> adapter =new FirebaseRecyclerAdapter<Notificacion, Items>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull Notificacion tutores) {
                final String key = getRef(i).getKey();
                referenceNoticia.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Context context = getContext();
                        if (dataSnapshot.exists()){
                            final String empresa=dataSnapshot.child("nombre_empresa").getValue().toString();
                            final String mensaje=dataSnapshot.child("mensaje").getValue().toString();
                            final String detalle=dataSnapshot.child("detalle").getValue().toString();
                            final String rutafoto=dataSnapshot.child("image_empresa").getValue().toString();
                            final String fecha=dataSnapshot.child("fecha").getValue().toString();
                            final String estado=dataSnapshot.child("estado").getValue().toString();
                            final String ruta_documento=dataSnapshot.child("ruta_documento").getValue().toString();

                            items.tvnombre_usu.setText(empresa);
                            items.tvdescripcionnoticia.setText(mensaje);

                            if (rutafoto.equals("default_image")){
                                items.imgperfil.setImageResource(R.drawable.ic_launcher_background);
                            }else {
                                Glide.with(getContext().getApplicationContext())
                                        .load(rutafoto)
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .fitCenter()
                                        .centerCrop()
                                        .into(items.imgperfil);
                              }
                            items.imgDetalle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (estado.equals(("Aceptado"))){
                                        BottonShetFragment2 bottomSheetDialog = BottonShetFragment2.newInstance();
                                        bottomSheetDialog.ruta_archivo=ruta_documento;
                                        bottomSheetDialog.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @NonNull
            @Override
            public Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificaciones, parent, false);
                return new Items(vista);
            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();
    }
    public  static class Items extends RecyclerView.ViewHolder{
        TextView tvnombre_usu,tvdescripcionnoticia,tvdireccion;
        ImageView imgnoticia,imgperfil,imgDetalle,imgphone,imgchat;
        String key_empresa,ke_publicacion;

        public Items(@NonNull View itemView) {
            super(itemView);
            tvnombre_usu=(TextView)itemView.findViewById(R.id.txtnombreEmpresa);
            tvdescripcionnoticia=(TextView)itemView.findViewById(R.id.txtmensaje);
            imgperfil=(ImageView)itemView.findViewById(R.id.imgempresa);
            imgDetalle=(ImageView)itemView.findViewById(R.id.imgDetalle2);

        }
    }
}