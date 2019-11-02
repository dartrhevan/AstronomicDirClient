package com.example.astronomicdirclient.ui.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.R;

import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarTabFragment extends Fragment {
/*
    private static final String ARG_SECTION_NUMBER = "section_number";

    //private PageViewModel pageViewModel;

    public static StarTabFragment newInstance(int index) {
        StarTabFragment fragment = new StarTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    private Star star;

    public StarTabFragment(){}
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        star = (Star)getArguments().getSerializable(SectionsPagerAdapter.STAR);
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        if(star != null) initializeView(root);
        return root;
    }

    private void initializeView(View root) {
        initField(root, R.id.name_field, star.getName());
        initField(root, R.id.gal_field, star.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(star.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(star.getRadius()));
        initField(root, R.id.dist_field, Integer.toString(star.getMiddleDistance().getValue()));
        Date date = star.getInventingDate();
        DatePicker dt = root.findViewById(R.id.date_field);
        if(date != null)
            dt.init(date.getYear(), date.getMonth(), date.getDay(), (view, year, monthOfYear, dayOfMonth) -> {});
        dt.setEnabled(false);
        Spinner sp = root.findViewById(R.id.spinner);
        ///sp.setSelection(star.getMiddleDistance().getUnit().ordinal());
        switch (star.getMiddleDistance().getUnit()){

            case Kilometers:
                sp.setSelection(0);
                break;
            case LightYears:
                sp.setSelection(2);
                break;
            case AstronomicUnits:
                sp.setSelection(1);
                break;
        }
        sp.setEnabled(false);
    }

    private void initField(View root, @IdRes int id, String value) {
        EditText field = root.findViewById(id);
        field.setText(value);
        field.setEnabled(false);
    }
}