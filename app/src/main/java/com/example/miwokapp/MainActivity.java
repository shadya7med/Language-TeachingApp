package com.example.miwokapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ViewPager mainViewPager = (ViewPager) findViewById(R.id.main_root_View);
        CustomFragmentsPageAdapter customFragmentsPageAdapter = new CustomFragmentsPageAdapter(this,getSupportFragmentManager());
        mainViewPager.setAdapter(customFragmentsPageAdapter);
        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(mainViewPager);

    }


}