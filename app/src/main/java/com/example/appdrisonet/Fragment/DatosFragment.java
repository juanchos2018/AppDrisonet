package com.example.appdrisonet.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdrisonet.Acitity.RegistroActivity;
import com.example.appdrisonet.Adapter.PagerAdapter;
import com.example.appdrisonet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class DatosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "DatosFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtnombre,txtapellidoP,txtapellidoM,tvdni;
    EditText etcorreo,etdni;
    boolean estadoCampos;
    Button btnbuscar;
    Button btn;
    View vista;
   // private BootonClickLisntener mListener;

    private ProgressDialog progressDialog;
    private VerificarDatos mListener;
    List<String> papelietas;
    public static  String Nombreusuario;
    public  static String ApellidosUsuario;
    public static String DniUsuario;
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
        txtnombre=(TextView)vista.findViewById(R.id.tvnombres);
        txtapellidoP=(TextView)vista.findViewById(R.id.tvapPaterno);
        txtapellidoM=(TextView)vista.findViewById(R.id.tvapeMaterno);
        etdni=(EditText)vista.findViewById(R.id.etDni);
        tvdni=(TextView)vista.findViewById(R.id.tvdni);
        btnbuscar=(Button)vista.findViewById(R.id.btnbuscar);
        /*
        btn=(Button)vista.findViewById(R.id.btnver);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroActivity.name="kiara loca";
                mListener.onButtonclick("kiara loca");
                SharedPreferences preferences=getContext().getSharedPreferences("nombre", Context.MODE_PRIVATE);
                String nombre="kiara loca";
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("nombre",nombre);
                editor.commit();
            }
        });

         */

        RegistroActivity.name="Kiara";
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Verificar2();
                String dni = etdni.getText().toString();
                ConsutarDni(dni);
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

    private  void  nada()
    {
        Log.e(TAG, "nada: ");
    }
    private  void  ConsutarDni(final String dni){
        if(TextUtils.isEmpty(dni)){
            etdni.setError("campo requerido");
        }
        else {
            progressDialog =new ProgressDialog(getContext());
            progressDialog.setTitle("Consultando");
            progressDialog.setMessage("Cargando ....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            final String URL2="https://quertium.com/api/v1/reniec/dni/"+dni+"?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.MTM3Mw.x-jUgUBcJukD5qZgqvBGbQVMxJFUAIDroZEm4Y9uTyg";
            RequestQueue requestQueue2= Volley.newRequestQueue(getContext().getApplicationContext());
            StringRequest stringRequest1 =new StringRequest(Request.Method.GET,URL2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responses) {
                            try {
                               // card1.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                JSONObject jsonObject2=new JSONObject(responses);
                                //  JSONObject nombre=jsonObject.getJSONObject("nombres");
                             //   etdni.setText(dni);
                                tvdni.setText(dni);
                                String name=jsonObject2.getString("primerNombre");
                                String name2=jsonObject2.getString("segundoNombre");
                                String apellido_paterno=jsonObject2.getString("apellidoPaterno");
                                String apellido_materno=jsonObject2.getString("apellidoMaterno");
                                txtnombre.setText(name +" "+name2);
                                txtapellidoP.setText(apellido_paterno );
                                txtapellidoM.setText(apellido_materno);
                                Nombreusuario=name+" "+ name2;
                                ApellidosUsuario= apellido_materno +" "+apellido_paterno;
                                SharedPreferences preferences=getContext().getSharedPreferences("nombre", Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("nombre",Nombreusuario);
                                editor.putString("apellido",ApellidosUsuario);
                                editor.putString("dni",dni);
                                editor.commit();

                                Log.e(TAG, name);
                                
                             //   ocultar();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Err "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest1.setRetryPolicy(policy);
            requestQueue2.add(stringRequest1);
        }
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
         public   void  onButtonclick(String texto);

    }
}