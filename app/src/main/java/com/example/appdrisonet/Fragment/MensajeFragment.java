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
import com.example.appdrisonet.Clases.SubChat;
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
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MensajeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MensajeFragment extends Fragment {

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
    private DatabaseReference referenceNoticia,referenceSubchat;
    String dni,Completos,urlfoto,nombre_usuario,img_usuario;

    public MensajeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MensajeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MensajeFragment newInstance(String param1, String param2) {
        MensajeFragment fragment = new MensajeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_mensaje, container, false);

        recycler=vista.findViewById(R.id.recycler2);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        user_id =  mAuth.getCurrentUser().getUid();

        referenceNoticia= FirebaseDatabase.getInstance().getReference("SubChat2").child(user_id);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        user_id =  mAuth.getCurrentUser().getUid();
        referenceUsuarios = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_id);
        referenceUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                urlfoto = dataSnapshot.child("image_usuario").getValue().toString();
                nombre_usuario= dataSnapshot.child("nombre_usuario").getValue().toString();
                String apellido = dataSnapshot.child("apellido_usuario").getValue().toString();
                dni = dataSnapshot.child("dni_usuario").getValue().toString();
                Completos=nombre_usuario+" "+apellido;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<SubChat> recyclerOptions = new FirebaseRecyclerOptions.Builder<SubChat>()
                .setQuery(referenceNoticia, SubChat.class).build();
        FirebaseRecyclerAdapter<SubChat, Items> adapter =new FirebaseRecyclerAdapter<SubChat, Items>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull SubChat tutores) {
                final String key = getRef(i).getKey();
                referenceNoticia.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Context context = getContext();
                        if (dataSnapshot.exists()){
                            final String usuario=dataSnapshot.child("nombre_usuario").getValue().toString();
                            final String mensaje=dataSnapshot.child("mensaje").getValue().toString();
                            final String img_empresa=dataSnapshot.child("image_usuario").getValue().toString();
                            final String id_empresa=dataSnapshot.child("id_empresa").getValue().toString();
                            items.tvnombre_usu.setText(usuario);
                            items.tvmenaje.setText(mensaje);


                            if (img_empresa.equals("default_image")){
                                items.imgperfil.setImageResource(R.drawable.ic_launcher_background);
                            }else {
                                Glide.with(getContext().getApplicationContext())
                                        .load(img_empresa)
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .fitCenter()
                                        .centerCrop()
                                        .into(items.imgperfil);
                            }

                            items.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id_empresa",id_empresa);
                                    bundle.putString("id_usuario",user_id);
                                    bundle.putString("nombre_usuario",Completos);
                                    bundle.putString("nombre_empresa",usuario);
                                    bundle.putString("image_usuario",urlfoto);//rutausuario
                                    bundle.putString("image_empresa","ruta_empresa");//rutausuario
                                    Intent intent= new Intent(getContext(), ChatActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
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

                View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contactos, parent, false);
                return new Items(vista);

            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();

    }
    public  static class Items extends RecyclerView.ViewHolder{
        TextView tvnombre_usu,tvmenaje,tvdireccion;
        ImageView imgnoticia,imgperfil,imgDetalle,imgphone,imgchat;
        String key_empresa,ke_publicacion;

        public Items(@NonNull View itemView) {
            super(itemView);
            tvnombre_usu=(TextView)itemView.findViewById(R.id.NombreEmpresa);
            tvmenaje=(TextView)itemView.findViewById(R.id.Mensaje);
            imgperfil=(ImageView)itemView.findViewById(R.id.fotoPerfilMensaje2);


        }
    }
}