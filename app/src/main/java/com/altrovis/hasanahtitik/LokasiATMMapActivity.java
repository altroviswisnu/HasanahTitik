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

import com.altrovis.hasanahtitik.Business.LokasiATMHelper;
import com.altrovis.hasanahtitik.Entitties.LokasiATM;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LokasiATMMapActivity extends Activity {

    private GoogleMap googleMapATM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_atm_map);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Lokasi ATM");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        try {
            if (googleMapATM == null) {
                googleMapATM = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.mapATM)).getMap();
            }
            googleMapATM.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            ArrayList<LokasiATM> listOfATM = LokasiATMHelper.getAllATM(this);
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

            CameraPosition cameraPosition = new CameraPosition.Builder().target(koordinatMyLocation).zoom(12).build();
            googleMapATM.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            ImageButton buttonListATM = (ImageButton)findViewById(R.id.ImageButtonListATM);
            buttonListATM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LokasiATMMapActivity.this, LokasiATMListActivity.class);
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
