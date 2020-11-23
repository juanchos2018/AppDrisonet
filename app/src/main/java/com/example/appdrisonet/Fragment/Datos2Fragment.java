package com.example.appdrisonet.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.example.appdrisonet.Adapter.AdapterNoticias;
import com.example.appdrisonet.Adapter.AdapterPapeletas;
import com.example.appdrisonet.Clases.Papeleta;
import com.example.appdrisonet.R;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private OkHttpClient httpClient;
    private ImageView ivCaptcha;
    private EditText txtCodigo;
    RecyclerView recyclerView;
    ImageButton recarga;
  public    static ArrayList<Papeleta> ListaPapeleta;
    TextView txtdni;
    AdapterPapeletas adapter;
    Button btnconsuta;
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

       // mAuth = FirebaseAuth.getInstance();
      //  user = mAuth.getCurrentUser();
        ivCaptcha = vista.findViewById(R.id.ivCaptcha2);
        ListaPapeleta=new ArrayList<>();
        txtCodigo = vista.findViewById(R.id.txtCodigo2);
        recarga=(ImageButton)vista.findViewById(R.id.btnLoadImage);
        btnconsuta=(Button)vista.findViewById(R.id.btnConsultar);
        btnconsuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar1();
            }
        });
        txtdni=vista.findViewById(R.id.xttddni);
        recyclerView=vista.findViewById(R.id.recyclerpapeletas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

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
                txtdni.setText(d);
               }
          //  tvnombreusu.setText(mName);
        }

        init();
        cargarImagen();
        recarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        return vista;
    }
    public void init() {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getContext()));

        this.httpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();

        Request request = new Request.Builder().url("https://www.munitacna.gob.pe/pagina/sf/servicios/papeletas").build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
    public void cargarImagen() {
        Request request = new Request.Builder().url("https://www.munitacna.gob.pe/transporte/papeleta_c").build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ivCaptcha.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });
    }

    public void consultar1() {

        if (dni=="" || dni==null){
            Toast.makeText(getContext(), "No conulstade dni", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody formBody = new FormBody.Builder()
                .add("codigo", txtCodigo.getText().toString())
                .add("opcion", "dni")
                .add("busca", dni)
                .build();

        Request request = new Request.Builder()
                .addHeader("Content-type", "application/x-www-form-urlencoded")
                .url("https://www.munitacna.gob.pe/transporte/papeleta")
                .post(formBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                try {
                    final JSONObject jsonObject = new JSONObject(json);

                    if(jsonObject.getBoolean("ok")){
                        JSONArray papeletasArray = jsonObject.getJSONArray("papeletas");

                        final ArrayList<Papeleta> papeletas = new ArrayList<Papeleta>();

                        for (int i = 0; i < papeletasArray.length(); i++) {
                            JSONObject itemObject = papeletasArray.getJSONObject(i);
                            Papeleta papeleta = new Papeleta();
                            papeleta.setConductor(itemObject.getString("CONDUCTOR"));
                            papeleta.setEstado_deuda(itemObject.getString("EstadoDeuda"));
                            papeleta.setFecha(itemObject.getString("FECHA"));
                            papeleta.setImporte(itemObject.getString("IMPORTE"));
                            papeleta.setInfraccion(itemObject.getString("INFRACCION"));
                            papeleta.setPropietario(itemObject.getString("PROPIETARIO"));
                            papeleta.setPt_cod_papeleta(itemObject.getString("PTCodPapeleta"));
                            papeleta.setPt_dni_conductor(itemObject.getString("PTDniConductor"));
                            papeleta.setPt_dni_propietario(itemObject.getString("PTDniPropietario"));
                            papeleta.setPt_numero_licencia(itemObject.getString("PTNumeroLicencia"));
                            papeleta.setSerie_papeleta(itemObject.getString("SeriePapeleta"));

                           // papeletas.add(papeleta);
                            ListaPapeleta.add(papeleta);
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new AdapterPapeletas(getContext(),ListaPapeleta);
                                recyclerView.setAdapter(adapter);
                            }
                        });

                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void consultar() {
        RequestBody formBody = new FormBody.Builder()
                .add("codigo", txtCodigo.getText().toString())
                .add("opcion", "dni")
                .add("busca", dni)
                .build();

        Request request = new Request.Builder()
                .addHeader("Content-type", "application/x-www-form-urlencoded")
                .url("https://www.munitacna.gob.pe/transporte/papeleta")
                .post(formBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                try {
                    final JSONObject jsonObject = new JSONObject(json);

                    if(jsonObject.getBoolean("ok")){
                        JSONArray papeletasArray = jsonObject.getJSONArray("papeletas");

                        final ArrayList<Papeleta> papeletas = new ArrayList<Papeleta>();

                        for (int i = 0; i < papeletasArray.length(); i++) {
                            JSONObject itemObject = papeletasArray.getJSONObject(i);
                            Papeleta papeleta = new Papeleta();
                            papeleta.setConductor(itemObject.getString("CONDUCTOR"));
                            papeleta.setEstado_deuda(itemObject.getString("EstadoDeuda"));
                            papeleta.setFecha(itemObject.getString("FECHA"));
                            papeleta.setImporte(itemObject.getString("IMPORTE"));
                            papeleta.setInfraccion(itemObject.getString("INFRACCION"));
                            papeleta.setPropietario(itemObject.getString("PROPIETARIO"));
                            papeleta.setPt_cod_papeleta(itemObject.getString("PTCodPapeleta"));
                            papeleta.setPt_dni_conductor(itemObject.getString("PTDniConductor"));
                            papeleta.setPt_dni_propietario(itemObject.getString("PTDniPropietario"));
                            papeleta.setPt_numero_licencia(itemObject.getString("PTNumeroLicencia"));
                            papeleta.setSerie_papeleta(itemObject.getString("SeriePapeleta"));

                       //     papeletas.add(papeleta);
                            ListaPapeleta.add(papeleta);
                            Log.e("iosta","osdaffsdfdsf");
                            Log.e("iosta",String.valueOf( ListaPapeleta.size()));
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                             //   papeletaAdaptador = new PapeletaAdaptador(MainActivity.this, papeletas);
                              //  papeletasList.setAdapter(papeletaAdaptador); ListaPapeleta
                                adapter = new AdapterPapeletas(getContext(),ListaPapeleta);
                                recyclerView.setAdapter(adapter);
                            }
                        });

                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}