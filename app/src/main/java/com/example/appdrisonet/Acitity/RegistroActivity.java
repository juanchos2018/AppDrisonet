package com.example.appdrisonet.Acitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.example.appdrisonet.Adapter.PagerAdapter;
import com.example.appdrisonet.Fragment.DatosFragment;
import com.example.appdrisonet.LoginActivity;
import com.example.appdrisonet.R;

public class RegistroActivity extends AppCompatActivity implements DatosFragment.VerificarDatos {


    Button btnPrev,btnNext,btnSubmit;
    TextView tvcontador;
    int vista=0;

    boolean campoLLenoFragmentDatos;
    public  static  String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnPrev=(Button)findViewById(R.id.btn_prev);
        btnNext=(Button)findViewById(R.id.btn_next);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        tvcontador=(TextView)findViewById(R.id.tvcontador);

        final ViewPager pager = findViewById(R.id.pager);
        assert pager != null;

        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(),name));
        final StepperIndicator indicator = findViewById(R.id.stepper_indicator);

     //   indicator.setViewPager(pager, true);
        indicator.setViewPager(pager, pager.getAdapter().getCount());


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vista++;
                if (!campoLLenoFragmentDatos){
                 //   Toast.makeText(RegistroActivity.this, "Campo vacio", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegistroActivity.this, "campo lleno", Toast.LENGTH_SHORT).show();
                }
                pager.setCurrentItem(vista);
                tvcontador.setText(""+vista);

                if (vista == 0) {
                    btnPrev.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                } else if (vista == 1) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                } else if (vista == 2) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }


            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vista--;
                pager.setCurrentItem(vista);
                tvcontador.setText(""+vista);

                if (vista == 0) {
                    btnPrev.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                } else if (vista == 1) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                } else if (vista == 2) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistroActivity.this,LoginActivity.class));
                finish();
            }
        });

/*
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    btnPrev.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                } else if (position == 1) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);
                } else if (position == 2) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        */
        /*
        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                pager.setCurrentItem(step, true);
            }
        });

         */


    }

    private boolean  verifica(){
        DatosFragment a = new DatosFragment();
        return a.verificar();
    }

    @Override
    public void VerificaCampos(boolean va) {
        campoLLenoFragmentDatos=va;
    }

    @Override
    public void onButtonclick(String texto) {

    }
}