package com.alexandre.kohakuapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CountryBrowseActivity extends AppCompatActivity {

    private static String continent_id, continent_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_browse);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            continent_id = extras.getString("continent_id");
            continent_name = extras.getString("continent_name");
            //The key argument here must match that used in the other activity
        }

        setTitle(continent_id + " (" + continent_name + ")");

    }
}
