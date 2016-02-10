package com.altrovis.hasanahtitik;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Business.DateHijri;
import com.altrovis.hasanahtitik.Business.JadwalSholatAdapter;
import com.altrovis.hasanahtitik.Business.PrayTime;
import com.altrovis.hasanahtitik.Entitties.JadwalSholat;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class JadwalSholatActivity extends AppCompatActivity implements OnDateSelectedListener {


    TextView textViewWaktuSubuh;
    TextView textViewWaktuZuhur;
    TextView textViewWaktuAshar;
    TextView textViewWaktuMaghrib;
    TextView textViewWaktuIsya;
    TextView textViewTanggalSekarang;

    MaterialCalendarView calendarViewWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Jadwal Sholat");
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

        textViewWaktuSubuh = (TextView) findViewById(R.id.TextViewWaktuSubuh);
        textViewWaktuZuhur = (TextView) findViewById(R.id.TextViewWaktuZuhur);
        textViewWaktuAshar = (TextView) findViewById(R.id.TextViewWaktuAshar);
        textViewWaktuMaghrib = (TextView) findViewById(R.id.TextViewWaktuMaghrib);
        textViewWaktuIsya = (TextView) findViewById(R.id.TextViewWaktuIsya);
        textViewTanggalSekarang = (TextView)findViewById(R.id.TextViewTanggalSekarang);

        calendarViewWeek = (MaterialCalendarView) findViewById(R.id.CalendarViewWeek);

        calendarViewWeek.setOnDateChangedListener(this);

        calendarViewWeek.setTopbarVisible(false);

        calendarViewWeek.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        calendarViewWeek.setFirstDayOfWeek(Calendar.SUNDAY);

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);

        calendarViewWeek.setSelectedDate(calendar.getTime());

        calendarViewWeek.setCalendarDisplayMode(CalendarMode.WEEKS);


        Calendar calendarOffset = (Calendar)calendar.clone();
        calendarOffset.add(Calendar.DAY_OF_YEAR, 7);

        calendarViewWeek.setCurrentDate(CalendarDay.from(calendarOffset), true);

        SimpleDateFormat dateFormatCurrentDate = new SimpleDateFormat("d MMMM y", new Locale("id", "ID"));
        String TanggalMasehi = dateFormatCurrentDate.format(calendar.getTime());
        String TanggalHijriah = DateHijri.getIslamicDate();
        textViewTanggalSekarang.setText(TanggalMasehi + " / " + TanggalHijriah);

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
            prayers.setCalcMethod(prayers.Makkah);
            prayers.setAsrJuristic(prayers.Shafii);
            prayers.setAdjustHighLats(prayers.AngleBased);
            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
            prayers.tune(offsets);

            Calendar cal = Calendar.getInstance();
            cal.setTime(calendarViewWeek.getSelectedDate().getDate());

            ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                    longitude, timezone);
            ArrayList prayerNames = prayers.getTimeNames();

            textViewWaktuSubuh.setText(prayerTimes.get(0).toString());
            textViewWaktuZuhur.setText(prayerTimes.get(2).toString());
            textViewWaktuAshar.setText(prayerTimes.get(3).toString());
            textViewWaktuMaghrib.setText(prayerTimes.get(5).toString());
            textViewWaktuIsya.setText(prayerTimes.get(6).toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
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
            prayers.setCalcMethod(prayers.Makkah);
            prayers.setAsrJuristic(prayers.Shafii);
            prayers.setAdjustHighLats(prayers.AngleBased);
            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr, Sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha}
            prayers.tune(offsets);

            Calendar cal = Calendar.getInstance();
            cal.setTime(calendarViewWeek.getSelectedDate().getDate());

            ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                    longitude, timezone);
            ArrayList prayerNames = prayers.getTimeNames();

            textViewWaktuSubuh.setText(prayerTimes.get(0).toString());
            textViewWaktuZuhur.setText(prayerTimes.get(2).toString());
            textViewWaktuAshar.setText(prayerTimes.get(3).toString());
            textViewWaktuMaghrib.setText(prayerTimes.get(5).toString());
            textViewWaktuIsya.setText(prayerTimes.get(6).toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
