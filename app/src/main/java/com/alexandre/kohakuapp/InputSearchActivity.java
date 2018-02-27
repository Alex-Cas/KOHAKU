package com.alexandre.kohakuapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InputSearchActivity extends AppCompatActivity {

    private final static String TAG = "TextSearch Logs";
    Client client = new Client("LB1Q6RJR95", "7f85033618828217019ec8cea1736c06");
    Index index = client.getIndex("dev_RIVERS");

    private LinearLayout linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_search);

        linear = (LinearLayout) findViewById(R.id.layout);


        //Event when the algolia request is completed
        final CompletionHandler completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                Log.e(TAG, "Hits: " + content.toString());
                parseHits(content);

            }

        };

        final EditText input_search = (EditText) findViewById(R.id.input_search);

        //Action Listener on the input text
        input_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //If the user clicks on "done" on the keyboard
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //Close the keyboard of the user
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    Log.e(TAG, "Input text: " + input_search.getText().toString());

                    //Do a query with the input
                    index.searchAsync(new Query(input_search.getText().toString()), completionHandler);

                    return true;
                }
                return false;
            }
        });

    }

    //WIP (parsing the json object)
    private void parseHits(JSONObject o){

        try {


            printResultInfo(o);


            // Array of results
            JSONArray hits = o.getJSONArray("hits");

            // Number of hits
            int len = o.getInt("nbHits");

            if(len > 0){
                for(int i=0; i<len; i++){
                    Log.e(TAG, "Hit " + i + ": " + hits.getJSONObject(i).getString("name").toString());

                    JSONObject hit = hits.getJSONObject(i);
                    JSONObject hl_hit = hit.getJSONObject("_highlightResult");

                    //Linear layout parameters
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    TextView tv = new TextView(this);

                    tv.append("id: " + hit.getString("_id") + "\n");
                    tv.append("name: " + hit.getString("name") + "\n");
                    tv.append("country: " + hit.getString("country") + "\n");
                    tv.append("objectID: " + hit.getString("objectID") + "\n");
                    tv.append("More: " + hl_hit.getJSONObject("name").getString("value") + "\n\n");

                    linear.addView(tv, params);


                }
            }
            //Log.e(TAG, "Parsing: " + ob.getString("name").toString());
        }
        catch (JSONException e) {
            Log.e(TAG, "Could not parse: ", e);
        }

    }

    private void printResultInfo(JSONObject o){
        //Linear layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        try{
            tv.append("Results for: ");
            tv.append(o.getString("query") + "\n");
            tv.append("Number of hits: ");
            tv.append( Integer.toString(o.getInt("nbHits")) + "\n");
            tv.append("Processing time (ms): ");
            tv.append( Integer.toString(o.getInt("processingTimeMS")) + "\n\n");

            linear.addView(tv, params);

        }catch (JSONException e) {
            Log.e(TAG, "Could not parse: ", e);
        }



    }

}
