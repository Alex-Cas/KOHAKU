package com.alexandre.kohakuapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RiverInfoActivity extends AppCompatActivity {

    private int river_id;

    private final static String TAG = "RiverInfo Logs";

    private DatabaseReference mDatabase;

    private River myRiver;

    private TextView idRiver, nameRiver, countryRiver, continentRiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            river_id = extras.getInt("river_id");
            //The key argument here must match that used in the other activity
        }

        //Init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        idRiver = (TextView) findViewById(R.id.idRiver);
        nameRiver = (TextView) findViewById(R.id.nameRiver);
        countryRiver = (TextView) findViewById(R.id.countryRiver);
        continentRiver = (TextView) findViewById(R.id.continentRiver);



        getRiver();

    }

    private void addRiverInfo(){

        setTitle(myRiver.getName());

        idRiver = (TextView) findViewById(R.id.idRiver);
        idRiver.setText("id: " + myRiver.get_id());

        nameRiver = (TextView) findViewById(R.id.nameRiver);
        nameRiver.setText("name: " + myRiver.getName());

        countryRiver = (TextView) findViewById(R.id.countryRiver);
        countryRiver.setText("country: " + myRiver.getCountry());

        continentRiver = (TextView) findViewById(R.id.continentRiver);
        continentRiver.setText("continent: " + myRiver.getContinent());
    }



    private void getRiver(){

        //Get a db reference of the "rivers" folder
        DatabaseReference ref = mDatabase.child("rivers/");

        //Query will retrieve the current river
        //by checking the /_id/ field
        ref.orderByChild("_id/").equalTo(river_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Get a snapshot of all rivers
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Create an object of the River class
                    //This will automatically fill the fields if there's a match
                    //between the db object and java class
                    River r = postSnapshot.getValue(River.class);

                    setMyRiver(r);
                    addRiverInfo();

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setMyRiver(River r){
        this.myRiver = r;
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
