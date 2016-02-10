package com.altrovis.hasanahtitik;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.GlobalVariable;

import java.util.List;
import java.util.Locale;

public class ArahKiblatActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor magneticSensor;

    private float[] lastAccelerometer;
    private float[] lastMagnetic;

    private boolean lastAccelerometerSet;
    private boolean lastMagneticSet;

    private float[] rotation;
    private float[] orientation;

    private float currentDegree;
    private float qiblaDegree;

    private TextView textViewMyLatitudeLongitude;
    private TextView textViewMyCurrentCity;
    private TextView textViewMyCurrentCountry;
    private TextView textViewDegree;

    private ImageView imageViewKompasBase;
    private ImageView imageViewKompasNorth;
    private ImageView imageViewKompasQibla;

    private GeomagneticField geoFieldUser;
    private Location QiblaLocation;
    private Location MyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arah_kiblat);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Arah Kiblat");
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

        textViewMyLatitudeLongitude = (TextView) findViewById(R.id.TextViewMyLatitudeLongitude);
        textViewMyCurrentCity = (TextView) findViewById(R.id.TextViewMyCurrentCity);
        textViewMyCurrentCountry = (TextView) findViewById(R.id.TextViewMyCurrentCountry);
        SetUpMyLocation();

        imageViewKompasBase = (ImageView) findViewById(R.id.ImageViewKompasBase);
        imageViewKompasNorth = (ImageView) findViewById(R.id.ImageViewKompasNorth);
        imageViewKompasQibla = (ImageView) findViewById(R.id.ImageViewKompasQibla);
        textViewDegree = (TextView) findViewById(R.id.TextViewDegree);
        SetUpCompass();

    }

    private void SetUpMyLocation(){

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

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        MyLocation = locationManager.getLastKnownLocation(provider);

        if (MyLocation != null) {
            textViewMyLatitudeLongitude.setText(MyLocation.getLatitude() + ", " + MyLocation.getLongitude());

            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(MyLocation.getLatitude(), MyLocation.getLongitude(), 1);
                if (addresses.size() > 0) {
                    textViewMyCurrentCity.setText(addresses.get(0).getLocality().toUpperCase());
                    textViewMyCurrentCountry.setText(addresses.get(0).getCountryName().toUpperCase());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            geoFieldUser = new GeomagneticField((float) MyLocation.getLatitude(), (float) MyLocation.getLongitude(),
                    (float)MyLocation.getAltitude(), System.currentTimeMillis());
        }
    }

    private void SetUpCompass(){

        lastAccelerometer = new float[3];
        lastMagnetic = new float[3];

        lastAccelerometerSet = false;
        lastMagneticSet = false;

        rotation = new float[9];
        orientation = new float[3];
        currentDegree = 0f;
        qiblaDegree = 0f;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        QiblaLocation = new Location("reverseGeocoded");
        QiblaLocation.setLatitude(GlobalVariable.QiblaLatitude / 1e6);
        QiblaLocation.setLongitude(GlobalVariable.QiblaLongitude / 1e6);

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, magneticSensor);
        sensorManager.unregisterListener(this, magneticSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == accelerometerSensor) {

            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            lastAccelerometerSet = true;

        } else if (event.sensor == magneticSensor) {

            System.arraycopy(event.values, 0, lastMagnetic, 0, event.values.length);
            lastMagneticSet = true;

        }
        if (lastAccelerometerSet && lastMagneticSet) {

            SensorManager.getRotationMatrix(rotation, null, lastAccelerometer, lastMagnetic);
            SensorManager.getOrientation(rotation, orientation);

            float azimuthInRadians = orientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;

            RotateAnimation rotateAnimationNorth = new RotateAnimation(currentDegree, -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimationNorth.setDuration(1000);
            rotateAnimationNorth.setFillAfter(true);

            imageViewKompasBase.startAnimation(rotateAnimationNorth);
            imageViewKompasNorth.startAnimation(rotateAnimationNorth);

            float azimuthQibla = lastMagnetic[0];
            azimuthQibla += geoFieldUser.getDeclination();
            azimuthQibla = azimuthQibla % 360;

            float directionQibla = azimuthQibla + MyLocation.bearingTo(QiblaLocation);
            directionQibla = 360 + directionQibla;
            directionQibla = directionQibla % 360;

            if(qiblaDegree == 0){
                qiblaDegree = directionQibla - azimuthInDegress;
                qiblaDegree = qiblaDegree % 360;
            }

            RotateAnimation rotateAnimationQibla = new RotateAnimation(currentDegree - qiblaDegree, currentDegree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimationQibla.setDuration(1000);
            rotateAnimationQibla.setFillAfter(true);

            imageViewKompasQibla.startAnimation(rotateAnimationQibla);
            textViewDegree.setText(directionQibla + "");

            currentDegree = -azimuthInDegress;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
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