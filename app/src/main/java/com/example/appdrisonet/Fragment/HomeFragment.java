package com.example.appdrisonet.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.example.appdrisonet.Adapter.AdapterNoticias;
import com.example.appdrisonet.Clases.Noticias;
import com.example.appdrisonet.DialogoFratment.BottonSheetFragment;
import com.example.appdrisonet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

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
    private DatabaseReference referenceUsuarios;
    public FirebaseUser currentUser;
    RecyclerView recycler;
    private DatabaseReference referenceNoticia;

    ArrayList<Noticias> listaNoticias;
    AdapterNoticias adapter;

    String dni,Completos;
    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        recycler=vista.findViewById(R.id.recycler1);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        referenceNoticia= FirebaseDatabase.getInstance().getReference("Publicaciones");
        listaNoticias = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        user_id =  mAuth.getCurrentUser().getUid();
        referenceUsuarios = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_id);
        referenceUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String img_usuario = dataSnapshot.child("image_usuario").getValue().toString();
                String nombre = dataSnapshot.child("nombre_usuario").getValue().toString();
                String apellido = dataSnapshot.child("apellido_usuario").getValue().toString();
                dni = dataSnapshot.child("dni_usuario").getValue().toString();
                Completos=nombre+" "+apellido;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();/*
        Query query=referenceNoticia;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaNoticias.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Noticias noti = postSnapshot.getValue(Noticias.class);
                    listaNoticias.add(noti);
                }
                adapter = new AdapterNoticias(listaNoticias);
                recycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });*/
        FirebaseRecyclerOptions<Noticias> recyclerOptions = new FirebaseRecyclerOptions.Builder<Noticias>()
                .setQuery(referenceNoticia, Noticias.class).build();
        FirebaseRecyclerAdapter<Noticias,Items> adapter =new FirebaseRecyclerAdapter<Noticias, Items>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull Noticias tutores) {
                final String key = getRef(i).getKey();
                referenceNoticia.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Context context = getContext();
                        if (dataSnapshot.exists()){
                            final String usuario=dataSnapshot.child("nombre_usuario").getValue().toString();
                            final String descripcion=dataSnapshot.child("descripcion_noticia").getValue().toString();
                            final String rutafoto=dataSnapshot.child("img_noticia").getValue().toString();
                            final String rutausuario=dataSnapshot.child("img_usuario").getValue().toString();
                            final String key_noticia=dataSnapshot.child("key_noticia").getValue().toString();

                            items.tvnombre_usu.setText(usuario);
                            items.tvdescripcionnoticia.setText(descripcion);

                            if (rutafoto.equals("default_image")){
                               items.imgnoticia.setImageResource(R.drawable.ic_launcher_background);
                            }else {
                                Glide.with(getContext().getApplicationContext())
                                   .load(rutafoto)
                                   .placeholder(R.drawable.ic_launcher_background)
                                   .fitCenter()
                                   .centerCrop()
                                   .into(items.imgnoticia);
                            }
                            Glide.with(getContext().getApplicationContext())
                                    .load(rutausuario)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(items.imgperfil);
                            items.imgDetalle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    BottonSheetFragment bottomSheetDialog = BottonSheetFragment.newInstance();
                                    bottomSheetDialog.key_noticia=key_noticia;
                                    bottomSheetDialog.dni_usuario=dni;
                                    bottomSheetDialog.nombrecompletos=Completos;
                                    bottomSheetDialog.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");                                }
                            });
                            items.tvdireccion.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getContext(), MapaActivity.class));
                                }
                            });
                            items.imgchat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getContext(), ChatActivity.class));
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

                View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false);
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
            tvnombre_usu=(TextView)itemView.findViewById(R.id.tvnombreusu);
            tvdescripcionnoticia=(TextView)itemView.findViewById(R.id.tvdescripcion);
            imgnoticia=(ImageView)itemView.findViewById(R.id.imgnoticia);
            imgperfil=(ImageView)itemView.findViewById(R.id.imgperfil);
            imgDetalle=(ImageView)itemView.findViewById(R.id.imgDetalle);
            tvdireccion=(TextView)itemView.findViewById(R.id.tvdireccion);
            imgphone=(ImageView)itemView.findViewById(R.id.imgphone);
            imgchat=(ImageView)itemView.findViewById(R.id.imgchat);

        }
    }
}