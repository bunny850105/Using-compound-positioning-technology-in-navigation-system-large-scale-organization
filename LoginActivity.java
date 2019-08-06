package com.itshareplus.googlemapdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends Activity {

    private ImageButton login;
    private ImageButton register;
    public static EditText account, password;
    private ProgressBar progressBar;

    private JSONArray contacts;
    private static String test = "http://134.208.97.61/IM18A/Login1.aspx";
    private String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> loginList;
    private String url;
    private String waitflag1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (ImageButton) findViewById(R.id.imageButton);
        register = (ImageButton) findViewById(R.id.imageButton2);
        account = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginRequest();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegisterRequest();
            }
        });
        loginList = new ArrayList<>();


    }

    private String getValue(){
        //String url;
        //getaccount = account.getText().toString();
        //getpassword =  password.getText().toString();
        url="http://134.208.97.61/IM18A/Login1.aspx?id="+account.getText().toString()+"&password="+password.getText().toString();
        //url="http://134.208.97.61/IM18A/Login1.aspx?id=410335001&password=123";

        return url;

    }


    private void sendLoginRequest() {
        String getaccount = account.getText().toString();
        String getpassword =  password.getText().toString();;
        //String destinationtest = "管理學院";
        if (getaccount.isEmpty()) {
            Toast.makeText(this, "請輸入帳號!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getpassword.isEmpty()) {
            Toast.makeText(this, "請輸入密碼!", Toast.LENGTH_SHORT).show();
            return;
        }





        new GetLogin().execute();

        int i=0;
        while(waitflag1==null)
        {
            i++;
        }

        String testid = loginList.get(0).get("帳號");
        int idl=testid.length();
        int gidl=getaccount.length();
        testid.trim();
        String testpw = loginList.get(0).get("密碼");
        testpw.trim();


        //LoginActivity.this.finish();

        if (getaccount.equals(testid)&&getpassword.equals(testpw)) {
            Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, StartMenuActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void sendRegisterRequest() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        //LoginActivity.this.finish();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetLogin extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
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


