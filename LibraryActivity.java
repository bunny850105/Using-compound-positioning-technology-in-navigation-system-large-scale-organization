package com.itshareplus.googlemapdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class LibraryActivity extends Activity implements ZXingScannerView.ResultHandler {

    private JSONArray contacts;
    private String urltest;
    private String TAG = LibraryActivity.class.getSimpleName();
    private ZXingScannerView mScannerView;
    private Button btnLocation1,btnFindEnd,btnback;
    private String input,input2;
    private ImageView gridView;
    private TextView mtvScan1,mtvScan2;
    private ImageView mImageView,map1;
    private Button mButton1;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6;
    private TextView mTextView7,mTextView8;
    private TextView mtvStart;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> bookList;
    private Spinner mSpnEnd;
    private String msEnd;
    private String waitflag1;
    private String url;


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
                it.setClass(LibraryActivity.this,LibraryActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_library);
        Intent intent = this.getIntent();
        String input = intent.getStringExtra("input");
        mtvStart = (TextView) findViewById(R.id.tvStart);
        mtvStart.setText(input);
        mTextView1 = (TextView) findViewById(R.id.textView8);
        mTextView2 = (TextView) findViewById(R.id.textView9);
        mTextView3 = (TextView) findViewById(R.id.textView6);
        mTextView4 = (TextView) findViewById(R.id.textView10);
        mTextView5 = (TextView) findViewById(R.id.book);
        mTextView6 = (TextView) findViewById(R.id.textView4);
        mTextView7 = (TextView) findViewById(R.id.textView3);
        mTextView8 = (TextView) findViewById(R.id.textView7);
        mSpnEnd = (Spinner) findViewById(R.id.spnEnd);
        mSpnEnd.setOnItemSelectedListener(spnEndOnItemSelected);

        bookList = new ArrayList<>();

        btnLocation1 = (Button) findViewById(R.id.btnLocation);
        //etOrigin = (EditText) findViewById(R.id.etOrigin);
        //etDestination = (EditText) findViewById(R.id.etDestination);

        btnLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequest();
            }
        });



    }
    private String getValue(){
        urltest = "http://134.208.97.61/IM18A/qrcode1.aspx?num=";
        url=urltest+mtvStart.getText().toString();
        //url="http://134.208.97.61/IM18A/Login1.aspx?id=410335001&password=123";

        return url;

    }


    private void sendRequest() {
        String origin = mtvStart.getText().toString();
        String origintest ="沁月莊";
        String destination = msEnd;
        String destinationtest ="管理學院";
        if (origin.isEmpty()) {
            Toast.makeText(this, "請掃描書籍QR CODE!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "請選擇起點PC", Toast.LENGTH_SHORT).show();
            return;
        }

        new GetBook().execute();
        int i=0;
        while(waitflag1==null)
        {
            i++;
        }
        // TextView map =(TextView) findViewById(R.id.book);
        if (origin.equals("1") && destination.equals("PC01")) {
            mTextView1.setText(bookList.get(0).get("層")+"層");
            mTextView2.setText(bookList.get(0).get("間")+"間");
            mTextView3.setText(bookList.get(0).get("排")+"排");
            mTextView4.setText(bookList.get(0).get("面")+"面");
            mTextView5.setText(bookList.get(0).get("行")+"行");
            mTextView6.setText(bookList.get(0).get("格")+"格");
            mTextView7.setText("書名:"+bookList.get(0).get("書名"));
            mTextView8.setText("索書號:"+bookList.get(0).get("索書號"));
            map1 = (ImageView) findViewById(R.id.imageView);
            map1.setImageResource(R.drawable.p5);
            //Toast.makeText(this, "65156 QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;

        }
        if (origin.equals("2") && destination.equals("PC01")) {
            mTextView1.setText(bookList.get(0).get("層")+"層");
            mTextView2.setText(bookList.get(0).get("間")+"間");
            mTextView3.setText(bookList.get(0).get("排")+"排");
            mTextView4.setText(bookList.get(0).get("面")+"面");
            mTextView5.setText(bookList.get(0).get("行")+"行");
            mTextView6.setText(bookList.get(0).get("格")+"格");
            mTextView7.setText("書名:"+bookList.get(0).get("書名"));
            mTextView8.setText("索書號:"+bookList.get(0).get("索書號"));
            map1 = (ImageView) findViewById(R.id.imageView);
            map1.setImageResource(R.drawable.p1);
            //Toast.makeText(this, "65156 QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;

        }
        if (origin.equals("3") && destination.equals("PC01")) {
            mTextView1.setText(bookList.get(0).get("層")+"層");
            mTextView2.setText(bookList.get(0).get("間")+"間");
            mTextView3.setText(bookList.get(0).get("排")+"排");
            mTextView4.setText(bookList.get(0).get("面")+"面");
            mTextView5.setText(bookList.get(0).get("行")+"行");
            mTextView6.setText(bookList.get(0).get("格")+"格");
            mTextView7.setText("書名:"+bookList.get(0).get("書名"));
            mTextView8.setText("索書號:"+bookList.get(0).get("索書號"));
            map1 = (ImageView) findViewById(R.id.imageView);
            map1.setImageResource(R.drawable.p2);
            //Toast.makeText(this, "65156 QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;

        }
        if (origin.equals("4") && destination.equals("PC01")) {
            mTextView1.setText(bookList.get(0).get("層")+"層");
            mTextView2.setText(bookList.get(0).get("間")+"間");
            mTextView3.setText(bookList.get(0).get("排")+"排");
            mTextView4.setText(bookList.get(0).get("面")+"面");
            mTextView5.setText(bookList.get(0).get("行")+"行");
            mTextView6.setText(bookList.get(0).get("格")+"格");
            mTextView7.setText("書名:"+bookList.get(0).get("書名"));
            mTextView8.setText("索書號:"+bookList.get(0).get("索書號"));
            map1 = (ImageView) findViewById(R.id.imageView);
            map1.setImageResource(R.drawable.p3);
            //Toast.makeText(this, "65156 QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;

        }
        if (origin.equals("5") && destination.equals("PC01")) {
            mTextView1.setText(bookList.get(0).get("層")+"層");
            mTextView2.setText(bookList.get(0).get("間")+"間");
            mTextView3.setText(bookList.get(0).get("排")+"排");
            mTextView4.setText(bookList.get(0).get("面")+"面");
            mTextView5.setText(bookList.get(0).get("行")+"行");
            mTextView6.setText(bookList.get(0).get("格")+"格");
            mTextView7.setText("書名:"+bookList.get(0).get("書名"));
            mTextView8.setText("索書號:"+bookList.get(0).get("索書號"));
            map1 = (ImageView) findViewById(R.id.imageView);
            map1.setImageResource(R.drawable.p4);
            //Toast.makeText(this, "65156 QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;

        }
        /*map.setText(bookList.get(0).get("層"));
        //TextView
        map.setText(bookList.get(0).get("間"));
        map.setText(bookList.get(0).get("排"));
        map.setText(bookList.get(0).get("面"));
        map.setText(bookList.get(0).get("行"));
        map.setText(bookList.get(0).get("格"));*/


    }
    private AdapterView.OnItemSelectedListener spnEndOnItemSelected = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView parent, View view, int position, long id) {
            msEnd = parent.getSelectedItem().toString();
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
        }
    };

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBook extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LibraryActivity.this);
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

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("層", c.getString("層"));
                        contact.put("間", c.getString("間"));
                        contact.put("排", c.getString("排"));
                        contact.put("面", c.getString("面"));
                        contact.put("行", c.getString("行"));
                        contact.put("格", c.getString("格"));
                        contact.put("書名", c.getString("書名"));
                        contact.put("索書號", c.getString("索書號"));

                        // adding contact to contact list
                        bookList.add(contact);
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
            waitflag1=(String) bookList.get(0).get("層");
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

