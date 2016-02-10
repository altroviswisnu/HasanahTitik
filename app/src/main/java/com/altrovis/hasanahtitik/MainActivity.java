package com.altrovis.hasanahtitik;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Business.DateHijri;
import com.altrovis.hasanahtitik.Business.PrayTime;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView textViewLokasi;

    TextView textViewCurrentTime;
    TextView textViewNextSholat;
    TextView textViewTimeToNextSholat;

    TextView textViewCurrentDate;
    DateFormat dateFormatCurrentDate;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        textViewLokasi = (TextView) findViewById(R.id.TextViewLokasi);
        SetUpCurrentCity();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        SetUpViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        textViewCurrentDate = (TextView) findViewById(R.id.TextViewCurrentDate);
        dateFormatCurrentDate = new SimpleDateFormat("d MMMM y", new Locale("id", "ID"));
        SetCurrentLocalDate();

        textViewCurrentTime = (TextView) findViewById(R.id.TextViewCurrentTime);
        textViewNextSholat = (TextView) findViewById(R.id.TextViewNextSholat);
        textViewTimeToNextSholat = (TextView) findViewById(R.id.TextViewTimeToNextSholat);
        SetPrayTime();

        SetUpTimer();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void SetPrayTime() {
        try {
            double latitude;
            double longitude;

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location myLocation = locationManager.getLastKnownLocation(provider);

            if (myLocation != null) {
                latitude = myLocation.getLatitude();
                longitude = myLocation.getLongitude();
            } else {
                latitude = -6.1753871;
                longitude = 106.8249641;
            }

            double timezone = (Calendar.getInstance().getTimeZone()
                    .getOffset(Calendar.getInstance().getTimeInMillis()))
                    / (1000 * 60 * 60);

            PrayTime prayers = new PrayTime();

            prayers.setTimeFormat(prayers.Time24);
            prayers.setCalcMethod(prayers.MWL);
            prayers.setAsrJuristic(prayers.Shafii);
            prayers.setAdjustHighLats(prayers.AngleBased);
            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
            prayers.tune(offsets);

            Calendar cal = Calendar.getInstance();

            Calendar calNow = (Calendar)cal.clone();

            ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                    longitude, timezone);
            ArrayList prayerNames = prayers.getTimeNames();

            Calendar waktuIsya = (Calendar) cal.clone();
            waktuIsya.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(prayerTimes.get(6).toString().split(":")[0]), Integer.parseInt(prayerTimes.get(6).toString().split(":")[1]));

            if (cal.compareTo(waktuIsya) > 0) {
                // Bandingkan dengan waktu subuh besok.
                prayers = new PrayTime();

                prayers.setTimeFormat(prayers.Time24);
                prayers.setCalcMethod(prayers.MWL);
                prayers.setAsrJuristic(prayers.Shafii);
                prayers.setAdjustHighLats(prayers.AngleBased);
                prayers.tune(offsets);

                cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, 1);

                prayerTimes = prayers.getPrayerTimes(cal, latitude,
                        longitude, timezone);
                prayerNames = prayers.getTimeNames();

                textViewCurrentTime.setText(prayerTimes.get(0).toString());
                textViewNextSholat.setText("Subuh");

                Calendar waktuSubuh = (Calendar) cal.clone();
                waktuSubuh.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(prayerTimes.get(0).toString().split(":")[0]), Integer.parseInt(prayerTimes.get(0).toString().split(":")[1]));

                long deltaTime = waktuSubuh.getTimeInMillis() - calNow.getTimeInMillis();
                long timeToNextSholat = deltaTime;

                long days = TimeUnit.MILLISECONDS
                        .toDays(timeToNextSholat);
                timeToNextSholat -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS
                        .toHours(timeToNextSholat);
                timeToNextSholat -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS
                        .toMinutes(timeToNextSholat);
                timeToNextSholat -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS
                        .toSeconds(timeToNextSholat);

                textViewTimeToNextSholat.setText(hours + "j " + minutes + "m");
            }
            else
            {
                Calendar waktuSubuh = (Calendar) cal.clone();
                waktuSubuh.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(prayerTimes.get(0).toString().split(":")[0]), Integer.parseInt(prayerTimes.get(0).toString().split(":")[1]));

                if (cal.compareTo(waktuSubuh) <= 0) {
                    textViewCurrentTime.setText(prayerTimes.get(0).toString());
                    textViewNextSholat.setText("Subuh");

                    long deltaTime = waktuSubuh.getTimeInMillis() - calNow.getTimeInMillis();
                    long timeToNextSholat = deltaTime;

                    long days = TimeUnit.MILLISECONDS
                            .toDays(timeToNextSholat);
                    timeToNextSholat -= TimeUnit.DAYS.toMillis(days);

                    long hours = TimeUnit.MILLISECONDS
                            .toHours(timeToNextSholat);
                    timeToNextSholat -= TimeUnit.HOURS.toMillis(hours);

                    long minutes = TimeUnit.MILLISECONDS
                            .toMinutes(timeToNextSholat);
                    timeToNextSholat -= TimeUnit.MINUTES.toMillis(minutes);

                    long seconds = TimeUnit.MILLISECONDS
                            .toSeconds(timeToNextSholat);

                    textViewTimeToNextSholat.setText(hours + "j " + minutes + "m");
                }
                else
                {
                    Calendar waktuZuhur = (Calendar) cal.clone();
                    waktuZuhur.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(prayerTimes.get(2).toString().split(":")[0]), Integer.parseInt(prayerTimes.get(2).toString().split(":")[1]));

                    if (cal.compareTo(waktuZuhur) <= 0) {
                        textViewCurrentTime.setText(prayerTimes.get(2).toString());
                        textViewNextSholat.setText("Zuhur");

                        long deltaTime = waktuZuhur.getTimeInMillis() - calNow.getTimeInMillis();
                        long timeToNextSholat = deltaTime;

                        long days = TimeUnit.MILLISECONDS
                                .toDays(timeToNextSholat);
                        timeToNextSholat -= TimeUnit.DAYS.toMillis(days);

                        long hours = TimeUnit.MILLISECONDS
                                .toHours(timeToNextSholat);
                        timeToNextSholat -= TimeUnit.HOURS.toMillis(hours);

                        long minutes = TimeUnit.MILLISECONDS
                                .toMinutes(timeToNextSholat);
                        timeToNextSholat -= TimeUnit.MINUTES.toMillis(minutes);

                        long seconds = TimeUnit.MILLISECONDS
                                .toSeconds(timeToNextSholat);

                        textViewTimeToNextSholat.setText(hours + "j " + minutes + "m");
                    }
                    else
                    {
                        Calendar waktuAshar = (Calendar) cal.clone();
                        waktuAshar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(prayerTimes.get(3).toString().split(":")[0]), Integer.parseInt(prayerTimes.get(3).toString().split(":")[1]));

                        if (cal.compareTo(waktuAshar) <= 0) {
                            textViewCurrentTime.setText(prayerTimes.get(3).toString());
                            textViewNextSholat.setText("Ashar");

                            long deltaTime = waktuAshar.getTimeInMillis() - calNow.getTimeInMillis();
                            long timeToNextSholat = deltaTime;

                            long days = TimeUnit.MILLISECONDS
                                    .toDays(timeToNextSholat);
                            timeToNextSholat -= TimeUnit.DAYS.toMillis(days);

                            long hours = TimeUnit.MILLISECONDS
                                    .toHours(timeToNextSholat);
                            timeToNextSholat -= TimeUnit.HOURS.toMillis(hours);

                            long minutes = TimeUnit.MILLISECONDS
                                    .toMinutes(timeToNextSholat);
                            timeToNextSholat -= TimeUnit.MINUTES.toMillis(minutes);

                            long seconds = TimeUnit.MILLISECONDS
                                    .toSeconds(timeToNextSholat);

                            textViewTimeToNextSholat.setText(hours + "j " + minutes + "m");
                        }
                        else
                        {
                            Calendar waktuMaghrib = (Calendar) cal.clone();
                            waktuMaghrib.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(prayerTimes.get(5).toString().split(":")[0]), Integer.parseInt(prayerTimes.get(5).toString().split(":")[1]));

                            if (cal.compareTo(waktuMaghrib) <= 0) {
                                textViewCurrentTime.setText(prayerTimes.get(5).toString());
                                textViewNextSholat.setText("Maghrib");

                                long deltaTime = waktuMaghrib.getTimeInMillis() - calNow.getTimeInMillis();
                                long timeToNextSholat = deltaTime;

                                long days = TimeUnit.MILLISECONDS
                                        .toDays(timeToNextSholat);
                                timeToNextSholat -= TimeUnit.DAYS.toMillis(days);

                                long hours = TimeUnit.MILLISECONDS
                                        .toHours(timeToNextSholat);
                                timeToNextSholat -= TimeUnit.HOURS.toMillis(hours);

                                long minutes = TimeUnit.MILLISECONDS
                                        .toMinutes(timeToNextSholat);
                                timeToNextSholat -= TimeUnit.MINUTES.toMillis(minutes);

                                long seconds = TimeUnit.MILLISECONDS
                                        .toSeconds(timeToNextSholat);

                                textViewTimeToNextSholat.setText(hours + "j " + minutes + "m");
                            }
                            else
                            {
                                if (cal.compareTo(waktuIsya) <= 0) {
                                    textViewCurrentTime.setText(prayerTimes.get(6).toString());
                                    textViewNextSholat.setText("Isya");

                                    long deltaTime = waktuIsya.getTimeInMillis() - calNow.getTimeInMillis();
                                    long timeToNextSholat = deltaTime;

                                    long days = TimeUnit.MILLISECONDS
                                            .toDays(timeToNextSholat);
                                    timeToNextSholat -= TimeUnit.DAYS.toMillis(days);

                                    long hours = TimeUnit.MILLISECONDS
                                            .toHours(timeToNextSholat);
                                    timeToNextSholat -= TimeUnit.HOURS.toMillis(hours);

                                    long minutes = TimeUnit.MILLISECONDS
                                            .toMinutes(timeToNextSholat);
                                    timeToNextSholat -= TimeUnit.MINUTES.toMillis(minutes);

                                    long seconds = TimeUnit.MILLISECONDS
                                            .toSeconds(timeToNextSholat);

                                    textViewTimeToNextSholat.setText(hours + "j " + minutes + "m");
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetUpTimer() {

        final Handler mHandler = new Handler();
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                SetCurrentLocalDate();
                SetPrayTime();
            }
        };

        int delay = 0;
        int period = 60000;

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                mHandler.post(mUpdateResults);
            }

        }, delay, period);

    }

    private void SetCurrentLocalDate() {

        Calendar calendar = Calendar.getInstance();
        String TanggalMasehi = dateFormatCurrentDate.format(calendar.getTime());

        String TanggalHijriah = DateHijri.getIslamicDate();
        textViewCurrentDate.setText(TanggalMasehi + " / " + TanggalHijriah);
    }

    private void SetUpCurrentCity() {

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

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);

        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                textViewLokasi.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SetUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HajiUmrohFragment(), "Haji & Umroh");
        adapter.addFragment(new KartuMigranFragment(), "Kartu Migran");
        adapter.addFragment(new HasanahTitikFragment(), "Hasanah Titik");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.altrovis.hasanahtitik/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.altrovis.hasanahtitik/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}