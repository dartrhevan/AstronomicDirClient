package com.example.astronomicdirclient;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Moon;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.ui.main.SectionsPagerAdapter;
import com.example.astronomicdirclient.ui.main.StarTabFragment;

import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarFragment extends Fragment {


    public StarFragment() {
        // Required empty public constructor
    }

    private Context ct;

    public static boolean isIsMoonFragment() {
        return isMoonFragment;
    }

    public static void setIsMoonFragment(boolean isMoonFragment) {
        StarFragment.isMoonFragment = isMoonFragment;
    }

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
                star, true);//star == defStar || star == null);

        ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = v.findViewById(R.id.fab);
        View.OnClickListener uploadListener = view -> {
            StarTabFragment starTabFragment = sectionsPagerAdapter.getStarTabFragment();
            try {
                if (viewPager.getCurrentItem() == 0) {
                    Star st = starTabFragment.initStar();
                    UploadStarAsync up = new UploadStarAsync();
                    up.execute(st);
                } else if (!isIsMoonFragment()) {
                    starTabFragment.updatePlanet();
                    viewPager.setCurrentItem(0);
                } else
                    sectionsPagerAdapter.getPlanetTabFragment().updateMoon();
            } catch (Exception e) {
                Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        };

        View.OnClickListener editListener= view -> {
            StarTabFragment starTabFragment = sectionsPagerAdapter.getStarTabFragment();
            try {
                if (viewPager.getCurrentItem() == 0) {
                    Star st = starTabFragment.initStar();
                    EditStarAsync up = new EditStarAsync();
                    StarActivity act = (StarActivity)ct;
                    up.execute(act.getStarLite().getId(), st);
                } else if (!isIsMoonFragment()) {
                    starTabFragment.updatePlanet();
                    viewPager.setCurrentItem(0);
                } else
                    sectionsPagerAdapter.getPlanetTabFragment().updateMoon();
            } catch (Exception e) {
                Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        };

        ((Activity)getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fab.setOnClickListener(ct instanceof StarActivity ? editListener : uploadListener);
        return v;
    }

    private class EditStarAsync extends AsyncTask<Object, Void, String>
    {
        @Override
        protected String doInBackground(Object... star) {
            return NetHelper.EditStar((int)star[0], (Star)star[1]);
        }

        @Override
        protected void onPostExecute(String resp) {
            Snackbar.make(getView(), resp, Snackbar.LENGTH_LONG).show();
        }
    }

    private class UploadStarAsync extends AsyncTask<Star, Void, String> {
        @Override
        protected String doInBackground(Star... star) {
            return NetHelper.UploadStar(star[0]);
        }

        @Override
        protected void onPostExecute(String resp) {
            Snackbar.make(getView(), resp, Snackbar.LENGTH_LONG).show();
        }
    }
    private static boolean isMoonFragment = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
}
