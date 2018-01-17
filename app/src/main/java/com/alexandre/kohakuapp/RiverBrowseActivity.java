package com.alexandre.kohakuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RiverBrowseActivity extends AppCompatActivity {

    private String country_id;

    private final static String TAG = "RiverBrowse Logs";

    private DatabaseReference mDatabase;

    private LinearLayout linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_browse);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            country_id = extras.getString("country_id");
            //The key argument here must match that used in the other activity
        }

        setTitle(getStringResourceByName(country_id));

        //Init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        linear = (LinearLayout) findViewById(R.id.layout);

        getRivers();
    }

    private void getRivers(){

        //Get a db reference of the "rivers" folder
        DatabaseReference ref = mDatabase.child("rivers/");

        //Query will filter all rivers which are in the current country
        //by checking the /country/ field
        ref.orderByChild("country/").equalTo(country_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Get a snapshot of all rivers
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Create an object of the River class
                    //This will automatically fill the fields if there's a match
                    //between the db object and java class
                    River myRiver = postSnapshot.getValue(River.class);

                    //Try to add a button with the river's country name
                    addRiverButton(myRiver);

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
    This function will try to create and add a button to get to the river's info page
    Takes an object of the River class in parameter
     */
    private void addRiverButton(River r){

        Log.e(TAG, r.getName());

        //Linear layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn = new Button(this);

        //Store the current id generated
        int id = r.get_id();
        btn.setId(id);

        //Get the full country name from the resources in the format CONTINENT_COUNTRY
        String riverName = r.getName();

        //Set the text of the button
        btn.setText(riverName);

        //Add the button to the layout
        linear.addView(btn, params);

        //Get final variables for a click listener
        final River finalRiver = r;
        final Button btnFinal = ((Button) findViewById(id));

        btnFinal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.e(TAG, "Le bouton " + btnFinal.getText() + " a été cliqué");
                gotoRiverInfo(finalRiver.get_id());
            }
        });

    }

    private void gotoRiverInfo(int i) {
        Log.e(TAG, "Going to River Info");
        Intent myIntent = new Intent(getBaseContext(),
                RiverInfoActivity.class);

        //Variable to pass to the next activity
        myIntent.putExtra("river_id", i);

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
