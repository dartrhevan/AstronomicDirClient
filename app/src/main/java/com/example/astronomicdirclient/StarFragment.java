package com.example.astronomicdirclient;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Moon;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.ui.main.SectionsPagerAdapter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarFragment extends Fragment {


    public StarFragment() {
        // Required empty public constructor
    }

    private Context ct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        final View v = inflater.inflate(R.layout.activity_star, container, false);
        HashSet<Planet> planets = new HashSet<>();
        Planet earth = new Planet("Earth", "Sun", "Milky Way");
        earth.Moons.add(new Moon("Moon", earth));
        planets.add(earth);
        planets.add(new Planet("Mars", "Sun", "Milky Way"));
        final Star star = new Star("Milky way", null, "Sun", new Distance(1, UnitType.AstronomicUnits), 500000, 5000, new Date(2015, 7, 3), planets);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(ct, ((AppCompatActivity)ct).getSupportFragmentManager(),
                star, true);

        ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
}
