package com.example.astronomicdirclient.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    /*@StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3 };*/
    private final Context mContext;
    private final Star star;
    private final boolean editable;
    public static final String STAR = "STAR";
    public static final String EDITEBLE = "EDITEBLE";
    public SectionsPagerAdapter(Context context, FragmentManager fm, @Nullable Star star, boolean editeble) {
        super(fm);
        this.star = star;
        mContext = context;
        this.editable = editeble;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a StarTabFragment (defined as a static inner class below).
        Fragment fragment;
        Bundle bundle = new Bundle();
        if (position == 0) {
            fragment = new StarTabFragment();
            bundle.putSerializable(STAR, star);
        }
        else {
            bundle.putSerializable(STAR, (Planet)star.Planets.toArray()[0]);
            fragment = new PlanetTabFragment();
        }
        bundle.putBoolean(EDITEBLE, editable);
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