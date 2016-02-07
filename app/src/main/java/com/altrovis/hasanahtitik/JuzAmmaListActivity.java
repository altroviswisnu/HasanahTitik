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

import com.altrovis.hasanahtitik.Business.JuzAmmaAdapter;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.JuzAmma;

import java.util.ArrayList;

public class JuzAmmaListActivity extends Activity {

    ListView listViewJuzAmma;
    ArrayList<JuzAmma> listofJuzAmma;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juz_amma_list);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Juz Amma");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewJuzAmma = (ListView) findViewById(R.id.ListViewJuzAmma);
        try {

            listofJuzAmma = new ArrayList<JuzAmma>();
            JuzAmmaAdapter adapter = new JuzAmmaAdapter(this, R.layout.list_view_juz_amma, listofJuzAmma);
            listViewJuzAmma.setAdapter(adapter);

            listViewJuzAmma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.UrlWebView = listofJuzAmma.get(position).getUrlWebView();
                    Intent intent = new Intent(JuzAmmaListActivity.this, JuzAmmaDetailActivity.class);
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
