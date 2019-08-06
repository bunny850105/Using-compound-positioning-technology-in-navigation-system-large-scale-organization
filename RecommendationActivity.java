package com.itshareplus.googlemapdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecommendationActivity extends Activity {

    private ImageView book1, book2, book3;
    private Button btnbook1, btnbook2, btnbook3;

    private JSONArray contacts;
    private String TAG = RecommendationActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> recList;

    private String url;
    private String waitflag1;
    private String booktest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recommendation);
            recList = new ArrayList<>();
            book1 = (ImageView) findViewById(R.id.imageView);
            book1.setImageResource(R.drawable.book);
            book2 = (ImageView) findViewById(R.id.imageView2);
            book2.setImageResource(R.drawable.book);
            book3 = (ImageView) findViewById(R.id.imageView3);
            book3.setImageResource(R.drawable.book);

            new GetRec().execute();
            btnbook1 = (Button) findViewById(R.id.button);
            btnbook2 = (Button) findViewById(R.id.button2);
            btnbook3 = (Button) findViewById(R.id.button3);
            //btnbook1.setText("書名");

            int i=0;
            while(waitflag1==null)
            {
                i++;
            }




            btnbook1.setText(recList.get(0).get("書名")+"，"+recList.get(0).get("索書號"));
            btnbook2.setText(recList.get(1).get("書名")+"，"+recList.get(1).get("索書號"));
            btnbook3.setText(recList.get(2).get("書名")+"，"+recList.get(2).get("索書號"));




            btnbook1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequest();
                }
            });
            btnbook2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequest();
                }
            });

             btnbook3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

    }

    private void sendRequest() {
        Intent intent = new Intent();
        intent.setClass(RecommendationActivity.this, LibraryActivity.class);
        startActivity(intent);
    }

    private String getValue(){
        booktest = RegisterActivity.book;

        url ="http://134.208.97.61/IM18A/Recommendation1.aspx?rec="+booktest;


        return url;

    }

    private class GetRec extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RecommendationActivity.this);
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
                        contact.put("喜愛藏書種類", c.getString("喜愛藏書種類"));
                        contact.put("書名", c.getString("書名"));
                        contact.put("索書號", c.getString("索書號"));


                        // adding contact to contact list
                        recList.add(contact);
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
            waitflag1=(String) recList.get(0).get("書名");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }
}




