package com.example.astronomicdirclient.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.R;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;

import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarTabFragment extends Fragment {

    private Star star;
    private boolean editeble;
    public StarTabFragment(){}


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
        star = (Star)args.getSerializable(SectionsPagerAdapter.STAR);
        editeble = args.getBoolean(SectionsPagerAdapter.EDITEBLE);
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        if(star != null) initializeView(root);
        return root;
    }

    /*public void chooseDate(View v) {
        Date d = new Date();
        DatePickerDialog tpd = new DatePickerDialog(ct,(view, year, month, dayOfMonth) -> {
        }, d.getYear(), d.getMonth(), d.getDay());
        tpd.show();
        //tpd.getDatePicker().
    }*/

    private void initializeView(View root) {
        ArrayList<Planet> planetList = new ArrayList<>(star.Planets);
        adapter = new ArrayAdapter<>(ct, android.R.layout.simple_list_item_1, planetList);
        ListView list = root.findViewById(R.id.planets);
        list.setAdapter(adapter);
        initField(root, R.id.name_field, star.getName());
        initField(root, R.id.gal_field, star.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(star.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(star.getRadius()));
        initField(root, R.id.dist_field, Integer.toString(star.getMiddleDistance().getValue()));
        initField(root, R.id.date_field, star.getInventingDate().toString());
        View but = root.findViewById(R.id.ch_date);
        but.setEnabled(editeble);
        Spinner sp = root.findViewById(R.id.spinner);
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
        byte[] ph = star.getPhoto();
        if(ph != null) {
            ImageView img = root.findViewById(R.id.photo);
            img.setImageBitmap(BitmapFactory.decodeByteArray(ph, 0, ph.length));
        }
    }

    private ArrayAdapter<Planet> adapter;
    private void initField(View root, @IdRes int id, String value) {
        EditText field = root.findViewById(id);
        if(value != null)
            field.setText(value);
        field.setEnabled(editeble);
    }
}