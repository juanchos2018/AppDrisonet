package com.example.appdrisonet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appdrisonet.Acitity.RegistroActivity;

public class LoginActivity extends AppCompatActivity {


    Button btningresar,btn3;
    EditText et1,et2;
    private ImageButton btnregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        btnregistrar=(ImageButton)findViewById(R.id.btnregistrar);
        btningresar=(Button)findViewById(R.id.btningresar);
        //btn3=(Button)findViewById(R.id.btnregistrar);
        et1=(EditText)findViewById(R.id.etcorreo);
        et2=(EditText)findViewById(R.id.etclave);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo=et1.getText().toString();
                String clave =et2.getText().toString();
                Ingresar(correo,clave);
            }
        });



    }

    private void Registrar() {
        startActivity(new Intent(LoginActivity.this,RegistroActivity.class));

    }

    private void Ingresar(String correo, String clave) {

        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
    }

    private void ver(){
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
    }
}