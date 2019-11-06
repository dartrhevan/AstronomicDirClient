package com.example.astronomicdirclient.ui.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarTabFragment extends Fragment {

    private Star star;
    private boolean editeble;
    public StarTabFragment(){}
    private PlanetTabFragment planetTabFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
    private Context ct;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Bundle args = getArguments();
        star = (Star)args.getSerializable(SectionsPagerAdapter.MODEL);
        //planetTabFragment =(PlanetTabFragment)args.getSerializable(SectionsPagerAdapter.FRAGMENT);
        editeble = args.getBoolean(SectionsPagerAdapter.EDITABLE);
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        if(star != null) initializeView(root);
        Toast.makeText(ct, "StarTabFragment created", Toast.LENGTH_LONG);
        return root;
    }

    private void initializeView(View root) {
        initPlanetList(root);
        initField(root, R.id.name_field, star.getName());
        initField(root, R.id.gal_field, star.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(star.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(star.getRadius()));
        initField(root, R.id.dist_field, Integer.toString(star.getMiddleDistance() != null ?
                    star.getMiddleDistance().getValue() : 0));
        initField(root, R.id.date_field, (star.getInventingDate()!= null ?
                    star.getInventingDate() : new Date()).toString());
        View but = root.findViewById(R.id.ch_date);
        but.setEnabled(editeble);
        initSpinner(root);
        byte[] ph = star.getPhoto();
        if(ph == null) ph = new byte[0];
        ImageView img = root.findViewById(R.id.photo);
        img.setImageBitmap(BitmapFactory.decodeByteArray(ph, 0, ph.length));
    }

    private void initPlanetList(View root) {
        ArrayList<Planet> planetList = new ArrayList<>(star.getPlanets());
        ArrayAdapter<Planet> adapter = new ArrayAdapter<>(ct, android.R.layout.simple_list_item_1, planetList);
        ListView list = root.findViewById(R.id.planets);
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            planetTabFragment.setPlanet(adapter.getItem(position));
            ViewPager viewPager = ((Activity)ct).findViewById(R.id.view_pager);
            viewPager.setCurrentItem(1);
        });
    }

    private void initSpinner(View root) {
        Spinner sp = root.findViewById(R.id.spinner);
        if(star.getMiddleDistance() != null)//TODO:To make else with default value assigning
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
        sp.setEnabled(editeble);
    }

    //private ArrayAdapter<Planet> adapter;
    private void initField(View root, @IdRes int id, String value) {
        EditText field = root.findViewById(id);
        if(value != null)
            field.setText(value);
        field.setEnabled(editeble);
    }

    public void setPlanetTabFragment(PlanetTabFragment planetTabFragment) {
        this.planetTabFragment = planetTabFragment;
    }
}