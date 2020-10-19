package com.example.appdrisonet.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdrisonet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtnombre;
    EditText etcorreo;
    boolean estadoCampos;
    Button btnbuscar;
    View vista;

    private VerificarDatos mListener;
    public DatosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DatosFragment newInstance() {
        DatosFragment fragment = new DatosFragment();

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
        vista = inflater.inflate(R.layout.fragment_datos, container, false);
        txtnombre=(TextView)vista.findViewById(R.id.tvnombreusu);
        btnbuscar=(Button)vista.findViewById(R.id.btnbuscar);
        etcorreo=(EditText)vista.findViewById(R.id.tvcorreo);
       // etcorreo=(EditText)vista.findViewById(R.id.tvcorreo);
       // tvcorreo

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verificar2();
            }
        });
        return vista;
    }

    private void Verificar2() {

        String correo=etcorreo.getText().toString();
        if (TextUtils.isEmpty(correo)){
            Toast.makeText(getContext(), "Vacio", Toast.LENGTH_SHORT).show();
            estadoCampos=true;
        }
        else{
            estadoCampos=false;
            Toast.makeText(getContext(), "No vacio", Toast.LENGTH_SHORT).show();
        }
        mListener.VerificaCampos(estadoCampos);
    }

    public  boolean verificar(){
        boolean estado=false;

        String correo=etcorreo.getText().toString();
        if (TextUtils.isEmpty(correo)){
            estado=true;
        }

        return  estado;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            if (context instanceof VerificarDatos){
                mListener = (VerificarDatos) context;
            }else{

                throw  new ClassCastException(context.toString()+"musimpemet");
            }
            // mListener=(BootonClickLisntener)context;
        }catch (ClassCastException e){

        }

    }
    public interface VerificarDatos {
        public void VerificaCampos(boolean va);

    }
}