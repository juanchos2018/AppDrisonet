package com.example.appdrisonet.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.Acitity.PerfilActivity;
import com.example.appdrisonet.Acitity.RegistroActivity;
import com.example.appdrisonet.Clases.Papeleta;
import com.example.appdrisonet.LoginActivity;
import com.example.appdrisonet.PrincipalActivity;
import com.example.appdrisonet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    public FirebaseUser currentUser;

    CardView carperfil;
    TextView tvnombre;
    ImageView imgperfil2;
    private TextView idverperfil;
    private TextView signup;
    private DatabaseReference referenceUsuarios;

    int cantidapepletas;
    int cantidapendietes;
    private DatabaseReference referecepapeletas;
    String user_id;
    private FirebaseUser user;
    TextView tvpapeletas;
    TextView tvpendientes,tvnumerolicencia;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);

        idverperfil = vista.findViewById(R.id.idverperfil);
        imgperfil2=(ImageView)vista.findViewById(R.id.imgperfil2);
        signup = vista.findViewById(R.id.signup);
        tvpapeletas=(TextView)vista.findViewById(R.id.idcantidadpapeletas);
        tvpendientes=(TextView)vista.findViewById(R.id.idpapeletaspendientes);
        tvnumerolicencia=(TextView)vista.findViewById(R.id.idnumlicencia);

        carperfil=(CardView)vista.findViewById(R.id.carperfil);
        tvnombre=(TextView)vista.findViewById(R.id.tvnombre);
        carperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perfil();
            }
        });
        tvnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perfil();
            }
        });
        idverperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perfil();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutUser();
                //mAuth.signOut();
                //startActivity(new Intent(PerfilFragment.this, LoginActivity.class));
            }
        });
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
                String dni = dataSnapshot.child("dni_usuario").getValue().toString();
                tvnumerolicencia.setText("K-"+dni);
                tvnombre.setText(nombre+ " "+apellido);
                if (img_usuario.equals("default_image")){
                    imgperfil2.setImageResource(R.drawable.default_profile_image);
                }
                else{
                    Glide.with(getActivity().getApplicationContext())
                            .load(img_usuario)
                            .placeholder(R.drawable.default_profile_image)
                            .fitCenter()
                            .centerCrop()
                            .error(R.drawable.default_profile_image)
                            .into(imgperfil2);
                }
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
        cantidapendietes=0;
        cantidapepletas=0;
        referecepapeletas=FirebaseDatabase.getInstance().getReference("MisPapeletas").child(user_id);
        Query qp =referecepapeletas;
        qp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Papeleta obj = postSnapshot.getValue(Papeleta.class);
                        if (obj.getEstado_deuda().equals("PENDIENTE")){
                            cantidapendietes++;
                        }
                        cantidapepletas++;
                    }
                    tvpapeletas.setText(""+cantidapepletas);
                    tvpendientes.setText(""+cantidapendietes);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Perfil() {

        startActivity(new Intent(getContext(), PerfilActivity.class));

    }
    private void logOutUser() {
        mAuth.signOut();
       // logOutUser();
        Intent loginIntent =  new Intent(getContext(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);

    }
}