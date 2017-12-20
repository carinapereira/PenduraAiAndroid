package com.example.carinadossantospereira.shoppingnotebook;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carinadossantospereira.shoppingnotebook.adapters.TabAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        if(tabLayout.getTabCount() > 0){
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_group);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_sales);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_money);
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_settings);

        }
    }
}
