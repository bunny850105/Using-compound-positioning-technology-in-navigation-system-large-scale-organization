package com.itshareplus.googlemapdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import android.view.View.OnClickListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText account;
    private EditText password;
    private EditText major;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;
    private CheckBox checkbox5;
    private CheckBox checkbox6;
    private CheckBox checkbox7;
    private CheckBox checkbox8;
    private CheckBox checkbox9;
    private ImageButton register;

    private String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private String url;
    private static String test = "http://134.208.97.61/IM18A/Default.aspx";
    public static String book;

    ArrayList<HashMap<String, String>> contactList;


    //CheckBox狀態改變觸發動作
    private CheckBox.OnCheckedChangeListener chklistener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // TODO Auto-generated method stub
            book = "";
            if (checkbox1.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox2.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox3.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox4.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox5.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox6.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox7.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox8.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }
            if (checkbox9.isChecked()) {
                book = book + "1";
            } else {
                book = book + "0";
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        contactList = new ArrayList<>();
        // new GetContacts().execute();

        register = (ImageButton) findViewById(R.id.imageButton);
        account = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText5);
        name = (EditText) findViewById(R.id.editText);
        major = (EditText) findViewById(R.id.editText7);
        //獲取Layout裡的CheckBox
        checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkbox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkbox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkbox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkbox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkbox7 = (CheckBox) findViewById(R.id.checkBox7);
        checkbox8 = (CheckBox) findViewById(R.id.checkBox8);
        checkbox9 = (CheckBox) findViewById(R.id.checkBox9);



        checkbox1.setOnCheckedChangeListener(chklistener);
        checkbox2.setOnCheckedChangeListener(chklistener);
        checkbox3.setOnCheckedChangeListener(chklistener);
        checkbox4.setOnCheckedChangeListener(chklistener);
        checkbox5.setOnCheckedChangeListener(chklistener);
        checkbox6.setOnCheckedChangeListener(chklistener);
        checkbox7.setOnCheckedChangeListener(chklistener);
        checkbox8.setOnCheckedChangeListener(chklistener);
        checkbox9.setOnCheckedChangeListener(chklistener);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegisterRequest();
            }

        });



    }
    private void sendRegisterRequest() {
        new GetContacts().execute();
        url = test + "?account=" + account.getText().toString() + "&password=" + password.getText().toString() + "&major=" + major.getText().toString() + "&book=" +book+ "&name=" + name.getText().toString();
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);


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

            /*ListAdapter adapter = new SimpleAdapter(
                    MainActivityRegistered.this, contactList,
                    R.layout.list_item, new String[]{"account","password","name", "major",
                    "str"}, new int[]{R.id.account,R.id.password,R.id.name,
                    R.id.major, R.id.str});

            lv.setAdapter(adapter);*/
        }

    }


}


