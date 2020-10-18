package com.example.appdrisonet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.example.appdrisonet.Fragment.PerfilFragment;
import com.google.android.material.tabs.TabLayout;

public class PrincipalActivity extends AppCompatActivity {

    private ViewPagerAdater viewPagerAdater;
    private TableLayout tableLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    TextView titulo;
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
        viewPager.setAdapter(viewPagerAdater);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.message);
        tabLayout.getTabAt(2).setIcon(R.drawable.perfil);

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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }
}