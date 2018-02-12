package com.example.carinadossantospereira.shoppingnotebook.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.carinadossantospereira.shoppingnotebook.fragments.CalendarFragment;
import com.example.carinadossantospereira.shoppingnotebook.fragments.ConfigurationFragment;
import com.example.carinadossantospereira.shoppingnotebook.fragments.ListClientFragment;
import com.example.carinadossantospereira.shoppingnotebook.fragments.ListClientSalesFragment;

/**
 * Created by carinadossantospereira on 11/12/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] titleTabs = {"Clientes", "Vendas"};
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new ListClientFragment();
                break;
            case 1 :
                fragment = new ListClientSalesFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return titleTabs.length;
    }



}
