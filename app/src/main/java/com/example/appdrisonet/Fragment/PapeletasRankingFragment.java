package com.example.appdrisonet.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.Adapter.AdapterPapeletas;
import com.example.appdrisonet.Adapter.AdapterRankingPapeletas;
import com.example.appdrisonet.Clases.Cantidad;
import com.example.appdrisonet.Clases.Papeleta;
import com.example.appdrisonet.Clases.Usuario;
import com.example.appdrisonet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PapeletasRankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PapeletasRankingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference referenceUsuarios,referecepapeletas;
    List<Cantidad> listaUsuario;
    ArrayList<Cantidad> listaUsuario1;
    ArrayList<Papeleta> ListaPapeleas;
    RecyclerView recyclerView;
    AdapterPapeletas adapter;
    ImageView imgprimero,imgsegundo,imgtercero;
    TextView tvprimero,tvsegundo,tvterero;
    AdapterRankingPapeletas adapterRankingPapeletas;


    public PapeletasRankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PapeletasRankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PapeletasRankingFragment newInstance(String param1, String param2) {
        PapeletasRankingFragment fragment = new PapeletasRankingFragment();
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
       View vista = inflater.inflate(R.layout.fragment_papeletas_ranking, container, false);
        recyclerView=(RecyclerView)vista.findViewById(R.id.paleletas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        imgprimero =(ImageView)vista.findViewById(R.id.puesto1);
        imgsegundo =(ImageView)vista.findViewById(R.id.puesto2);
        imgtercero =(ImageView)vista.findViewById(R.id.puesto3);

        tvprimero=(TextView)vista.findViewById(R.id.tv1);
        tvsegundo=(TextView)vista.findViewById(R.id.tv2);
        tvterero=(TextView)vista.findViewById(R.id.tv3);
        listaUsuario=new ArrayList<>();
        listaUsuario1=new ArrayList<>();
        ListaPapeleas=new ArrayList<>();
        referenceUsuarios= FirebaseDatabase.getInstance().getReference("TotalPapeletas");


        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query q=referenceUsuarios;
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaUsuario.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cantidad lis = postSnapshot.getValue(Cantidad.class);
                    listaUsuario.add(lis);
                }
               Collections.sort(listaUsuario);

                for (Cantidad item : listaUsuario){
                    System.out.println(item.getCantidad_pendiente());                  //  System.out.println(item.getNombre());
                //    Log.e("datos",String.valueOf( item.getCantidad_pendiente()));
                }

                Log.e("pirmeri", listaUsuario.get(0).getNombre());
                Log.e("foto", listaUsuario.get(0).getImg_usuario());
                String fotoprimero,nombreprimero;
                String fotosegundo,nombresegundo;
                String fototercero,nombretercero;
                nombreprimero=listaUsuario.get(0).getNombre();
                nombresegundo=listaUsuario.get(1).getNombre();
                nombretercero=listaUsuario.get(2).getNombre();

                fotoprimero=listaUsuario.get(0).getImg_usuario();
                fotosegundo=listaUsuario.get(1).getImg_usuario();
                fototercero=listaUsuario.get(2).getImg_usuario();

                tvprimero.setText(nombreprimero);
                tvsegundo.setText(nombresegundo);
                tvterero.setText(nombretercero);

                if (fotoprimero.equals("default_image")){
                    imgprimero.setImageResource(R.drawable.default_profile_image);
                }
                else{
                    Glide.with(getActivity().getApplicationContext())
                            .load(fotoprimero)
                            .placeholder(R.drawable.default_profile_image)
                            .fitCenter()
                            .centerCrop()
                            .error(R.drawable.default_profile_image)
                            .into(imgprimero);
                 }

                if (fotosegundo.equals("default_image")){
                    imgsegundo.setImageResource(R.drawable.default_profile_image);
                }
                else{
                    Glide.with(getActivity().getApplicationContext())
                            .load(fotosegundo)
                            .placeholder(R.drawable.default_profile_image)
                            .fitCenter()
                            .centerCrop()
                            .error(R.drawable.default_profile_image)
                            .into(imgsegundo);
                }
                if (fototercero.equals("default_image")){
                    imgtercero.setImageResource(R.drawable.default_profile_image);
                }
                else{
                    Glide.with(getActivity().getApplicationContext())
                            .load(fototercero)
                            .placeholder(R.drawable.default_profile_image)
                            .fitCenter()
                            .centerCrop()
                            .error(R.drawable.default_profile_image)
                            .into(imgtercero);
                }

                for (int i=3;i<listaUsuario.size();i++){
                    Cantidad o = new Cantidad();
                    o.setNombre(listaUsuario.get(i).getNombre());
                    o.setImg_usuario(listaUsuario.get(i).getImg_usuario());
                    listaUsuario1.add(o);
                }
                adapterRankingPapeletas = new AdapterRankingPapeletas(listaUsuario1);
                recyclerView.setAdapter(adapterRankingPapeletas);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  void ListarPeletas(ArrayList<Usuario> lista){
        for (Usuario item : lista) {
            referecepapeletas= FirebaseDatabase.getInstance().getReference("MisPapeletas").child(item.getId_usuario());
            Query q=referecepapeletas;
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                  //  ListaPapeleas.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Papeleta lis = postSnapshot.getValue(Papeleta.class);
                     //   listaUsuario.add(lis);
                        ListaPapeleas.add(lis);
                      //  ListaPapeleas.add()
                    }
                    adapter = new AdapterPapeletas(getContext(),ListaPapeleas);
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
      //  Log.e("lita", String.valueOf( ListaPapeleas.size()));

    }
}