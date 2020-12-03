package com.example.appdrisonet.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    Button btnhora;
    private DatabaseReference referenceNoticia,referenceSubchat;

    ArrayList<Noticias> listaNoticias;
    AdapterNoticias adapter;

    String dni,Completos,urlfoto,nombre_usuario,img_usuario;
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
        btnhora=(Button)vista.findViewById(R.id.btnhora);
        btnhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //Fcha Actual
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    String formattedDate = df.format(c.getTime());

                    //Lo primero que tienes que hacer es establecer el formato que tiene tu fecha para que puedas obtener un objeto de tipo Date el cual es el que se utiliza para obtener la diferencia.
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault());

                    //Parceas tus fechas en string a variables de tipo date se agrega un try catch porque si el formato declarado anteriormente no es igual a tu fecha obtendrás una excepción
                    Date dateStart = dateFormat.parse(df.format(c.getTime()));
                   // Date dateStart =df.format(c.getTime());

                  //  Date dateEnd = dateFormat.parse("2018/09/14 01:10:20");
                    Date dateEnd = dateFormat.parse("28/11/2020 12:10:20");
                    //obtienes la diferencia de las fechas
                    long difference = Math.abs(dateEnd.getMinutes() - dateStart.getMinutes());

                    //obtienes la diferencia en horas ya que la diferencia anterior esta en milisegundos
                    difference= difference / (60 * 60 * 1000);
                    Log.e("Difference: " ,  Long.toString(difference));


                    Toast.makeText(getContext(), Long.toString(difference), Toast.LENGTH_SHORT).show();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
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

        // formattedDate have current date/time

        /*


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
                            final String id_empresa=dataSnapshot.child("key_usuario").getValue().toString();
                            final String telefono=dataSnapshot.child("telefono").getValue().toString();
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
                                    bottomSheetDialog.imgusuario=urlfoto;
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
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id_empresa",id_empresa);
                                    bundle.putString("id_usuario",user_id);
                                    bundle.putString("nombre_usuario",Completos);
                                    bundle.putString("nombre_empresa",usuario);
                                    bundle.putString("image_usuario",urlfoto);//rutausuario
                                    bundle.putString("image_empresa",rutausuario);//rutausuario
                                    Intent intent= new Intent(getContext(), ChatActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            items.imgphone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //String telefono="970780836";
                                    Telefono(telefono);

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

    private void Telefono(String numero) {
        try {
            if (TextUtils.isEmpty(numero)){
                Toast.makeText(getContext(), "no existe numero de telefono", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+numero));
                startActivity(intent);
            }

        }catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private  void  SubChat(String idempresa,String id_usuario,String nombre_usuario,String img_usuario){
        referenceSubchat = FirebaseDatabase.getInstance().getReference("SubChat").child(idempresa);
        referenceSubchat.child("id_usuario").setValue(id_usuario);
        referenceSubchat.child("nombre_usuario").setValue(nombre_usuario);
        referenceSubchat.child("image_usuario").setValue(img_usuario);
        referenceSubchat.child("fecha").setValue("12/12/12");
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