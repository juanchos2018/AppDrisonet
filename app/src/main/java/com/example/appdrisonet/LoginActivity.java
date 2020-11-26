package com.example.appdrisonet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appdrisonet.Acitity.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {


    private Button btningresar;
    private EditText etcorreo,etpassword;
    private ImageButton btnregistrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference userDatabaseReference,reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnregistrar=(ImageButton)findViewById(R.id.btnregistrar);
        btningresar=(Button)findViewById(R.id.btningresar);
        etcorreo=(EditText)findViewById(R.id.etcorreo);
        etpassword=(EditText)findViewById(R.id.etclave);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });
        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = etcorreo.getText().toString();
                String clave = etpassword.getText().toString();
                //startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                 ingresar(correo,clave);

            }
        });
    }

    private void Registrar() {
        startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
    }

    private void ingresar(String correo, String clave) {
        progressDialog = new ProgressDialog(this);
        if (TextUtils.isEmpty(correo)){
            etcorreo.setError("campo requerido");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(clave)){
            etpassword.setError("campo requerido");
        }
        else{
            progressDialog.setMessage("Cargando...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            mAuth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userUID = mAuth.getCurrentUser().getUid();
                        String userDeiceToken = FirebaseInstanceId.getInstance().getToken();
                        checkVerifiedEmail();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Verifique su Email", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
    private void checkVerifiedEmail() {
        user = mAuth.getCurrentUser();
        boolean isVerified = false;
        if (user != null) {
            isVerified = user.isEmailVerified();
        }
        if (isVerified){
            final String UID = mAuth.getCurrentUser().getUid();
            userDatabaseReference.child(UID).child("verified").setValue("true");
            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            userDatabaseReference.child(UID).child("active_now").setValue("true");
            GuardarToken(UID);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Correo no verificado", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
    public void GuardarToken(final String id_usuario){
        SharedPreferences preferences= getSharedPreferences("mitoken", Context.MODE_PRIVATE);
        String token=preferences.getString("token","no existe we");
        userDatabaseReference.child(id_usuario).child("token").setValue(token);
       // reference = FirebaseDatabase.getInstance().getReference("token").child(id_usuario);
      //  reference.child("token").setValue(token);

    }

    private void ver(){
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
    }
}