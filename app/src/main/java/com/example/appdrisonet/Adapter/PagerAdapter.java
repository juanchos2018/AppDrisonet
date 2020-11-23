package com.example.appdrisonet.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appdrisonet.DialogoFratment.BottonSheetFragment;
import com.example.appdrisonet.Fragment.Datos2Fragment;
import com.example.appdrisonet.Fragment.Datos3Fragment;
import com.example.appdrisonet.Fragment.DatosFragment;
import com.example.appdrisonet.Fragment.MtcFragment;
import com.example.appdrisonet.Fragment.PageFragment;

public class PagerAdapter extends FragmentPagerAdapter implements DatosFragment.VerificarDatos {

  public  static   String nombreGlobal;
    public PagerAdapter(@NonNull FragmentManager fm,String nombre) {
        super(fm);
        nombreGlobal=nombre;
    }
    String nombre;
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DatosFragment.newInstance();
            case 1:
                String nombre ="juan carlos pag";
                return Datos2Fragment.newInstance(nombre);
            case 2:
                return Datos3Fragment.newInstance();
            default:
                return null;
        }
       // return PageFragment.newInstance(position + 1, position == getCount() - 1);
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public void VerificaCampos(boolean va) {

    }

    @Override
    public void onButtonclick(String texto) {
        nombreGlobal=texto;
    }
}
