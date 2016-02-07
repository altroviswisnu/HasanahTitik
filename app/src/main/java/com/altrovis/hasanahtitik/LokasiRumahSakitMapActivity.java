package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.altrovis.hasanahtitik.Business.LokasiRumahSakitHelper;
import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LokasiRumahSakitMapActivity extends Activity {

    private GoogleMap googleMapRumahsakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_rumah_sakit_map);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Lokasi Rumah Sakit");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        try {
            if (googleMapRumahsakit == null) {
                googleMapRumahsakit = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.mapRumahSakit)).getMap();
            }
            googleMapRumahsakit.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            ArrayList<LokasiRumahSakit> listOfRumahSakit = LokasiRumahSakitHelper.getAllRumahSakit(this);
            for (int i = 0; i < listOfRumahSakit.size(); i++) {
                LokasiRumahSakit rumahSakit = listOfRumahSakit.get(i);
                final  LatLng koordinatRumahSakit = new LatLng(rumahSakit.getLatitude(), rumahSakit.getLongitude());

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

            CameraPosition cameraPosition = new CameraPosition.Builder().target(koordinatMyLocation).zoom(4).build();
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
}
