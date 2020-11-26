package com.example.appdrisonet.Acitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appdrisonet.Adapter.AdapterMensajes;
import com.example.appdrisonet.Clases.MensajeEnviar;
import com.example.appdrisonet.Clases.MensajeRecibir;
import com.example.appdrisonet.Clases.SubChat;
import com.example.appdrisonet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference userDatabaseReference;

 //   private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private AdapterMensajes adapter;
    private ImageButton btnEnviarFoto;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference,referenceSubchat;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCadena;
    String id_empresa,id_usuario,nombre_usuario,image_usuario,nombre_empresa,imgempresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
      //  nombre = (TextView) findViewById(R.id.nombre);
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
    //    btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);
       // fotoPerfilCadena = "default_image";nombre_empresa

        id_empresa=getIntent().getStringExtra("id_empresa");
        id_usuario=getIntent().getStringExtra("id_usuario");
        nombre_usuario=getIntent().getStringExtra("nombre_usuario");
        image_usuario=getIntent().getStringExtra("image_usuario");
        nombre_empresa=getIntent().getStringExtra("nombre_empresa");
        imgempresa=getIntent().getStringExtra("image_empresa");
        //   bundle.putString("image_empresa",rutausuario);//rutausuario
        database = FirebaseDatabase.getInstance();
      //  databaseReference = database.getReference("chatrooms").child("222").child("chats") ;//Sala de chat (nombre)
        databaseReference = database.getReference("Chat").child(id_empresa).child(id_usuario) ;//Sala de chat (nombre)
        storage = FirebaseStorage.getInstance();

        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String hora = df.format(c.getTime());
                databaseReference.push().setValue(new MensajeEnviar(txtMensaje.getText().toString(),nombre_usuario,image_usuario,"1", id_usuario,hora));
                SubChat(id_empresa,id_usuario,nombre_usuario,image_usuario,txtMensaje.getText().toString());
                SubChat2(id_empresa,id_usuario,nombre_empresa,imgempresa,txtMensaje.getText().toString());

            }
        });


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MensajeRecibir m = dataSnapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(m);
                //   Log.e("recibe",m.getFotoPerfil());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private  void  SubChat(String idempresa, String id_usuario, String nombre_usuario, String img_usuario, String mensaje){
        referenceSubchat = FirebaseDatabase.getInstance().getReference("SubChat").child(idempresa);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fechas = dateFormat.format(date);
        SubChat obj= new SubChat(idempresa,nombre_usuario,id_usuario,fechas,img_usuario,mensaje);
        referenceSubchat.child(id_usuario).setValue(obj);

    }
    private  void  SubChat2(String idempresa, String id_usuario, String nombre_usuario, String img_usuario, String mensaje){
        referenceSubchat = FirebaseDatabase.getInstance().getReference("SubChat2").child(id_usuario);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fechas = dateFormat.format(date);
        SubChat obj= new SubChat( idempresa  ,nombre_usuario,id_usuario,fechas,img_usuario,mensaje);
        referenceSubchat.child(idempresa).setValue(obj);
     //   txtMensaje.setText("");
    }
    private void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }
}