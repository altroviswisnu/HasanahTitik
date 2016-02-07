package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Business.JadwalSholatAdapter;
import com.altrovis.hasanahtitik.Entitties.JadwalSholat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class JadwalSholatActivity extends Activity {

    DateFormat dateFormatWaktuSaatIni;
    DateFormat dateFormatJamSholat;

    TextView textViewWaktuSaatIni;
    TextView textViewJamSholatSelanjutnya;

    ListView listViewJadwalSholat;
    ArrayList<JadwalSholat> listofJadwalSholat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Jadwal Sholat");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewJadwalSholat = (ListView) findViewById(R.id.ListViewJadwalSholat);
        try {

            listofJadwalSholat = new ArrayList<JadwalSholat>();
            JadwalSholatAdapter adapter = new JadwalSholatAdapter(this, R.layout.list_view_jadwal_sholat, listofJadwalSholat);
            listViewJadwalSholat.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dateFormatWaktuSaatIni = new SimpleDateFormat("HH:mm:ss", new Locale("id", "ID"));
        dateFormatJamSholat = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));

        textViewWaktuSaatIni = (TextView) findViewById(R.id.TextViewWaktuSaatIni);
        GetCurrentLocalTime();

        final Handler mHandler = new Handler();
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                GetCurrentLocalTime();
            }
        };

        int delay = 1000;
        int period = 1000;

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                mHandler.post(mUpdateResults);
            }

        }, delay, period);
    }

    private void GetCurrentLocalTime(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
        Date currentLocalTime = cal.getTime();
        textViewWaktuSaatIni.setText(dateFormatWaktuSaatIni.format(currentLocalTime));
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
