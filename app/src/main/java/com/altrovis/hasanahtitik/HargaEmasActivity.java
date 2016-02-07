package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Business.HargaEmasAdapter;
import com.altrovis.hasanahtitik.Entitties.HargaEmas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HargaEmasActivity extends Activity {

    ListView listViewHargaEmas;
    ArrayList<HargaEmas> listofHargaEmas;
    TextView textViewTanggalHariIni;
    DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_emas);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Harga Emas Terkini");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        textViewTanggalHariIni = (TextView) findViewById(R.id.TextViewTanggalHariIni);
        dateFormat = new SimpleDateFormat("EEEE, d MMMM y ", new Locale("id", "ID"));

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
        Date currentLocalTime = cal.getTime();
        textViewTanggalHariIni.setText(dateFormat.format(currentLocalTime));

        listViewHargaEmas = (ListView) findViewById(R.id.ListViewHargaEmas);
        try {

            listofHargaEmas = new ArrayList<HargaEmas>();
            HargaEmasAdapter adapter = new HargaEmasAdapter(this, R.layout.list_view_harga_emas, listofHargaEmas);
            listViewHargaEmas.setAdapter(adapter);
        } catch (Exception e) {
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
