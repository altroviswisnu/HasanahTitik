package com.altrovis.hasanahtitik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.altrovis.hasanahtitik.Business.LokasiATMHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.LokasiATM;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LokasiATMMapActivity extends AppCompatActivity {

    private GoogleMap googleMapATM;
    ArrayList<LokasiATM> listOfATM;
    Context context;
    static final int MAP_ATM_REQUEST = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_atm_map);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Peta ATM");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);

            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.logo);
            android.support.v7.app.ActionBar.LayoutParams layoutParams = new android.support.v7.app.ActionBar.LayoutParams(
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);
            layoutParams.rightMargin = 10;
            layoutParams.width = 120;
            layoutParams.height = 80;
            imageView.setLayoutParams(layoutParams);
            actionBar.setCustomView(imageView);
        }

        context = LokasiATMMapActivity.this;

        try {
            if (googleMapATM == null) {
                googleMapATM = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.mapATM)).getMap();
            }
            googleMapATM.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            CheckDatabaseATM();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CheckDatabaseATM() throws Exception {

        if(LokasiATMHelper.isEmpty(context)){
            new LokasiATMAsyncTask().execute();
        } else {
            listOfATM = LokasiATMHelper.getListOFATMFromDatabase(context);
            SetUpMapATM();
        }

    }

    public void SetUpMapATM(){

        for (int i = 0; i < listOfATM.size(); i++) {
            LokasiATM atm = listOfATM.get(i);
            final LatLng koordinatATM = new LatLng(atm.getLatitude(), atm.getLongitude());

            googleMapATM.addMarker(new MarkerOptions().position(koordinatATM).title(atm.getNama()));

        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMapATM.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        final LatLng koordinatMyLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

        googleMapATM.addMarker(new MarkerOptions().position(koordinatMyLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        GlobalVariable.SelectedCoordinate = koordinatMyLocation;

        CameraPosition cameraPosition = new CameraPosition.Builder().target(koordinatMyLocation).zoom(16).build();
        googleMapATM.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        ImageButton buttonListATM = (ImageButton)findViewById(R.id.ImageButtonListATM);
        buttonListATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LokasiATMMapActivity.this, LokasiATMListActivity.class);
                startActivityForResult(intent, MAP_ATM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MAP_ATM_REQUEST){
            SetZoomLokasiATM();
        }
    }

    private void SetZoomLokasiATM(){

        CameraPosition cameraPosition = new CameraPosition.Builder().target(GlobalVariable.SelectedCoordinate)
                .zoom(16).build();
        googleMapATM.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private class LokasiATMAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        private LokasiATMAsyncTask(){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Silahkan Tunggu");
            progressDialog.show();
        }

        protected void onPreExecute() {
            super.onPreExecute();

            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                listOfATM = LokasiATMHelper.getListOfATMFromWebService(context, GlobalVariable.UrlLokasiATM);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            SetUpMapATM();
        }
    }

}
