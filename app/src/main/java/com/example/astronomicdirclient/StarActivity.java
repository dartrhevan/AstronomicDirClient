package com.example.astronomicdirclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.XMLService.XMLHelper;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class StarActivity extends AppCompatActivity {
    private DownloadStarAsync d = new DownloadStarAsync();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        StarLite st = (StarLite)it.getSerializableExtra(MainActivity.STAR_LITE);
        d.execute(st.getId());
        setContentView(R.layout.activity_star);
        FloatingActionButton fab = findViewById(R.id.fab);
        final Activity a = this;
        fab.setOnClickListener(view ->
                a.finish());
        StarFragment fragment = new StarFragment();
        Bundle args = new Bundle();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void colourStatusBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }
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
