package com.itshareplus.googlemapdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.zxing.Result;

import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Modules.DirectionFinderListener;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SpeechActivity extends FragmentActivity implements ZXingScannerView.ResultHandler{

    private ListView lv;
    private JSONArray contacts;
    private String url ;
    private String TAG = SpeechActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> speechList;
    private ZXingScannerView mScannerView;
    private TextView mtvStart;
    private String input;
    private String waitflag1;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        Intent intent = this.getIntent();
        String input = intent.getStringExtra("input");
        speechList = new ArrayList<>();
        mtvStart = (TextView) findViewById(R.id.tvStart);
        mtvStart.setText(input);
        lv = (ListView) findViewById(R.id.list);
        btnSearch = (Button) findViewById(R.id.btnsearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

    }
    private void sendRequest() {
        String origin = mtvStart.getText().toString();

        if (origin.isEmpty()) {
            Toast.makeText(this, "請點選SCAN QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;
        }

        new GetSpeech().execute();
        int i=0;
        while(waitflag1==null)
        {
            i++;
        }
        ListAdapter adapter = new SimpleAdapter(
                SpeechActivity.this, speechList,
                R.layout.list_item, new String[]{"講題", "時間",
                "地點","講者","簡介"}, new int[]{R.id.title,R.id.time,
                R.id.place, R.id.speecher , R.id.introduction});

        lv.setAdapter(adapter);


    }



    private String getValue(){
        String origin = mtvStart.getText().toString();

        String test;
        test= "http://134.208.97.61/IM18A/Speech1.aspx?place=";

        url = test + origin;

        return url;

    }

    public void onClick(View v){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();
            mScannerView = null;
        }
    }

    public void handleResult(final Result result) {
        //Do anything with result here :D

        Log.w("handleResult", result.getText());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        //builder.setCancelable(false);
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog , int which)
            {
                Intent it = new Intent();
                it.setClass(SpeechActivity.this,SpeechActivity.class);
                // Bundle bundle = new Bundle();

                input = result.getText().toString();
                //bundle.putString("input",input);

                it.putExtra("input",input);

                startActivity(it);
                //MapsActivity.this.finish();


            }

            /*protected void onActivityResult(int requestCode,int resultCode, Intent data)
            {
                switch (resultCode){
                    case RESULT_OK:
                        metStart = it
                }
            }*/

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetSpeech extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SpeechActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(getValue());

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    contacts = new JSONArray(jsonStr);

                    // Getting JSON Array node
                    // JSONArray contacts = jsonarray.getJSONArray(" ");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        //  id = c.getString("qrcode編號");
                        // place = c.getString("地點名稱");
                        // longitude  = c.getString("經度座標");
                        //latitude  = c.getString("緯度座標");



                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("講題", c.getString("講題"));
                        contact.put("時間", c.getString("時間"));
                        contact.put("地點", c.getString("地點"));
                        contact.put("講者", c.getString("講者"));
                        contact.put("簡介", c.getString("簡介"));
                        contact.put("place", c.getString("place"));

                        // adding contact to contact list
                        speechList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            waitflag1=(String) speechList.get(0).get("place");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }


}

