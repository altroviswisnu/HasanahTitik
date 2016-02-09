package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Business.LokasiATMAdapter;
import com.altrovis.hasanahtitik.Business.LokasiATMHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.LokasiATM;

import java.util.ArrayList;

public class LokasiATMListActivity extends Activity {

    ListView listViewLokasiATM;
    ArrayList<LokasiATM> listofATM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_atm_list);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Lokasi ATM");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewLokasiATM = (ListView) findViewById(R.id.ListViewLokasiATM);
        try {

            listofATM = LokasiATMHelper.getListOfATMFromWebService(this, GlobalVariable.UrlLokasiATM);
            LokasiATMAdapter adapter = new LokasiATMAdapter(this, R.layout.list_view_lokasi_atm, listofATM);
            listViewLokasiATM.setAdapter(adapter);

            listViewLokasiATM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
