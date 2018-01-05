package com.alexandre.kohakuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ContinentBrowseActivity extends AppCompatActivity {

    private final static String[] continents = {"NA","SA", "EU", "AF", "AS", "OC"};
    private final static String TAG = "ContinentSearch Logs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_browse);
        setTitle("Choose your Continent");

        LinearLayout linear = (LinearLayout) findViewById(R.id.layout);


        for(int i=0; i<continents.length; i++){
            Log.e(TAG, continents[i]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            Button btn = new Button(this);
            btn.setId(i);
            String continentName = getStringResourceByName("continent_" + continents[i]);

            btn.setText(continentName);
            //btn.setBackgroundColor(Color.rgb(70, 80, 90));
            linear.addView(btn, params);
            final String continentNameFinal = continentName;
            final int iFinal = i;
            final Button btnFinal = ((Button) findViewById(i));
            btnFinal.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.e(TAG,"Le bouton " + btnFinal.getText() + " a été cliqué");
                    gotoCountryBrowse(continents[iFinal], continentNameFinal);
                }
            });
        }
    }

    private void gotoCountryBrowse(String s1, String s2){
        Log.e(TAG, "Going to Country Browse");
        Intent myIntent = new Intent(getBaseContext(),
                CountryBrowseActivity.class);
        myIntent.putExtra("continent_id", s1);
        myIntent.putExtra("continent_name", s2);
        startActivity(myIntent);
    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
}
