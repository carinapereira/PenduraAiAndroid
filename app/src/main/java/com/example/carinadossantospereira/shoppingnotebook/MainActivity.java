package com.example.carinadossantospereira.shoppingnotebook;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.carinadossantospereira.shoppingnotebook.adapters.TabAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createActionBar();

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        if(tabLayout.getTabCount() > 0){
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_group);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_sales);
        }
    }

    private void createActionBar(){
        Toolbar toolbar = findViewById(R.id.toolbar_aux);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pendura Aí");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }
}
