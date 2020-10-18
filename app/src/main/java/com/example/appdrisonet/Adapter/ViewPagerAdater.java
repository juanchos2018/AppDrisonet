package com.example.appdrisonet.Adapter;

import android.text.style.AlignmentSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdater extends FragmentPagerAdapter {

    private  final List<Fragment> lstFragment =new ArrayList<>();
    private  final List<String> lstTitle =new ArrayList<>();
    //constructor

    public ViewPagerAdater(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitle.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    //addFragment
    public void  addFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        lstTitle.add(title);

    }
}
