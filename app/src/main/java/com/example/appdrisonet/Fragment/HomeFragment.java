package com.example.appdrisonet.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appdrisonet.Adapter.AdapterNoticias;
import com.example.appdrisonet.Clases.Noticias;
import com.example.appdrisonet.R;
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


    RecyclerView recycler;
    private DatabaseReference referenceNoticia;

    ArrayList<Noticias> listaNoticias;
    AdapterNoticias adapter;


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

        referenceNoticia= FirebaseDatabase.getInstance().getReference("Noticias");
        listaNoticias = new ArrayList<>();

        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();

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
        });

    }
}