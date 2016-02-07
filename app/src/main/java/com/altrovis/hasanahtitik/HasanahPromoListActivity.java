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

import com.altrovis.hasanahtitik.Business.HasanahPromoAdapter;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.HasanahPromo;

import java.util.ArrayList;

public class HasanahPromoListActivity extends Activity {

    ListView listViewHasanahPromo;
    ArrayList<HasanahPromo> listofHasanahPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasanah_promo_list);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Hasanah Promo");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewHasanahPromo = (ListView) findViewById(R.id.ListViewHasanahPromo);
        try {

            listofHasanahPromo = new ArrayList<HasanahPromo>();
            HasanahPromoAdapter adapter = new HasanahPromoAdapter(this, R.layout.list_view_harga_emas, listofHasanahPromo);
            listViewHasanahPromo.setAdapter(adapter);

            listViewHasanahPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.UrlWebView = listofHasanahPromo.get(position).getUrlWebView();
                    Intent intent = new Intent(HasanahPromoListActivity.this, DoaHarianDetailActivity.class);
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
