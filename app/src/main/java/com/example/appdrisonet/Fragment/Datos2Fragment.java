package com.example.appdrisonet.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appdrisonet.R;

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
            Log.e("llego nonbre",nom);
            //poenerNombre(nom);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    vista   = inflater.inflate(R.layout.fragment_datos2, container, false);
        tvnombreusu=(TextView)vista.findViewById(R.id.tvnombreUsu);


      // tvnombreusu.setText(DatosFragment.Nombreusuario);
        //Log.e(TAG, "viene "+ DatosFragment.Nombreusuario);

        return vista;
    }
    public  void  poenerNombre(String nonbre){
     //   tvnombreusu=(TextView)vista.findViewById(R.id.tvnombreUsu);
       // tvnombreusu.setText(nonbre);
    }
}