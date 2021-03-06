package com.example.astronomicdirclient;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.XMLService.XMLHelper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String STAR_LITE = "STAR_LITE";
    public static final String STAR = "STAR";
    private ListView list;
    private AppCompatActivity activity = this;
    private ArrayAdapter<com.example.astronomicdirclient.Model.StarLite> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        update();
        super.onCreate(savedInstanceState);
        initMap();
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.list_page);
        list = viewById.findViewById(R.id.star_list);
        list.setOnItemClickListener((a, v, i, l) -> {
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WebView browser=( WebView )findViewById(R.id.dens_anim);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("https://nordennavic.github.io/");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        currentPage = findViewById(R.id.main_page);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent it = new Intent(activity, StarActivity.class);
            it.putExtra(STAR_LITE, adapter.getItem(position));
            startActivity(it);
        });
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Deleting of star")
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        StarLite st = adapter.getItem(position);
                        DeleteStarAsync del = new DeleteStarAsync();
                        del.execute(st.getId());
                        adapter.remove(st);
                    })
                    .setNegativeButton("No",
                            (dialog, id1) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });
        initUpButton();
    }

    private void initUpButton() {
        View upbut = findViewById(R.id.update);
        anim = ObjectAnimator.ofFloat(upbut , "rotation", 0, 360);
        anim.setDuration(500);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(4);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                upbut.setRotation(0);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    private void update() {
        DownloadStarListAsync d = new DownloadStarListAsync();
        d.execute();
    }

    private ObjectAnimator anim;// = ObjectAnimator.ofFloat(findViewById(R.id.update), "rotation", 0, 360);
    public void onUpdate(View view) {
        anim.start();
        update();
    }

    private class DeleteStarAsync extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... id) {
            try {
                return NetHelper.DeleteStar(id[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Error!";
        }

        @Override
        protected void onPostExecute(String resp) {
            Snackbar.make(MainActivity.this.findViewById(R.id.list_page), resp, Snackbar.LENGTH_LONG).show();
        }
    }

    private void initMap() {
        itemToPage.put(R.id.list, R.id.list_page);
        itemToPage.put(R.id.main, R.id.main_page);
        itemToPage.put(R.id.add, R.id.fragment);
    }

    private class DownloadStarListAsync extends AsyncTask<Void, Void, List<StarLite>> {
        @Override
        protected List<StarLite> doInBackground(Void... voids) {
            try {
                String xmlLine = NetHelper.DownloadStarList();
                return XMLHelper.DeserializeStarList(xmlLine);
            } catch (IOException e) {
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<StarLite> stars) {
            //stars.add(new StarLite(1, "No stars", ""));
            adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,
                    stars);
            list.setAdapter(adapter);
            Snackbar.make(MainActivity.this.findViewById(R.id.list_page),
            adapter.getCount() == 0 ? "Failed to load stars!" : "Successful loading", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(activity, "Failed to load stars!", Toast.LENGTH_SHORT).show();
            if(anim.isRunning())
                anim.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void chooseDate(View v) {
        chooseDate(R.id.date_field);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chooseDate(@IdRes int id) {
        Date d = new Date();
        EditText df = findViewById(id);
        DatePickerDialog tpd = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
        }, d.getYear(), d.getMonth(), d.getDay());
        tpd.show();
        tpd.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            DatePicker dp = tpd.getDatePicker();
            DateTime date = new DateTime(dp.getYear(), dp.getMonth() + 1,
                    dp.getDayOfMonth(), 0, 0, 0, 0).toDateTime(DateTimeZone.forOffsetHours(5));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            View info = findViewById(R.id.info);
            info.setZ(10);
            info.setVisibility(info.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final Map<Integer, Integer> itemToPage = new HashMap<>();
    private View currentPage;// = findViewById(R.id.main);

    private void setCurrentPage(int id) {
        if (!itemToPage.containsKey(id)) return;
        View new_page = findViewById(itemToPage.get(id));
        if (new_page.equals(currentPage)) return;
        currentPage.setVisibility(View.INVISIBLE);
        new_page.setVisibility(View.VISIBLE);
        currentPage = new_page;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setCurrentPage(id);/*
        if (id == R.id.list && adapter.getCount() == 0)
            Toast.makeText(activity, "Failed to load stars!", Toast.LENGTH_SHORT).show();*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
