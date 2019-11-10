package com.example.astronomicdirclient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.XMLService.XMLHelper;

import org.joda.time.DateTime;

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
        DownloadStarListAsync d = new DownloadStarListAsync();
        d.execute();
        super.onCreate(savedInstanceState);
        initMap();
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.list_page);
        list = viewById.findViewById(R.id.star_list);
        list.setOnItemClickListener((a, v, i, l) ->{});
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        currentPage = findViewById(R.id.main_page);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent it= new Intent(activity, StarActivity.class);
            it.putExtra(STAR_LITE, adapter.getItem(position));
            startActivity(it);
        });
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
        DatePickerDialog tpd = new DatePickerDialog(this,(view, year, month, dayOfMonth) -> {
        }, d.getYear(), d.getMonth(), d.getDay());
        tpd.show();
        tpd.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            DatePicker dp = tpd.getDatePicker();
            DateTime date = new DateTime(dp.getYear(), dp.getMonth() + 1, dp.getDayOfMonth(), 0,0);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final Map<Integer, Integer> itemToPage = new HashMap<>();
    private View currentPage;// = findViewById(R.id.main);
    private void setCurrentPage(int id) {
        if(!itemToPage.containsKey(id)) return;
        View new_page = findViewById(itemToPage.get(id));
        if(new_page.equals(currentPage)) return;
        currentPage.setVisibility(View.INVISIBLE);
        new_page.setVisibility(View.VISIBLE);
        currentPage = new_page;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setCurrentPage(id);
        if(id == R.id.list && adapter.getCount() == 0)
            Toast.makeText(activity, "Failed to load stars!", Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
