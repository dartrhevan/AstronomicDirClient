package com.example.astronomicdirclient;

import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.astronomicdirclient.Model.StarLite;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.astronomicdirclient.XMLHelper.DeserrializeStarList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView list;
    private AppCompatActivity activity = this;
    private ArrayAdapter<com.example.astronomicdirclient.Model.StarLite> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Download d = new Download();
        d.execute();
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.list_page);
        list = (ListView) viewById.findViewById(R.id.star_list);
        list.setOnItemClickListener((a, v, i, l) ->{});
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        currentPage = findViewById(R.id.main_page);
    }

    private class Download extends AsyncTask<Void, Void, List<StarLite>> {

        @Override
        protected List<StarLite> doInBackground(Void... voids) {
            try {
                String xmlLine = Downloader.DownloadStarList();
                return DeserrializeStarList(xmlLine);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... items) {
        }

        @Override
        protected void onPostExecute(List<StarLite> stars) {
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

    private View currentPage;// = findViewById(R.id.main);
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case R.id.list:
            {
                currentPage.setVisibility(View.INVISIBLE);
                View lisr_page = findViewById(R.id.list_page);
                lisr_page.setVisibility(View.VISIBLE);
                currentPage = lisr_page;
                break;
            }
            case R.id.main:
            {
                currentPage.setVisibility(View.INVISIBLE);
                View main = findViewById(R.id.main_page);
                main.setVisibility(View.VISIBLE);
                currentPage = main;
                break;
            }
            case R.id.upload:
            {
                break;
            }
            case R.id.add:
            {
                break;
            }
            /*default:
                throw new Exception("Bad choice!");*/
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
