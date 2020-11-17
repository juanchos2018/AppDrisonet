package com.example.appdrisonet.DialogoFratment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.example.appdrisonet.Clases.Solicitud;
import com.example.appdrisonet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottonSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottonSheetFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public    String  key_noticia,key_empresa;
    private final int frames = 9;
    private int currentAnimationFrame = 0;
    private LottieAnimationView animationView;
    android.app.AlertDialog.Builder builder2;
    AlertDialog aler2;
    private DatabaseReference referenceNoticia;
    private FirebaseAuth mAuth;

    public String  dni_usuario,nombrecompletos;
    private FirebaseUser user;

    public BottonSheetFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BottonSheetFragment newInstance() {
        BottonSheetFragment fragment = new BottonSheetFragment();
        return fragment;
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
      //  super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.fragment_botton_sheet, null);
        TextView txsolitud;

        txsolitud=(TextView)contentView.findViewById(R.id.id_solicitud);
        txsolitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
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
      View vista = inflater.inflate(R.layout.fragment_botton_sheet, container, false);
        TextView txsolitud;
        txsolitud=(TextView)vista.findViewById(R.id.id_solicitud);
        txsolitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                menssaje("Enviado Solicutd");
            }
        });
        return vista;
    }
    private void menssaje(String estado){
        RegistrarSolicitud(this.key_noticia);
        builder2 = new AlertDialog.Builder(getContext());
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_solicitud, null);
        animationView = v.findViewById(R.id.animation_viewcheck);
        resetAnimationView();
        animationView.playAnimation();
        builder2.setView(v);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(estado);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aler2.dismiss();
            }
        });

        aler2  = builder2.create();
        aler2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        aler2.show();
    }
    private  void RegistrarSolicitud(String key_noticia){
        referenceNoticia= FirebaseDatabase.getInstance().getReference("Solicitudes").child(key_noticia);
        String key = referenceNoticia.push().getKey();
        mAuth = FirebaseAuth.getInstance();
        String key_usu = mAuth.getCurrentUser().getUid();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fechas = dateFormat.format(date);
        Solicitud o =new Solicitud(nombrecompletos,key_usu,fechas,dni_usuario,key);
        referenceNoticia.child(key).setValue(o).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Enviado", Toast.LENGTH_SHORT).show();
                 //   progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error :" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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