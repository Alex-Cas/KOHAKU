package com.alexandre.kohakuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ContinentBrowseActivity extends AppCompatActivity {

    private final static String[] continents = {"NA","SA", "EU", "AF", "AS", "OC"};
    private final static String TAG = "ContinentSearch Logs";
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_browse);
        setTitle("Choose your Continent");

        linear = (LinearLayout) findViewById(R.id.layout);

        addContinentButtons();

    }


    /*
    This function will try to create and add a button to get to the river's country page
    Takes an object of the River class in parameter
     */
    private void addContinentButtons(){

        for(int i=0; i<continents.length; i++){
            Log.e(TAG, continents[i]);

            //Linear layout parameters
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            Button btn = new Button(this);
            btn.setId(i);

            //Get the full continent name from the resources in the format continent_CONTINENTKEY
            String continentName = getStringResourceByName("continent_" + continents[i]);

            //Set the text of the button
            btn.setText(continentName);

            //Add the button to the layout
            linear.addView(btn, params);

            //Get final variables for a click listener
            final int iFinal = i;
            final Button btnFinal = ((Button) findViewById(i));
            btnFinal.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.e(TAG,"Le bouton " + btnFinal.getText() + " a été cliqué");
                    gotoCountryBrowse(continents[iFinal]);
                }
            });
        }
    }

    /*
    Start the CountryBrowse activity
    Takes in parameters the key of the continent
     */
    private void gotoCountryBrowse(String s1) {
        Log.e(TAG, "Going to Country Browse");
        Intent myIntent = new Intent(getBaseContext(),
                CountryBrowseActivity.class);

        //Variable to pass to the next activity
        myIntent.putExtra("continent_id", s1);

        startActivity(myIntent);
    }

    /*
    Goes into the resources of the project and
    returns the string corresponding to the key
    entered in parameter
     */
    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
}
