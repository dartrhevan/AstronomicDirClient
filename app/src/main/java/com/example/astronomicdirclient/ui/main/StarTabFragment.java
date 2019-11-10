package com.example.astronomicdirclient.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.R;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarTabFragment extends Fragment {


    private Star star;
    private boolean editeble;
    private ArrayAdapter<Planet> adapter;

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
        editeble = args.getBoolean(SectionsPagerAdapter.EDITABLE);
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        this.root = root;
        if(star != null) initializeView(root);
        Toast.makeText(ct, "StarTabFragment created", Toast.LENGTH_LONG);
        return root;
    }


    public Star initStar() {
        star.setName(getName());
        star.setGalaxy(((EditText)root.findViewById(R.id.gal_field)).getText().toString());
        star.setRadius(Integer.parseInt(((EditText)root.findViewById(R.id.radius_field)).getText().toString()));
        star.setTemperature(Integer.parseInt(((EditText)root.findViewById(R.id.temp_field)).getText().toString()));
        star.setInventingDate(DateTime.parse(((EditText)root.findViewById(R.id.date_field)).getText().toString()));
        ImageButton img = root.findViewById(R.id.photo);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        if(bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            star.setPhoto(byteArray);
        }
        initDist();
        return star;
    }

    private void initDist() {
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
        star.setMiddleDistance(new Distance(value, t));
    }
    public void addPlanet(Planet pl)
    {
        adapter.add(pl);
    }
    public void addPlanet()
    {

        adapter.add(planetTabFragment.initPlanet());
    }
    private View root;
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
        ImageButton img = root.findViewById(R.id.photo);
        img.setOnClickListener(v -> {
            OpenFileDialog fileDialog = new OpenFileDialog(ct);
            fileDialog.setOpenDialogListener(s -> img.setImageBitmap(BitmapFactory.decodeFile(s)));
            fileDialog.show();
        });
        img.setImageBitmap(BitmapFactory.decodeByteArray(ph, 0, ph.length));
    }

    private void initPlanetList(View root) {
        ArrayList<Planet> planetList = new ArrayList<>(star.getPlanets());
        adapter = new ArrayAdapter<>(ct, android.R.layout.simple_list_item_1, planetList);
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
        if(star.getMiddleDistance() != null && star.getMiddleDistance().getUnit() != null)//TODO:To make else with default value assigning
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

    public String getName() {
        return ((EditText)root.findViewById(R.id.name_field)).getText().toString();
    }
}