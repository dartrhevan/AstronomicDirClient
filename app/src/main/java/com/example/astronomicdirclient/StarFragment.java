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
import com.example.astronomicdirclient.ui.main.StarTabFragment;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.HashSet;

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
        final View v = inflater.inflate(R.layout.content_star, container, false);
        HashSet<Planet> planets = new HashSet<>();
        Planet earth = new Planet("Earth", "Sun", "Milky Way");
        earth.getMoons().add(new Moon("Moon", earth));
        planets.add(earth);
        planets.add(new Planet("Mars", "Sun", "Milky Way"));
        final Star defStar = new Star("Milky way", null, "Sun", new Distance(1, UnitType.AstronomicUnits), 500000, 5000, new DateTime(2015, 7, 3, 0, 0), planets);
        Star star = getArguments()!= null ? (Star) getArguments().getSerializable(MainActivity.STAR) : defStar;
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(ct, ((AppCompatActivity)ct).getSupportFragmentManager(),
                star, star == defStar || star == null);

        ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            StarTabFragment starTabFragment = sectionsPagerAdapter.getStarTabFragment();
            try {
            if(viewPager.getCurrentItem() == 0){
                Star st = starTabFragment.initStar();
                    NetHelper.UploadStar(st);

            }
            else {
                starTabFragment.addPlanet();
                viewPager.setCurrentItem(0);
            }
            } catch (Exception e) {
                Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
}
