package com.example.astronomicdirclient.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;

import java.io.Serializable;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter implements Serializable {

    public static final String MOON = "MOON";
    private final Context mContext;
    private final Star star;
    private final boolean editable;
    public static final String MODEL = "MODEL";
    public static final String EDITABLE = "EDITABLE";
    //public static final String FRAGMENT = "FRAGMENT";

    public StarTabFragment getStarTabFragment() {
        return starTabFragment;
    }

    private StarTabFragment starTabFragment;

    public PlanetTabFragment getPlanetTabFragment() {
        return planetTabFragment;
    }

    private PlanetTabFragment planetTabFragment;// = new PlanetTabFragment();

    public SectionsPagerAdapter(Context context, FragmentManager fm, @Nullable Star star, boolean editable) {
        super(fm);
        this.star = star;
        mContext = context;
        this.editable = editable;
        starTabFragment = StarTabFragment.makeStarTabFragment(star, editable);
        planetTabFragment = PlanetTabFragment.makePlanetTabFragment((star.getPlanets().size() > 0 ?
                (Planet)star.getPlanets().toArray()[0] : null), editable);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a StarTabFragment (defined as a static inner class below).
        Fragment fragment;
        if (position == 0) {
            fragment = starTabFragment;
            starTabFragment.setPlanetTabFragment(planetTabFragment);
        }
        else {
            fragment = planetTabFragment;
            planetTabFragment.setStarTabFragment(starTabFragment);
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

}