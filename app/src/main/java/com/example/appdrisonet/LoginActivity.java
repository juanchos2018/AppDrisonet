package com.example.appdrisonet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdrisonet.Acitity.RegistroActivity;

public class LoginActivity extends AppCompatActivity {


    Button btn1,btn2,btn3;
    EditText et1,et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn1=(Button)findViewById(R.id.btnregistrar);
        btn2=(Button)findViewById(R.id.btningresar);
        btn3=(Button)findViewById(R.id.btnregistrar);
        et1=(EditText)findViewById(R.id.etcorreo);
        et2=(EditText)findViewById(R.id.etclave);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo=et1.getText().toString();
                String clave =et2.getText().toString();
                Ingresar(correo,clave);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar();
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