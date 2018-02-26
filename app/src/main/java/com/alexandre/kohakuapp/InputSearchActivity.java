package com.alexandre.kohakuapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_search);


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
            JSONArray ar = o.getJSONArray("hits");
            JSONObject ob = ar.getJSONObject(0);
            Log.e(TAG, "Parsing: " + ob.getString("name").toString());
        }
        catch (JSONException e) {
            Log.e(TAG, "Could not parse: ", e);
        }

    }


}
