package com.example.appdrisonet.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdrisonet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Datos3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Datos3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Datos3Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Datos3Fragment newInstance() {
        Datos3Fragment fragment = new Datos3Fragment();
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
       View vista = inflater.inflate(R.layout.fragment_datos3, container, false);
        return vista;
    }
}