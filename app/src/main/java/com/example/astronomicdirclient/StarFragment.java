package com.example.astronomicdirclient;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astronomicdirclient.R;
import com.example.astronomicdirclient.ui.main.SectionsPagerAdapter;

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
        //setContentView(R.layout.activity_star);
        View v = inflater.inflate(R.layout.activity_star, container, false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(ct, ((AppCompatActivity)ct).getSupportFragmentManager());

        ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            //fm = ((AppCompatActivity)context).getSupportFragmentManager();
        ct = context;
    }
}
