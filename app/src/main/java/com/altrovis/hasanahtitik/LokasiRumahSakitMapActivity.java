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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.altrovis.hasanahtitik.Business.LokasiRumahSakitHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LokasiRumahSakitMapActivity extends AppCompatActivity {

    private GoogleMap googleMapRumahsakit;
    ArrayList<LokasiRumahSakit> listOfRumahSakit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_rumah_sakit_map);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Map Rumah Sakit");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);

            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.Logo);
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

        context = LokasiRumahSakitMapActivity.this;


        try {
            if (googleMapRumahsakit == null) {
                googleMapRumahsakit = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.mapRumahSakit)).getMap();
            }
            googleMapRumahsakit.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            CheckDatabaseRumahSakit();
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

    public void CheckDatabaseRumahSakit() throws Exception {

        if(LokasiRumahSakitHelper.isEmpty(context)){
            new LokasiRumahSakitAsyncTask().execute();
        } else {
            listOfRumahSakit = LokasiRumahSakitHelper.getListOfRumahSakitFromDataBase(context);
            SetUpMapRumahSakit();
        }

    }

    public void SetUpMapRumahSakit(){

        for (int i = 0; i < listOfRumahSakit.size(); i++) {
            LokasiRumahSakit rumahSakit = listOfRumahSakit.get(i);
            final LatLng koordinatRumahSakit = new LatLng(rumahSakit.getLatitude(), rumahSakit.getLongitude());

            googleMapRumahsakit.addMarker(new MarkerOptions().position(koordinatRumahSakit)
                    .title(rumahSakit.getNama()));

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

        googleMapRumahsakit.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        final LatLng koordinatMyLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

        googleMapRumahsakit.addMarker(new MarkerOptions().position(koordinatMyLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(koordinatMyLocation).zoom(12).build();
        googleMapRumahsakit.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        ImageButton buttonListRumahSakit = (ImageButton)findViewById(R.id.ImageButtonListRumahSakit);
        buttonListRumahSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LokasiRumahSakitMapActivity.this, LokasiRumahSakitListActivity.class);
                startActivity(intent);
            }
        });


    }

    private class LokasiRumahSakitAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        private LokasiRumahSakitAsyncTask(){
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
                listOfRumahSakit = LokasiRumahSakitHelper.
                        getListOfRumahSakitFromWebService(context, GlobalVariable.UrlLokasiRumahSakit);
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
            SetUpMapRumahSakit();
        }
    }

}
