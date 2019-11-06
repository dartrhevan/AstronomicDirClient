package com.example.astronomicdirclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.ui.main.SectionsPagerAdapter;

import org.joda.time.DateTime;


public class StarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        /*
        setContentView(R.layout.content_star);
        Intent it = getIntent();
        StarLite st = (StarLite)it.getSerializableExtra(MainActivity.STAR_LITE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Star star = new Star("Milky way", null, "Sun", new Distance(1, UnitType.AstronomicUnits), 500000, 5000, new DateTime(2015, 7, 3, 0, 0));
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, this.getSupportFragmentManager(),
                star, true);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        StarFragment fragment = new StarFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.STAR, new Star());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cont, fragment)
                .commit();

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
    }

}
