package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Entitties.ItemMenu;

import java.util.ArrayList;

public class HasanahTitikActivity extends Activity {

    ListView listViewHasanahTitik;
    ArrayList <ItemMenu> listofItemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasanah_titik);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Hasanah Titik");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);


        listViewHasanahTitik = (ListView) findViewById(R.id.ListViewHasanahTitik);
        String[] listMenu = getResources().getStringArray(R.array.MenuHasanahTitik);
        listofItemMenu = new ArrayList<ItemMenu>();

        /*
        for(int i = 0; i < listMenu.length; i++) {
            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setID(i);
            itemMenu.setNama(listMenu[i]);
            listofItemMenu.add(itemMenu);
        }

        GridViewAdapter adapter = new GridViewAdapter(this, R.layout.list_view_item_menu, listofItemMenu);
        listViewHasanahTitik.setAdapter(adapter);

        listViewHasanahTitik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listofItemMenu.get(position).getID() == 0) {
                    Intent intent = new Intent(HasanahTitikActivity.this, HasanahPromoListActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 1) {
                    Intent intent = new Intent(HasanahTitikActivity.this, HasanahKalkulatorActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 2) {
                    Intent intent = new Intent(HasanahTitikActivity.this, HargaEmasActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 3) {
                    // Hasanah News
                } else if (listofItemMenu.get(position).getID() == 4) {
                    // Hasanah Tips dan Produk
                } else if (listofItemMenu.get(position).getID() == 5) {
                    Intent intent = new Intent(HasanahTitikActivity.this, LokasiATMMapActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 6) {
                    // Cabang
                } else if (listofItemMenu.get(position).getID() == 7) {
                    // Quiz
                } else if (listofItemMenu.get(position).getID() == 8) {
                    Intent intent = new Intent(HasanahTitikActivity.this, JuzAmmaListActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 9) {
                    Intent intent = new Intent(HasanahTitikActivity.this, JadwalSholatActivity.class);
                    startActivity(intent);
                }
            }
        });
        */
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
