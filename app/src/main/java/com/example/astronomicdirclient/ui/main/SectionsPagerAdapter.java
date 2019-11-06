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

    private final Context mContext;
    private final Star star;
    private final boolean editable;
    public static final String MODEL = "MODEL";
    public static final String EDITABLE = "EDITABLE";
    public static final String FRAGMENT = "FRAGMENT";
    private StarTabFragment starTabFragment = new StarTabFragment();
    private PlanetTabFragment planetTabFragment = new PlanetTabFragment();

    public SectionsPagerAdapter(Context context, FragmentManager fm, @Nullable Star star, boolean editable) {
        super(fm);
        this.star = star;
        mContext = context;
        this.editable = editable;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a StarTabFragment (defined as a static inner class below).
        Fragment fragment;
        Bundle bundle = new Bundle();
        if (position == 0) {
            starTabFragment.setPlanetTabFragment(planetTabFragment);
            fragment = starTabFragment;
            bundle.putSerializable(MODEL, star);
            //bundle.putSerializable(FRAGMENT, planetTabFragment);
        }
        else {
            if(star.getPlanets().size() > 0)
                bundle.putSerializable(MODEL, (Planet)star.getPlanets().toArray()[0]);
            else
                bundle.putSerializable(MODEL, null);
            fragment = planetTabFragment;
            planetTabFragment.setStarTabFragment(starTabFragment);
            //bundle.putSerializable(FRAGMENT, starTabFragment);
        }
        bundle.putBoolean(EDITABLE, editable);
        fragment.setArguments(bundle);
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