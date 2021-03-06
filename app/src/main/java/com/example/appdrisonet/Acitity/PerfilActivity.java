package com.example.appdrisonet.Acitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdrisonet.LoginActivity;
import com.example.appdrisonet.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class PerfilActivity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView imgfoto;
    private String path;//almacena la ruta de la imagen
    private final static int GALLERY_PICK_CODE = 1;
    Bitmap thumb_Bitmap = null;
    private ProgressDialog progressDialog;
    private static final int COD_SELECCIONA = 10;
    Uri uri;

    private DatabaseReference referenceUsuarios;
    private FirebaseAuth mAuth;

    private FirebaseUser user;

    public FirebaseUser currentUser;
    private StorageReference mStorageRef;

    private StorageReference storageReference;
    private static final int COD_FOTO = 20;
    String user_id;
    private final int MIS_PERMISOS = 100;
    TextView tvdatos;
    ImageButton imageButton;

    private ImageView recuperarclave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        toolbar = findViewById(R.id.toolbar1);
        tvdatos=(TextView)findViewById(R.id.nombresusuario);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        user_id =  mAuth.getCurrentUser().getUid();
        referenceUsuarios = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_id);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        imageButton=(ImageButton)findViewById(R.id.btnregistrar);
        recuperarclave = findViewById(R.id.recuperarclave);
        imgfoto=(ImageView)findViewById(R.id.imgperfil);
        referenceUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String img_usuario = dataSnapshot.child("image_usuario").getValue().toString();
                String nombre = dataSnapshot.child("nombre_usuario").getValue().toString();
                String apellido = dataSnapshot.child("apellido_usuario").getValue().toString();

                tvdatos.setText(nombre+ " "+apellido);


                if (img_usuario.equals("default_image")){
                    imgfoto.setImageResource(R.drawable.default_profile_image);

                }
                else{
                    Glide.with(getApplicationContext())
                            .load(img_usuario)
                            .placeholder(R.drawable.default_profile_image)
                            .fitCenter()
                            .centerCrop()
                            .error(R.drawable.default_profile_image)
                            .into(imgfoto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);// 10
            }
        });

        recuperarclave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PerfilActivity.this,Clave.class));
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //TODO : ESTO SELECCIONA DE LA GALERIA
            case COD_SELECCIONA:
                 if (data==null){
                    Toast.makeText(PerfilActivity.this, "No selecciono una imagen", Toast.LENGTH_SHORT).show();
                    return;
                }
                uri=data.getData();
                imgfoto.setImageURI(uri);

                try {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setTitle("Subiendo..");
                    progressDialog.setProgress(0);
                    progressDialog.show();
                    final StorageReference mountainsRef=mStorageRef.child("Imagenes").child(uri.getLastPathSegment());
                    imgfoto.setDrawingCacheEnabled(true);
                    imgfoto.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgfoto.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] path = baos.toByteArray();

                    final UploadTask uploadTask = mountainsRef.putBytes(path);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    return mountainsRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        referenceUsuarios.child("image_usuario").setValue(downloadUri.toString());
                                    } else {
                                        Toast.makeText(PerfilActivity.this, "Error al subir", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            int currentprogress= (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setProgress(currentprogress);
                            if (currentprogress==100){
                                progressDialog.dismiss();
                                String mensaje="Foto subida";
                                Toast.makeText(PerfilActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Errror", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
               }

                break;
            default:
                Toast.makeText(this, "No existe la foto", Toast.LENGTH_SHORT).show();
                break;
        }
;

    }
}