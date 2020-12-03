package com.example.appdrisonet.Acitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.example.appdrisonet.Adapter.ViewPagerAdater;
import com.example.appdrisonet.Fragment.HomeFragment;
import com.example.appdrisonet.Fragment.MensajeFragment;
import com.example.appdrisonet.Fragment.PerfilFragment;
import com.example.appdrisonet.R;
import com.google.android.material.tabs.TabLayout;

public class Ranking extends AppCompatActivity {

    private ViewPagerAdater viewPagerAdaterranking;
    private TableLayout tableLayoutranking;
    private ViewPager viewPagerranking;
    private TabLayout tabLayoutranking;

    Toolbar toolbar1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final Toolbar toolbar= findViewById(R.id.toolbarranking);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.app_name));

        tabLayoutranking = findViewById(R.id.tabLayoutranking);
        viewPagerranking = findViewById(R.id.viewPagerranking);

        toolbar1 = findViewById(R.id.toolbar1);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        viewPagerAdaterranking = new ViewPagerAdater(getSupportFragmentManager());

       /* viewPagerAdaterranking.addFragment(new HomeFragment(),"");
        viewPagerAdaterranking.addFragment(new MensajeFragment(),"");
        viewPagerAdaterranking.addFragment(new PerfilFragment(),"");

        viewPagerranking.setAdapter(viewPagerAdaterranking);
        viewPagerranking.setupWithViewPager(viewPagerranking);

        tabLayoutranking.getTabAt(0).setIcon(R.drawable.home);
        tabLayoutranking.getTabAt(1).setIcon(R.drawable.message);
        tabLayoutranking.getTabAt(2).setIcon(R.drawable.perfil);

        tabLayoutranking.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

    }
}