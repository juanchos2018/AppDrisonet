package com.example.appdrisonet.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.example.appdrisonet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Datos2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Datos2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tvnombreusu;
    View vista;
    Button btnregistrar;
    EditText etcorreo,etpassword;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String dni,nombreusuario,apellidousuario;

    private int currentAnimationFrame = 0;
    private LottieAnimationView animationView;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert;
    private static final String TAG = "Datos2Fragment";
    public Datos2Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Datos2Fragment newInstance(String nonbre) {
        Datos2Fragment fragment = new Datos2Fragment();
      //  tvnombreusu.setText(nonbre);

        Bundle args = new Bundle();
        args.putString("nombre", nonbre);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
         //   tvnombreusu.setText(mParam1);
            String nom =getArguments().getString("nombre");
            //Log.e("llego nonbre",nom);
            //poenerNombre(nom);
          //  poenerNombre(nom);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_datos2, container, false);
        tvnombreusu=(TextView)vista.findViewById(R.id.tvnombreUsu);
        btnregistrar=(Button)vista.findViewById(R.id.btnregistrarUsuario);
        etcorreo=(EditText)vista.findViewById(R.id.tvcorreo);
        etpassword=(EditText)vista.findViewById(R.id.tvpassword);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        final Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("nombre")) {
            // Set a default or error as you see fit
        } else {
         String   mName = arguments.getString("nombre");
            SharedPreferences sharedPreferences =this.getActivity().getSharedPreferences("nombre", Context.MODE_PRIVATE);
            if (sharedPreferences!=null)
            {
                String nombre=sharedPreferences.getString("nombre","");
                String apellido=sharedPreferences.getString("apellido","");
                String d=sharedPreferences.getString("dni","");
                tvnombreusu.setText(nombre);
                nombreusuario=nombre;
                apellidousuario=apellido;
                dni=d;
               }
          //  tvnombreusu.setText(mName);
        }
      // tvnombreusu.setText(DatosFragment.Nombreusuario);
        //Log.e(TAG, "viene "+ DatosFragment.Nombreusuario);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo=etcorreo.getText().toString().trim();
                String password=etpassword.getText().toString();
                Registar(correo,password);
            }
        });

        return vista;
    }

    private void Registar(final String correo, String password) {
       if (TextUtils.isEmpty(correo)){
            etcorreo.setError("campo requerido");
        }
        else if(TextUtils.isEmpty(password)){
            etpassword.setError("campo requerido");
        }

        else{
            progressDialog =new ProgressDialog(getContext());
            progressDialog.setTitle("Creando Cuenta");
            progressDialog.setMessage("Cargando...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            mAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String current_userID =  mAuth.getCurrentUser().getUid();
                        reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(current_userID);
                        reference.child("id_usuario").setValue(current_userID);
                        reference.child("dni_usuario").setValue(dni);
                        reference.child("nombre_usuario").setValue(nombreusuario);
                        reference.child("apellido_usuario").setValue(apellidousuario);
                        reference.child("correo_usuario").setValue(correo);
                        reference.child("image_usuario").setValue("default_image");
                        reference.child("created_at").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    user=mAuth.getCurrentUser();
                                    if (user!=null){
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    progressDialog.dismiss();
                                                    mensajeverfica();
                                                }else{
                                                    mAuth.signOut();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }else {
                        String message = task.getException().getMessage();
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error occurred : " + message, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void mensajeverfica(){
        builder1 = new AlertDialog.Builder(getContext());
        Button btcerrrar;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_correo, null);
        animationView = v.findViewById(R.id.animation_viewcheck4);
        resetAnimationView();
        animationView.playAnimation();
        builder1.setView(v);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo2);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert  = builder1.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }
    private void resetAnimationView() {
        currentAnimationFrame = 0;
        animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return null;
                    }
                }
        );
    }

}