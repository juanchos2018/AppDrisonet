package com.example.appdrisonet.Acitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.appdrisonet.Fragment.PapeletasRankingFragment;
import com.example.appdrisonet.Fragment.RankingPuntosFragment;
import com.example.appdrisonet.Fragment.RankingTotalFragment;
import com.example.appdrisonet.PrincipalActivity;
import com.example.appdrisonet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class Ranking extends AppCompatActivity {


    Toolbar toolbar1;
    private boolean viewIsAtHome;

    private TextView tvtitulo;
   private FrameLayout frameLayout;
   private BottomNavigationView botomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        toolbar1 = findViewById(R.id.toolbar1);
        frameLayout = findViewById(R.id.fragmentid);
        botomNavigationView = findViewById(R.id.botomNavigationView);
        tvtitulo = findViewById(R.id.tvtitulo);


        botomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                switch (itemId)
                {
                    case R.id.rankingpuntos:
                        addFragment(new RankingPuntosFragment());
                        tvtitulo.setText("Ranking por puntos");
                        viewIsAtHome = true;
                        break;
                    case R.id.rankingtotal:
                        addFragment(new RankingTotalFragment());
                        tvtitulo.setText("Ranking Total");
                        viewIsAtHome = true;
                        break;
                    case R.id.rankingpapeletas:
                        addFragment(new PapeletasRankingFragment());
                        tvtitulo.setText("Ranking por papeletas");
                        viewIsAtHome= true;
                        break;
                }

                return true;
            }

        });
        botomNavigationView.setSelectedItemId(R.id.rankingtotal);


        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();

                Intent home = new Intent(Ranking.this, PrincipalActivity.class);
                startActivity(home);
                finish();
            }
        });

    }

    private void addFragment (Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentid , fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

}