package com.alexandre.kohakuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class HomeSearchActivity extends AppCompatActivity implements View.OnClickListener{

    //private Button gotoSearchByInput, gotoSearchByBrowse, gotoSearchByMap;
    private final static String TAG = "HomeSearch Logs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        setTitle("Home Search");

        Button gotoSearchByInput = (Button) findViewById(R.id.goto_search_by_input);
        gotoSearchByInput.setOnClickListener(this);
        Button gotoSearchByBrowse = (Button) findViewById(R.id.goto_search_by_browse);
        gotoSearchByBrowse.setOnClickListener(this);
        Button gotoSearchByMap = (Button) findViewById(R.id.goto_search_by_map);
        gotoSearchByMap.setOnClickListener(this);


    }

    private void gotoSearchByBrowse(){
        Log.e(TAG, "Going to Continent Browse");
        Intent myIntent = new Intent(getBaseContext(),
                ContinentBrowseActivity.class);
        startActivity(myIntent);
    }

    private void gotoSearchByInput(){
        Log.e(TAG, "Going to Text Search");
        Intent myIntent = new Intent(getBaseContext(),
                InputSearchActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.goto_search_by_input:
                Log.e(TAG, "Search By Input (wip)");
                gotoSearchByInput();
                break;
            case R.id.goto_search_by_browse:
                Log.e(TAG, "Search By Browse");
                gotoSearchByBrowse();
                break;
            case R.id.goto_search_by_map:
                Log.e(TAG, "Search By Map (wip)");
                break;
        }
    }
}
