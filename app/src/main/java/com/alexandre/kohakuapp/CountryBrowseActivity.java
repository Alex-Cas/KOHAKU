package com.alexandre.kohakuapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CountryBrowseActivity extends AppCompatActivity {

    private static String continent_id;

    private final static String TAG = "CountryBrowse Logs";

    private DatabaseReference mDatabase;

    //List of all the countries displayed
    private ArrayList<String> myCountryList;

    private LinearLayout linear;

    //Integer that will generate ID's for Country buttons
    private int id_gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_browse);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            continent_id = extras.getString("continent_id");
            //The key argument here must match that used in the other activity
        }

        setTitle(getStringResourceByName("continent_" + continent_id));

        //Init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myCountryList = new ArrayList<>();
        linear = (LinearLayout) findViewById(R.id.layout);
        id_gen = 0;

        getCountries();

    }


    /*
    This function will read all the rivers in the database
    and add a button for every unique country in the current continent
     */
    private void getCountries(){

        //Get a db reference of the "rivers" folder
        DatabaseReference ref = mDatabase.child("rivers/");

        //Query will filter all rivers which are in the current continent
        //by checking the /continent/ field
        ref.orderByChild("continent/").equalTo(continent_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Get a snapshot of all rivers
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Create an object of the River class
                    //This will automatically fill the fields if there's a match
                    //between the db object and java class
                    River myRiver = postSnapshot.getValue(River.class);

                    //Try to add a button with the river's country name
                    addCountryButton(myRiver);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    /*
    This function will try to create and add a button to get to the river's country page
    Takes an object of the River class in parameter
     */
    private void addCountryButton(River r){
        Log.e(TAG, r.getName());

        //If the country's name is already displayed in a button
        for(String s : myCountryList){
            if(s.equals(r.getCountry())) return;
        }

        //If we get to this point, then the current country is not yet displayed
        myCountryList.add(r.getCountry());

        //Linear layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn = new Button(this);

        //Store the current id generated
        int id = id_gen;
        btn.setId(id);

        //Get the full country name from the resources in the format CONTINENT_COUNTRY
        String countryName = getStringResourceByName(continent_id + "_" + r.getCountry());

        //Set the text of the button
        btn.setText(countryName);

        //Add the button to the layout
        linear.addView(btn, params);

        //Get final variables for a click listener
        final String countryNameFinal = countryName;
        final int iFinal = id;
        final Button btnFinal = ((Button) findViewById(id));

        btnFinal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.e(TAG, "Le bouton " + btnFinal.getText() + " a été cliqué");
                // TODO : redirection vers une liste de rivieres
            }
        });

        //Change the id generator
        id_gen++;
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
