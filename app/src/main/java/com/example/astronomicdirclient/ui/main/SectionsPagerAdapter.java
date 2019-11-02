package com.example.astronomicdirclient.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    /*@StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3 };*/
    private final Context mContext;
    private final Star star;
    public static final String STAR = "STAR";
    public SectionsPagerAdapter(Context context, FragmentManager fm, @Nullable Star star) {
        super(fm);
        this.star = star;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a StarTabFragment (defined as a static inner class below).
        Fragment fragment = position == 0 ? new StarTabFragment() : new PlanetTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(STAR, star);
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