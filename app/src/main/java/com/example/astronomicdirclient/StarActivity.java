package com.example.astronomicdirclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.ui.main.SectionsPagerAdapter;

import org.joda.time.DateTime;


public class StarActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        Intent it = getIntent();
        StarLite st = (StarLite)it.getSerializableExtra(MainActivity.STAR_LITE);

        FloatingActionButton fab = findViewById(R.id.fab);
        final Activity a = this;
        fab.setOnClickListener(view ->
                a.finish());
        StarFragment fragment = new StarFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.STAR, new Star());
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cont, fragment)
                .commit();

        colourStatusBar();
    }

    private void colourStatusBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

}
