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

import com.altrovis.hasanahtitik.Business.DoaManasikAdapter;
import com.altrovis.hasanahtitik.Entitties.DoaManasik;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;

import java.util.ArrayList;

public class DoaManasikListActivity extends Activity {

    ListView listViewDoaManasik;
    ArrayList<DoaManasik> listofDoaManasik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_manasik_list);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Doa-doa Manasik");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewDoaManasik = (ListView) findViewById(R.id.ListViewDoaManasik);
        try {

            listofDoaManasik = new ArrayList<DoaManasik>();
            DoaManasikAdapter adapter = new DoaManasikAdapter(this, R.layout.list_view_doa_manasik, listofDoaManasik);
            listViewDoaManasik.setAdapter(adapter);

            listViewDoaManasik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.UrlWebView = listofDoaManasik.get(position).getUrlWebView();
                    Intent intent = new Intent(DoaManasikListActivity.this, DoaManasikDetailActivity.class);
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
