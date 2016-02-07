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

import com.altrovis.hasanahtitik.Business.ItemMenuAdapter;
import com.altrovis.hasanahtitik.Entitties.ItemMenu;

import java.util.ArrayList;

public class HasanahKalkulatorActivity extends Activity {

    ListView listViewHasanahKalkulator;
    ArrayList<ItemMenu> listofItemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasanah_kalkulator);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Hasanah Kalkulator");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        listViewHasanahKalkulator = (ListView) findViewById(R.id.ListViewHasanahKalkulator);
        String[] listMenu = getResources().getStringArray(R.array.MenuHasanahKalkulator);
        listofItemMenu = new ArrayList<ItemMenu>();

        for(int i = 0; i < listMenu.length; i++) {
            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setID(i);
            itemMenu.setNama(listMenu[i]);
            listofItemMenu.add(itemMenu);
        }

        ItemMenuAdapter adapter = new ItemMenuAdapter(this, R.layout.list_view_item_menu, listofItemMenu);
        listViewHasanahKalkulator.setAdapter(adapter);

        listViewHasanahKalkulator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listofItemMenu.get(position).getID() == 0) {
                    Intent intent = new Intent(HasanahKalkulatorActivity.this, KalkulatorDanaActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 1) {
                    Intent intent = new Intent(HasanahKalkulatorActivity.this, KalkulatorPembiayanActivity.class);
                    startActivity(intent);
                } 
            }
        });
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
