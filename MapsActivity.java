package com.itshareplus.googlemapdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.zxing.Result;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.ListView;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener ,ZXingScannerView.ResultHandler {

    private GoogleMap mMap;
    private Button btnFindPath,btnScan,btnback;
    //private EditText etOrigin;
    //private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Polyline mPolylineRoute;
    private Spinner mSpnEnd;
    private String msEnd;
    private ZXingScannerView mScannerView;
    // private EditText metStart;
    private TextView mtvStart;
    private String input;

    private String TAG = MapsActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    //private ListView lv;

    private JSONArray contacts;


    private String waitflag1;

    private static String url; ;

    ArrayList<HashMap<String, String>> placeList;




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
                it.setClass(MapsActivity.this,MapsActivity.class);
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
        setContentView(R.layout.activity_maps);
        Intent intent = this.getIntent();
        String input = intent.getStringExtra("input");
        // Bundle bundle = getIntent().getExtras();
        //String input = bundle.getString("input");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        //etOrigin = (EditText) findViewById(R.id.etOrigin);
        //etDestination = (EditText) findViewById(R.id.etDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequest();
            }
        });


        mtvStart = (TextView) findViewById(R.id.tvStart);
        mtvStart.setText(input);

        mSpnEnd = (Spinner) findViewById(R.id.spnEnd);
        mSpnEnd.setOnItemSelectedListener(spnEndOnItemSelected);

        placeList = new ArrayList<>();


      //  new GetPlace().execute();

    }



    private void sendRequest() {
        String origin = mtvStart.getText().toString();
        String origintest ="沁月莊";
        String destination = msEnd;
        String destinationtest ="管理學院";
        if (origin.isEmpty()) {
            Toast.makeText(this, "請點選SCAN QR CODE建立起點!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "請點選下拉選單選擇終點!", Toast.LENGTH_SHORT).show();
            return;
        }
        new GetPlace().execute();
        int i=0;
        while(waitflag1==null)
        {
            i++;
        }


           //new GetPlace().execute();
            polylinePaths = new ArrayList<>();
            Marker start = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(placeList.get(0).get("地點名稱"))
                    .position(new LatLng(Double.parseDouble(placeList.get(0).get("緯度座標")),Double.parseDouble(placeList.get(0).get("經度座標")))));

            Marker end = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(placeList.get(contacts.length()-1).get("地點名稱"))
                    .position(new LatLng(Double.parseDouble(placeList.get(contacts.length()-1).get("緯度座標")),Double.parseDouble(placeList.get(contacts.length()-1).get("經度座標")))));

            PolylineOptions polylineOpt = new PolylineOptions().width(4).color(Color.BLUE);
            ArrayList<LatLng>listLatLng = new ArrayList<LatLng>();
            for(int k=0;k<contacts.length();k++)
            {
                listLatLng.add(new LatLng(Double.parseDouble(placeList.get(k).get("緯度座標")),Double.parseDouble(placeList.get(k).get("經度座標"))));
            }
            polylineOpt.addAll(listLatLng);
            mPolylineRoute = mMap.addPolyline(polylineOpt);

       // if(origin.equals(origintest) && destination.equals(destinationtest))

    }



    private String getValue(){
        String origin = mtvStart.getText().toString();
        String destination = msEnd;
        String test;
        test= "http://134.208.97.61/IM18A/Json1.aspx?A=";
        if(origin.equals("沁月莊")&&destination.equals("管理學院"))
        {
            url=test+"A";
        }
        if(origin.equals("管理學院")&&destination.equals("圖書館"))
        {
            url=test+"B";
        }
        if(origin.equals("圖書館")&&destination.equals("行政大樓"))
        {
            url=test+"C";
        }
        return url;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng hcmus = new LatLng(23.897664,121.541733);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 15));



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            // ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            // ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
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
    private class GetPlace extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
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
                        contact.put("qrcode編號", c.getString("qrcode編號"));
                        contact.put("地點名稱", c.getString("地點名稱"));
                        contact.put("經度座標", c.getString("經度座標"));
                        contact.put("緯度座標", c.getString("緯度座標"));

                        // adding contact to contact list
                        placeList.add(contact);
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

            waitflag1=(String) placeList.get(0).get("qrcode編號");
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
