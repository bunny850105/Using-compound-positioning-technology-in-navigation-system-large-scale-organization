package com.itshareplus.googlemapdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class InformationActivity extends Activity {

    private JSONArray contacts;
    private static String test = "http://134.208.97.61/IM18A/Login1.aspx";
    private String TAG = InformationActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> loginList;
    private String url;
    public static TextView showname,showid,showdep,showbook,showbooktext;
    private String waitflag1;
    private String account1, password1;
    private ImageView name,account,department,lovebook;
    private ImageView logo;

    //public String getaccount;
    //public String getpassward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        name = (ImageView) findViewById(R.id.imageView5);
        name.setImageResource(R.drawable.name);
        account = (ImageView) findViewById(R.id.imageView6);
        account.setImageResource(R.drawable.account);
        department = (ImageView) findViewById(R.id.imageView7);
        department.setImageResource(R.drawable.department);
        lovebook = (ImageView) findViewById(R.id.imageView8);
        lovebook.setImageResource(R.drawable.lovebook);
       // show.setText(loginList.get(0).get("帳號"));
        loginList = new ArrayList<>();
        showname = (TextView)findViewById(R.id.textView16);
        showid = (TextView)findViewById(R.id.textView17);
        showdep = (TextView)findViewById(R.id.textView18);
       // showbooktext = (TextView)findViewById(R.id.textView26);
        showbook = (TextView)findViewById(R.id.textView26);
        logo = (ImageView) findViewById(R.id.imageView9);
        logo.setImageResource(R.drawable.fivemap);

        new GetLogin().execute();
        int i=0;
        while(waitflag1==null)
        {
            i++;
        }

        showname.setText( "姓名"+loginList.get(0).get("姓名"));
        showid.setText( "帳號:"+loginList.get(0).get("帳號"));
        showdep.setText( "科系："+loginList.get(0).get("科系"));
        showbook.setText("喜歡的藏書種類"+loginList.get(0).get("喜歡的藏書種類"));

        // 顯示結果

       // url="http://134.208.97.61/IM18A/Login1.aspx?id="+getaccount+"&password="+getpassword;



    }

    private String getValue(){

        account1 = LoginActivity.account.getText().toString();
        password1 = LoginActivity.password.getText().toString();

        url="http://134.208.97.61/IM18A/Login1.aspx?id="+account1+"&password="+password1;

        return url;

    }





    /**
     * Async task class to get json by making HTTP call
     */
    private class GetLogin extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(InformationActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response


            //String jsonStr = sh.makeServiceCall(getValue());
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
                        contact.put("帳號", c.getString("帳號"));
                        contact.put("密碼", c.getString("密碼"));
                        contact.put("科系", c.getString("科系"));
                        contact.put("喜歡的藏書種類", c.getString("喜歡的藏書種類"));
                        contact.put("姓名", c.getString("姓名"));

                        // adding contact to contact list
                        loginList.add(contact);
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
            waitflag1=(String) loginList.get(0).get("帳號");
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