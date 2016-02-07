package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Business.DoaHarianAdapter;
import com.altrovis.hasanahtitik.Entitties.DoaHarian;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;

import java.util.ArrayList;

public class DoaHarianListActivity extends Activity {

    ListView listViewDoaHarian;
    ArrayList<DoaHarian> listofDoaHarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_harian_list);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Kumpulan Doa Sehari-hari");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewDoaHarian = (ListView) findViewById(R.id.ListViewDoaHarian);
        try {

            listofDoaHarian = new ArrayList<DoaHarian>();
            DoaHarianAdapter adapter = new DoaHarianAdapter(this, R.layout.list_view_doa_harian, listofDoaHarian);
            listViewDoaHarian.setAdapter(adapter);

            listViewDoaHarian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.UrlWebView = listofDoaHarian.get(position).getUrlWebView();
                    Intent intent = new Intent(DoaHarianListActivity.this, DoaHarianDetailActivity.class);
                    startActivity(intent);
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
