package com.example.astronomicdirclient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.XMLService.XMLHelper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class StarActivity extends AppCompatActivity ///implements View.OnClickListener {
{
    private StarFragment fragment;

    public StarLite getStarLite() {
        return starLite;
    }

    private StarLite starLite;
    private DownloadStarAsync d = new DownloadStarAsync();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        starLite = (StarLite)it.getSerializableExtra(MainActivity.STAR_LITE);
        d.execute(starLite.getId());
        setContentView(R.layout.activity_star);
        FloatingActionButton fab = findViewById(R.id.exit);
        final Activity a = this;
        fab.setOnClickListener(view ->
                a.finish());

        fragment = new StarFragment();
        Bundle args = new Bundle();
        //args.putBoolean(SectionsPagerAdapter.EDITABLE, true);
        try {
            args.putSerializable(MainActivity.STAR, d.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cont, fragment)
                .commit();

        colourStatusBar();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void colourStatusBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }
/*
    @Override
    public void onClick(View view) {
        StarTabFragment starTabFragment = fragment.sectionsPagerAdapter.getStarTabFragment();
        try {
            if (viewPager.getCurrentItem() == 0) {
            } else if (!isIsMoonFragment()) {
                starTabFragment.updatePlanet();
                viewPager.setCurrentItem(0);
            } else
                sectionsPagerAdapter.getPlanetTabFragment().updateMoon();
        } catch (Exception e) {
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
        //Snackbar.make(StarActivity.this.findViewById(R.id.cont), "Ok!", Snackbar.LENGTH_SHORT).show();
    }*/


    /**######################################################################**/
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void chooseDate(View v) {
        chooseDate(R.id.date_field);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chooseDate(@IdRes int id) {
        Date d = new Date();
        EditText df = findViewById(id);
        DatePickerDialog tpd = new DatePickerDialog(this,(view, year, month, dayOfMonth) -> {
        }, d.getYear(), d.getMonth(), d.getDay());
        tpd.show();
        tpd.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            DatePicker dp = tpd.getDatePicker();
            DateTime date = new DateTime(dp.getYear(), dp.getMonth() + 1,
                    dp.getDayOfMonth(), 0,0, 0, 0).toDateTime(DateTimeZone.forOffsetHours(5));

            df.setText(date.toString());
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void choosePlanetDate(View v) {
        chooseDate(R.id.date_pl_field);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void chooseMoonDate(View v) {
        chooseDate(R.id.date_mn_field);
    }
    /**######################################################################**/

    private class DownloadStarAsync extends AsyncTask<Integer, Void, Star> {
        @Override
        protected Star doInBackground(Integer... id) {
            try {
                String xmlLine = NetHelper.DownloadStar(id[0]);
                return XMLHelper.DeserializeStar(xmlLine);
            } catch (IOException e) {
                return null;
            }
        }
    }
}
