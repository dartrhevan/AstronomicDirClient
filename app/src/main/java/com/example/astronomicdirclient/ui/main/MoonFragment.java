package com.example.astronomicdirclient.ui.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Moon;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.R;
import com.example.astronomicdirclient.StarFragment;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.ByteArrayOutputStream;

public class MoonFragment extends Fragment {


    public MoonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View root;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ct = context;
    }
    private Context ct;

    private int getDisplayHeight(AppCompatActivity act) {
        /**DisplayMetrics displaymetrics = act.getResources().getDisplayMetrics();**/
// узнаем размеры экрана из класса Display
        Display display = act.getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        return metricsB.heightPixels;//displaymetrics.widthPixels;
    }
    private Moon moon;
    private boolean editable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_moon, container, false);
        this.root = root;
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view ->
        {
            Fragment fr = this;
            AppCompatActivity a = (AppCompatActivity)ct;
            int w = getDisplayHeight(a);
            View lay = a.findViewById(R.id.lay);
            ObjectAnimator animationY = ObjectAnimator.ofFloat(lay, "Y", lay.getY(), w);
            animationY.setDuration(275);
            animationY.start();
            animationY.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    a.getSupportFragmentManager().beginTransaction()
                            .remove(fr)
                            .commit();
                    StarFragment.setIsMoonFragment(false);
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        });
        Bundle args = getArguments();
        moon = (Moon) args.getSerializable(SectionsPagerAdapter.MOON);
        editable = args.getBoolean(SectionsPagerAdapter.EDITABLE);
        //starTabFragment = (StarTabFragment)args.getSerializable(SectionsPagerAdapter.FRAGMENT);
        if(moon == null) moon = new Moon();
        initializeView(root);
        return root;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    private Planet planet;

    public void initMoon() {
        //Moon moon = (Moon) this.moon.clone();
        //moon.setType(((CheckBox)root.findViewById(R.id.has_surface)).isChecked() ? PlanetType.Tought : PlanetType.Gas);
        moon.setHasAtmosphere(((CheckBox)root.findViewById(R.id.has_atm)).isChecked());
        moon.setName(((EditText)root.findViewById(R.id.name_field)).getText().toString());
        moon.setRadius(Integer.parseInt(((EditText)root.findViewById(R.id.radius_field)).getText().toString()));
        moon.setTemperature(Integer.parseInt(((EditText)root.findViewById(R.id.temp_field)).getText().toString()));
        moon.setInventingDate(DateTime.parse(((EditText)root.findViewById(R.id.date_mn_field)).getText().toString()));
        ImageButton img = root.findViewById(R.id.photo);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        if(bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            moon.setPhoto(byteArray);
        }
        initDist(moon);
        moon.setPlanetOwner(planet.getName());
        moon.setGalaxy(planet.getGalaxy());
        //return moon;
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

    private void initializeView(View root) {
        initField(root, R.id.name_field, moon.getName());
        initField(root, R.id.gal_field, moon.getGalaxy());
        initField(root, R.id.pl_field, moon.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(moon.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(moon.getRadius()));
        initField(root, R.id.dist_field, Integer.toString(moon.getMiddleDistance() != null ?
                moon.getMiddleDistance().getValue() : 0));
        initField(root, R.id.date_mn_field, (moon.getInventingDate()!= null ?
                moon.getInventingDate() : new DateTime(DateTimeZone.forOffsetHours(5))).toString());
        View but = root.findViewById(R.id.ch_mn_date);
        but.setEnabled(editable);
        initSpinner(root);
        byte[] ph = moon.getPhoto();
        if(ph == null) ph = new byte[0];
        ImageButton img = root.findViewById(R.id.photo);
        Bitmap bm = BitmapFactory.decodeByteArray(ph, 0, ph.length);
        img.setImageBitmap(bm);
        img.setOnClickListener(v -> {
            OpenFileDialog fileDialog = new OpenFileDialog(ct);
            fileDialog.setOpenDialogListener(s -> img.setImageBitmap(BitmapFactory.decodeFile(s)));
            fileDialog.show();
        });
        CheckBox acb = root.findViewById(R.id.has_atm);
        acb.setChecked(moon.isHasAtmosphere());
        acb.setEnabled(editable);
    }
    private final static String PLANET = "PLANET";
    public static MoonFragment makeMoonFragment(Moon moon, boolean editable, Planet planet) {
        MoonFragment fragment = new MoonFragment();
        Bundle args = new Bundle();
        args.putSerializable(SectionsPagerAdapter.MOON, moon);
        args.putBoolean(SectionsPagerAdapter.EDITABLE, editable);
        args.putSerializable(PLANET, planet);
        fragment.setArguments(args);
        return  fragment;
    }

    private void initSpinner(View root) {
        Spinner sp = root.findViewById(R.id.spinner);
        if(moon.getMiddleDistance() != null && moon.getMiddleDistance().getUnit() != null)//TODO:To make else with default value assigning
            switch (moon.getMiddleDistance().getUnit()){
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



}
