package com.example.astronomicdirclient.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.astronomicdirclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanetTabFragment extends Fragment {


    public PlanetTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_planet, container, false);
        return root;
    }

}
