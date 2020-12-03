package com.example.appdrisonet.Acitity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.appdrisonet.R;

public class DetalleNotificacionActivity extends AppCompatActivity {


    Button btndescargar,btnadjuntar;
    TextView tvdocumento;
    String urldocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_notificacion);

    }
}