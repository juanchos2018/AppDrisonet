package com.example.appdrisonet.Acitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdrisonet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Clave extends AppCompatActivity {


    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    String correoprofe;
    public FirebaseUser currentUser;
    String user_id,correo;
   EditText etcorreo;
    private ProgressDialog progressDialog;
    Button btnclave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave);

        toolbar = findViewById(R.id.toolbar1);
        etcorreo=(EditText)findViewById(R.id.etcorreo_clave);
        btnclave=(Button)findViewById(R.id.btnrecuperarclave);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        user_id =    mAuth.getCurrentUser().getUid();
        etcorreo.setText(mAuth.getCurrentUser().getEmail());
        btnclave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo=etcorreo.getText().toString().trim();
                enviarmensaje(correo);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void enviarmensaje(String correo) {
        progressDialog=new ProgressDialog(this);
        if (TextUtils.isEmpty(correo)){
            etcorreo.setError("campo requerido");
        }else{

            progressDialog.setTitle("verficando");
            progressDialog.setMessage("cargando..");
            progressDialog.show();

            mAuth.setLanguageCode("es");
            mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(Clave.this, "Mensaje enviado a su correo", Toast.LENGTH_SHORT).show();
                        limpiarcajas();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Clave.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private void limpiarcajas(){
        etcorreo.setText("");
    }
}