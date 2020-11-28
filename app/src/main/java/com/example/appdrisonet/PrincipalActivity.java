package com.example.appdrisonet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdrisonet.Adapter.ViewPagerAdater;
import com.example.appdrisonet.Fragment.HomeFragment;
import com.example.appdrisonet.Fragment.MensajeFragment;
import com.example.appdrisonet.Fragment.NotificacionesFragment;
import com.example.appdrisonet.Fragment.PerfilFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PrincipalActivity extends AppCompatActivity {

    private ViewPagerAdater viewPagerAdater;
    private TableLayout tableLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    TextView titulo;
    public FirebaseUser currentUser;
    private final int MIS_PERMISOS = 100;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        final Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.app_name));
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titulo=(TextView)findViewById(R.id.tvtitulo);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager =findViewById(R.id.viewPager);
        viewPagerAdater=new ViewPagerAdater(getSupportFragmentManager());

        viewPagerAdater.addFragment(new HomeFragment(),"");
        viewPagerAdater.addFragment(new MensajeFragment(),"");
        viewPagerAdater.addFragment(new PerfilFragment(),"");
        viewPagerAdater.addFragment(new NotificacionesFragment(),"");
        viewPager.setAdapter(viewPagerAdater);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.message);
        tabLayout.getTabAt(2).setIcon(R.drawable.perfil);
        tabLayout.getTabAt(3).setIcon(R.drawable.notify);

       // tabLayout.getTabAt(0).getIcon().setColorFilter(Color.blue(), PorterDuff.Mode.SRC_IN); R.color.blue_link
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               // tab.getIcon().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                tab.getIcon().setColorFilter(getColor(R.color.blue_link), PorterDuff.Mode.SRC_IN);
                    if (tabLayout.getSelectedTabPosition()==0){
                        titulo.setVisibility(View.VISIBLE);
                    }
                    if (tabLayout.getSelectedTabPosition()==1){
                        titulo.setVisibility(View.GONE);
                      //  toolbar.LayoutParams params = (TableLayout.LayoutParams) toolbar.getLayoutParams();
                      //  Toolbar.LayoutParams params1 =(Toolbar.LayoutParams) toolbar.getLayoutParams();
                        //params1.height = 50;

                       // toolbar.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT));
                    }
                    if (tabLayout.getSelectedTabPosition()==2){
                        titulo.setVisibility(View.GONE);
                    }
                if (tabLayout.getSelectedTabPosition()==3){
                    titulo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(solicitaPermisosVersionesSuperiores()){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        //checking logging, if not login redirect to Login ACTIVITY
        if (currentUser == null ){
            mAuth.signOut();
            logOutUser(); // Return to Login activity
        }
        if (currentUser != null ){
//            userDatabaseReference.child("active_now").setValue("true");

        }
    }

    private void logOutUser() {
        Intent loginIntent =  new Intent(PrincipalActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if((getBaseContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&getBaseContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getBaseContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe conceder los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }
}