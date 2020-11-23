package com.example.appdrisonet.Acitity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appdrisonet.R;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

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

public class PruebaActivity extends AppCompatActivity {

    private Button btnConsultar;
    private Button btnLoadImage;
    private EditText txtDni;
    private ImageView ivCaptcha;
    private EditText txtCodigo;

    private OkHttpClient httpClient;

    private ListView papeletasList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        ivCaptcha = findViewById(R.id.ivCaptcha);

        txtDni = findViewById(R.id.txtDni);
        ivCaptcha = findViewById(R.id.ivCaptcha);
        txtCodigo = findViewById(R.id.txtCodigo1);
        init();
        cargarImagen();
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });
    }
    public void init() {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));

        this.httpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();

        Request request = new Request.Builder().url("https://slcp.mtc.gob.pe").build();

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
    private void cargarImagen() {
        Request request = new Request.Builder().url("https://slcp.mtc.gob.pe/Captcha.aspx").build();
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
    public void consultar() {
        RequestBody formBody = new FormBody.Builder()
                // .add("rbtnlBuqueda", "0")
                .add("txtCaptcha", txtCodigo.getText().toString())
                .add("ddlTipoDocumento", "4")
                .add("txtNroDocumento", txtDni.getText().toString())
                .build();

        final Request request = new Request.Builder()
                .addHeader("Content-type", "application/x-www-form-urlencoded")
                .url("https://slcp.mtc.gob.pe")
                .post(formBody)
                .build();
        Response response;
        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // failure case
                Log.e("re","ERrror");
               // Toast.makeText(PruebaActivity.this, "Errorr", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // success caseStri
               String resultesult = response.body().string();
                Log.e("a", resultesult.toString());
                //System.out.println(response.body().string());
             //  Log.e("re",response.body().string());
             //   Toast.makeText(PruebaActivity.this, "ok", Toast.LENGTH_SHORT).show();

            }
        });


    }
}