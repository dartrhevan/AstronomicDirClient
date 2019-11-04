package com.example.astronomicdirclient.ui.main;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.astronomicdirclient.Model.Moon;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanetTabFragment extends Fragment {

    private View root;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
    private Context ct;

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
        if(planet != null) initializeView(root);
    }

    private Planet planet;
    private boolean editeble;

    public PlanetTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_planet, container, false);
        this.root = root;
        Bundle args = getArguments();
        planet = (Planet)args.getSerializable(SectionsPagerAdapter.MODEL);
        editeble = args.getBoolean(SectionsPagerAdapter.EDITEBLE);
        if(planet != null) initializeView(root);
        return root;
    }

    private void initializeView(View root) {
        /*ArrayList<Moon> planetList = new ArrayList<>(planet.Moons);
        adapter = new ArrayAdapter<>(ct, android.R.layout.simple_list_item_1, planetList);
        ListView list = root.findViewById(R.id.planets);
        list.setAdapter(adapter);*/
        initField(root, R.id.name_field, planet.getName());
        initField(root, R.id.star_field, planet.getStar());
        initField(root, R.id.gal_field, planet.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(planet.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(planet.getRadius()));
        if(planet.getMiddleDistance() != null)//TODO:To make else with default value assigning
            initField(root, R.id.dist_field, Integer.toString(planet.getMiddleDistance().getValue()));
        if(planet.getInventingDate() != null)//TODO:To make else with default value assigning
            initField(root, R.id.date_field, planet.getInventingDate().toString());
        View but = root.findViewById(R.id.ch_pl_date);
        but.setEnabled(editeble);
        Spinner sp = root.findViewById(R.id.spinner);
        if(planet.getMiddleDistance() != null)//TODO:To make else with default value assigning
        switch (planet.getMiddleDistance().getUnit()){
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
        byte[] ph = planet.getPhoto();
        if(ph != null) {//TODO:To make else with default value assigning
            ImageView img = root.findViewById(R.id.photo);
            img.setImageBitmap(BitmapFactory.decodeByteArray(ph, 0, ph.length));
        }
    }

    private ArrayAdapter<Moon> adapter;
    private void initField(View root, @IdRes int id, String value) {
        EditText field = root.findViewById(id);
        if(value != null)
            field.setText(value);
        field.setEnabled(editeble);
    }


}
