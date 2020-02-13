package com.example.astronomicdirclient.ui.main;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarTabFragment extends Fragment {


    private Star star;
    private boolean editeble;
    private ArrayAdapter<Planet> adapter;
    private int shift;

    public StarTabFragment() {
    }

    private PlanetTabFragment planetTabFragment;

    private Activity activity;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Bundle args = getArguments();
        activity = getActivity();
        star = (Star) args.getSerializable(SectionsPagerAdapter.MODEL);
        editeble = args.getBoolean(SectionsPagerAdapter.EDITABLE);
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        this.root = root;
        Log.println(Log.DEBUG, "", "H: " + root.getHeight());
        if (star != null) initializeView(root);
        return root;
    }


    public Star initStar() {
        star.setName(getName());
        star.setGalaxy(((EditText) root.findViewById(R.id.gal_field)).getText().toString());
        star.setRadius(Integer.parseInt(((EditText) root.findViewById(R.id.radius_field)).getText().toString()));
        star.setTemperature(Integer.parseInt(((EditText) root.findViewById(R.id.temp_field)).getText().toString()));
        star.setInventingDate(DateTime.parse(((EditText) root.findViewById(R.id.date_field)).getText().toString()));
        ImageButton img = root.findViewById(R.id.photo);
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        if (bitmap != null) {
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
        switch (sp.getSelectedItemPosition()) {
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
        int value = Integer.parseInt(((EditText) root.findViewById(R.id.radius_field)).getText().toString());
        star.setMiddleDistance(new Distance(value, t));
    }

    public void updatePlanet() {
        planetTabFragment.initPlanet();
        adapter.notifyDataSetInvalidated();
        //Planet planets = adapter.getItem(0);
    }


    public static StarTabFragment makeStarTabFragment(Star star, boolean editable) {
        StarTabFragment fragment = new StarTabFragment();
        Bundle args = new Bundle();
        args.putSerializable(SectionsPagerAdapter.MODEL, star);
        args.putBoolean(SectionsPagerAdapter.EDITABLE, editable);
        fragment.setArguments(args);
        return fragment;
    }

    private View root;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeView(View root) {
        initPlanetList(root);
        initField(root, R.id.name_field, star.getName());
        initField(root, R.id.gal_field, star.getGalaxy());
        initField(root, R.id.temp_field, Integer.toString(star.getTemperature()));
        initField(root, R.id.radius_field, Integer.toString(star.getRadius()));
        initField(root, R.id.dist_field, Integer.toString(star.getMiddleDistance() != null ?
                star.getMiddleDistance().getValue() : 0));
        initField(root, R.id.date_field, (star.getInventingDate() != null ?
                star.getInventingDate().toDateTime(DateTimeZone.forOffsetHours(5)) : new DateTime().toDateTime().toDateTime(DateTimeZone.forOffsetHours(5))).toString());
        View but = root.findViewById(R.id.ch_date);
        but.setEnabled(editeble);
        initSpinner(root);
        byte[] ph = star.getPhoto();
        if (ph == null) ph = new byte[0];
        ImageButton img = root.findViewById(R.id.photo);
        img.setOnClickListener(v -> {
            OpenFileDialog fileDialog = new OpenFileDialog(activity);
            fileDialog.setOpenDialogListener(s -> img.setImageBitmap(BitmapFactory.decodeFile(s)));
            fileDialog.show();
        });
        img.setImageBitmap(BitmapFactory.decodeByteArray(ph, 0, ph.length));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    private void initPlanetList(View root) {
        ArrayList<Planet> planetList = new ArrayList<>(star.getPlanets());
        adapter = new ArrayAdapter<>(activity , android.R.layout.simple_list_item_1, planetList);
        ListView list = root.findViewById(R.id.planets);
        list.setAdapter(adapter);
        Consumer<Integer> onItemClick = position ->{
            planetTabFragment.setPlanet(adapter.getItem(position));
            ViewPager viewPager = ((Activity) activity).findViewById(R.id.view_pager);
            viewPager.setCurrentItem(1);
        };
        list.setOnItemClickListener((parent, view, position, id) -> onItemClick.accept(position));
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Deleting of planet")
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Planet pl = adapter.getItem(position);
                        adapter.remove(pl);
                        star.getPlanets().remove(pl);
                    })
                    .setNegativeButton("No",
                            (dialog, id1) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });
        Button add = root.findViewById(R.id.addPlanetBut);
        add.setOnClickListener(v -> {
            Planet pl = new Planet();
            pl.setName("New Planet");
            star.getPlanets().add(pl);
            adapter.add(pl);
                onItemClick.accept(adapter.getCount() - 1);
        });
        setAnimation(root);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setAnimation(View root) {
        View l = root.findViewById(R.id.planets_layout);
        final GestureDetector gdt = new GestureDetector(new GestureListener());
        l.setOnTouchListener((view, event) -> {
            gdt.onTouchEvent(event);
            return true;
        });
        l.setZ(10f);
    }


    private void openPlanets() {
        /**if (shift != 0) return;
         View l = root.findViewById(R.id.planets_layout);

         shift = (int) Math.round((getView().getHeight() - l.getHeight()) * 0.7);
         animateHeight(l, shift);
         */
        View l = root.findViewById(R.id.planets_layout);

        float a = l.getY();
        l.animate().y(y).setDuration(75).start();
        y = a;

        Log.println(Log.DEBUG, "", "New height" + this.getView().getHeight() * 0.7);
        //animateHeight(l, shift);
    }

    private float y = 250;

    private void closePlanets() {
        /**View l = root.findViewById(R.id.planets_layout);
         animateHeight(l, -shift);
         shift = 0;*/
        View l = root.findViewById(R.id.planets_layout);
        l.animate().y(y).setDuration(75).start();
        y = 250;
    }



    private void animateHeight(View v, int newH) {
        ObjectAnimator animationY = ObjectAnimator.ofInt(v, "minimumHeight", v.getHeight(), v.getHeight() + newH);
        animationY.setDuration(75);
        animationY.start();
    }

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                openPlanets();
                return false; // снизу вверх
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                closePlanets();
                return false; // сверху вниз
            }
            return false;
        }
    }

    private void initSpinner(View root) {
        Spinner sp = root.findViewById(R.id.spinner);
        if (star.getMiddleDistance() != null && star.getMiddleDistance().getUnit() != null)//TODO:To make else with default value assigning
            switch (star.getMiddleDistance().getUnit()) {
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
        if (value != null)
            field.setText(value);
        field.setEnabled(editeble);
    }


    public void setPlanetTabFragment(PlanetTabFragment planetTabFragment) {
        this.planetTabFragment = planetTabFragment;
    }

    public String getName() {
        return ((EditText) root.findViewById(R.id.name_field)).getText().toString();
    }
}