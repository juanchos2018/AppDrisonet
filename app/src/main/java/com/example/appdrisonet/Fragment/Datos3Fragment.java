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
import androidx.annotation.StyleableRes;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.example.appdrisonet.Clases.Papeleta;
import com.example.appdrisonet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Datos3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Datos3Fragment extends Fragment {

    Button btnregistrar;
    EditText etcorreo,etpassword;

    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String dni,nombreusuario,apellidousuario;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert;
    private int currentAnimationFrame = 0;
    private LottieAnimationView animationView;
    public Datos3Fragment() {
        // Required empty public constructor
    }


    public static Datos3Fragment newInstance() {
        Datos3Fragment fragment = new Datos3Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View vista = inflater.inflate(R.layout.fragment_datos3, container, false);

        btnregistrar=(Button)vista.findViewById(R.id.btnregistrarUsuario);
        etcorreo=(EditText)vista.findViewById(R.id.tvcorreo);
        etpassword=(EditText)vista.findViewById(R.id.tvpassword);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        SharedPreferences sharedPreferences =this.getActivity().getSharedPreferences("nombre", Context.MODE_PRIVATE);
        if (sharedPreferences!=null)
        {
            String nombre=sharedPreferences.getString("nombre","");
            String apellido=sharedPreferences.getString("apellido","");
            String d=sharedPreferences.getString("dni","");
           // tvnombreusu.setText(nombre);
            nombreusuario=nombre;
            apellidousuario=apellido;
            dni=d;
           // txtdni.setText(d);
        }
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo =etcorreo.getText().toString();
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
                        final String current_userID =  mAuth.getCurrentUser().getUid();
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
                                                    mAuth.signOut();
                                                    GuardarPapeletas(current_userID);
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
    private void GuardarPapeletas(String id ){

        for (Papeleta item:Datos2Fragment.ListaPapeleta) {

            reference2=FirebaseDatabase.getInstance().getReference("MisPapeletas").child(id);
            String key = reference2.push().getKey();
        //    reference2.child(user_id).child("iamge_usuario").setValue(rutafoto);
            Papeleta o =new Papeleta(item.getEstado_deuda(),item.getFecha(),item.getImporte(),item.getPropietario(),item.getConductor());
            reference2.child(key).setValue(o);

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