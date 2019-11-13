package com.example.astronomicdirclient.ui.main;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Moon;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.PlanetType;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.R;
import com.example.astronomicdirclient.StarFragment;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanetTabFragment extends Fragment {

    private View root;
    private ArrayAdapter<Moon> adapter;
    private MoonFragment moonFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
    private Context ct;

    /*public Planet getPlanet() {
        return planet;
    }*/

    public void setPlanet(Planet planet) {
        this.planet = planet;
        if(planet != null) initializeView(root);
    }

    private Planet planet;
    private boolean editable;
    private StarTabFragment starTabFragment;

    public PlanetTabFragment() {
        // Required empty public constructor
    }

    public void initPlanet() {
        //Planet planet = (Planet) this.planet.clone();
        planet.setType(((CheckBox)root.findViewById(R.id.has_surface)).isChecked() ? PlanetType.Tought : PlanetType.Gas);
        planet.setHasAtmosphere(((CheckBox)root.findViewById(R.id.has_atm)).isChecked());
        planet.setName(((EditText)root.findViewById(R.id.name_field)).getText().toString());
        planet.setGalaxy(((EditText)root.findViewById(R.id.gal_field)).getText().toString());
        planet.setRadius(Integer.parseInt(((EditText)root.findViewById(R.id.radius_field)).getText().toString()));
        planet.setTemperature(Integer.parseInt(((EditText)root.findViewById(R.id.temp_field)).getText().toString()));
        planet.setInventingDate(DateTime.parse(((EditText)root.findViewById(R.id.date_pl_field)).getText().toString()));
        ImageButton img = root.findViewById(R.id.photo);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        if(bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            planet.setPhoto(byteArray);
        }
        initDist(planet);
        planet.setStar(starTabFragment.getName());
        //return planet;
    }

    private void initDist(Planet planet) {
        Spinner sp = root.findViewById(R.id.spinner);
        UnitType t = UnitType.Kilometers;
        switch (sp.getSelectedItemPosition()){
            case 0:
                t = UnitType.Kilometers;
                break;
            case 2:
                t = UnitType.LightYears;
                break;
            case 1:
                t = UnitType.AstronomicUnits;
                break;
        }
        int value = Integer.parseInt(((EditText)root.findViewById(R.id.radius_field)).getText().toString());
        planet.setMiddleDistance(new Distance(value, t));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_planet, container, false);
        this.root = root;
        Bundle args = getArguments();
        planet = (Planet)args.getSerializable(SectionsPagerAdapter.MODEL);
        editable = args.getBoolean(SectionsPagerAdapter.EDITABLE);
        //starTabFragment = (StarTabFragment)args.getSerializable(SectionsPagerAdapter.FRAGMENT);
        if(planet == null) planet = new Planet();
        initializeView(root);
        return root;
    }

    private void initializeView(View root) {

        initMoonsList(root);
        initField(root, R.id.name_field, planet.getName());
        initField(root, R.id.star_field, planet.getStar());
        initField(root, R.id.gal_field, planet.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(planet.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(planet.getRadius()));
        initField(root, R.id.dist_field, Integer.toString(planet.getMiddleDistance() != null ?
                planet.getMiddleDistance().getValue() : 0));
        initField(root, R.id.date_pl_field, (planet.getInventingDate()!= null ?
                planet.getInventingDate() : new DateTime(DateTimeZone.forOffsetHours(5))).toString());
        View but = root.findViewById(R.id.ch_pl_date);
        but.setEnabled(editable);
        initSpinner(root);
        byte[] ph = planet.getPhoto();
        if(ph == null) ph = new byte[0];
        ImageButton img = root.findViewById(R.id.photo);
        img.setOnClickListener(v -> {
            OpenFileDialog fileDialog = new OpenFileDialog(ct);
            fileDialog.setOpenDialogListener(s -> img.setImageBitmap(BitmapFactory.decodeFile(s)));
            fileDialog.show();
        });
        img.setImageBitmap(BitmapFactory.decodeByteArray(ph, 0, ph.length));
        CheckBox acb = root.findViewById(R.id.has_atm);
        acb.setChecked(planet.isHasAtmosphere());
        CheckBox tcb = root.findViewById(R.id.has_surface);
        tcb.setChecked(planet.getType() == PlanetType.Tought);
        acb.setEnabled(editable);
        tcb.setEnabled(editable);
        Button add = root.findViewById(R.id.addMoonBut);
        add.setOnClickListener(v -> {
            Moon pl = new Moon();
            pl.setName("New Moon");
            planet.getMoons().add(pl);
            //pl.getPlanets().add(pl);
            adapter.add(pl);
        });
    }



    public static PlanetTabFragment makePlanetTabFragment(Planet planet, boolean editable) {
        PlanetTabFragment fragment = new PlanetTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(SectionsPagerAdapter.MODEL, planet);
        args.putBoolean(SectionsPagerAdapter.EDITABLE, editable);
        fragment.setArguments(args);
        return  fragment;
    }

    private void initMoonsList(View root) {
        ArrayList<Moon> planetList = new ArrayList<>(planet.getMoons());
        adapter = new ArrayAdapter<>(ct, android.R.layout.simple_list_item_1, planetList);
        ListView list = root.findViewById(R.id.moons);
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            AppCompatActivity a = (AppCompatActivity)ct;
            moonFragment = MoonFragment.makeMoonFragment( planetList.get(position), editable, planet);
            int w = getDisplayHeight(a);
            View lay = a.findViewById(R.id.lay);
            lay.setY(w);
            a.getSupportFragmentManager().beginTransaction()
                    .add(R.id.lay, moonFragment)
                    .commit();
            moonFragment.setPlanet(planet);
            StarFragment.setIsMoonFragment(true);
            ObjectAnimator animationY = ObjectAnimator.ofFloat(lay, "Y", lay.getY(), 0);
            animationY.setDuration(325);
            animationY.start();
        });
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ct);
            builder.setTitle("Deleting of moon")
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Moon moon = adapter.getItem(position);
                        adapter.remove(moon);
                        planet.getMoons().remove(moon);
                    })
                    .setNegativeButton("No",
                            (dialog, id1) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });
    }

    public void dropMoonFragment() {
        moonFragment = null;
    }

    public void updateMoon() {
        moonFragment.initMoon();
        //adapter.add(moonFragment.initMoon());
        ((AppCompatActivity)ct).getSupportFragmentManager().beginTransaction()
                .remove(moonFragment)
                .commit();
        StarFragment.setIsMoonFragment(false);
        dropMoonFragment();
    }
    private int getDisplayHeight(AppCompatActivity act) {
        //DisplayMetrics displaymetrics = act.getResources().getDisplayMetrics();
// узнаем размеры экрана из класса Display
        Display display = act.getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        return metricsB.heightPixels;//displaymetrics.widthPixels;
    }
    private void initSpinner(View root) {
        Spinner sp = root.findViewById(R.id.spinner);
        if(planet.getMiddleDistance() != null && planet.getMiddleDistance().getUnit() != null)//TODO:To make else with default value assigning
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
        sp.setEnabled(editable);
    }

    //private ArrayAdapter<Moon> adapter;
    private void initField(View root, @IdRes int id, String value) {
        EditText field = root.findViewById(id);
        if(value != null)
            field.setText(value);
        field.setEnabled(editable);
    }


    public StarTabFragment getStarTabFragment() {
        return starTabFragment;
    }

    public void setStarTabFragment(StarTabFragment starTabFragment) {
        this.starTabFragment = starTabFragment;
    }
}
