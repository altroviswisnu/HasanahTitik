package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Business.LokasiRumahSakitAdapter;
import com.altrovis.hasanahtitik.Business.LokasiRumahSakitHelper;
import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;

import java.util.ArrayList;

public class LokasiRumahSakitListActivity extends Activity {

    ListView listViewLokasiRumahSakit;
    ArrayList<LokasiRumahSakit> listofRumahSakit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_rumah_sakit_list);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Lokasi Rumah Sakit");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewLokasiRumahSakit = (ListView) findViewById(R.id.ListViewLokasiRumahSakit);
        try {

            listofRumahSakit = LokasiRumahSakitHelper.getAllRumahSakit(this);
            LokasiRumahSakitAdapter adapter = new LokasiRumahSakitAdapter(this, R.layout.list_view_rumah_sakit, listofRumahSakit);
            listViewLokasiRumahSakit.setAdapter(adapter);

            listViewLokasiRumahSakit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
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
