package com.alexandre.kohakuapp;

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
            String my = getStringResourceByName("continent_" + continents[i]);

            btn.setText(my);
            //btn.setBackgroundColor(Color.rgb(70, 80, 90));
            linear.addView(btn, params);

            final Button btn1 = ((Button) findViewById(i));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.e(TAG,"Le bouton " + btn1.getText() + " a été cliqué");
                }
            });
        }
    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
}
