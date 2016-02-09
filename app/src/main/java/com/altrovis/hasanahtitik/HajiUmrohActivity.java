package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Entitties.ItemMenu;

import java.util.ArrayList;

public class HajiUmrohActivity extends Activity {

    ListView listViewHajiUmroh;
    ArrayList <ItemMenu> listofItemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haji_umroh);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Haji Umroh");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        /*
        listViewHajiUmroh = (ListView) findViewById(R.id.ListViewHajiUmroh);
        String[] listMenu = getResources().getStringArray(R.array.MenuHajiUmroh);
        listofItemMenu = new ArrayList<ItemMenu>();

        for(int i = 0; i < listMenu.length; i++) {
            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setID(i);
            itemMenu.setNama(listMenu[i]);
            listofItemMenu.add(itemMenu);
        }

        GridViewAdapter adapter = new GridViewAdapter(this, R.layout.list_view_item_menu, listofItemMenu);
        listViewHajiUmroh.setAdapter(adapter);

        listViewHajiUmroh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listofItemMenu.get(position).getID() == 0) {
                    Intent intent = new Intent(HajiUmrohActivity.this, DoaManasikListActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 1) {
                    Intent intent = new Intent(HajiUmrohActivity.this, LokasiRumahSakitMapActivity.class);
                    startActivity(intent);
                } else if (listofItemMenu.get(position).getID() == 2) {
                    // Lokasi Rumah Makan
                } else if(listofItemMenu.get(position).getID() == 3){
                    // Daerah kerja
                } else if(listofItemMenu.get(position).getID() == 4){
                    Intent intent = new Intent(HajiUmrohActivity.this, LokasiATMMapActivity.class);
                    startActivity(intent);
                } else if(listofItemMenu.get(position).getID() == 5){
                    Intent intent = new Intent(HajiUmrohActivity.this, ArahKiblatActivity.class);
                    startActivity(intent);
                } else if(listofItemMenu.get(position).getID() == 6){
                    Intent intent = new Intent(HajiUmrohActivity.this, JadwalSholatActivity.class);
                    startActivity(intent);
                } else if(listofItemMenu.get(position).getID() == 7){
                    Intent intent = new Intent(HajiUmrohActivity.this, JadwalBusActivity.class);
                    startActivity(intent);
                } else if(listofItemMenu.get(position).getID() == 8){
                    // Fitur Kartu Haji dan Umroh
                } else if(listofItemMenu.get(position).getID() == 9){
                    // Cara Penggunaan dan Jika Kartu Hilang
                } else if(listofItemMenu.get(position).getID() == 10){
                    // Masjidil Haram dan Nabawi
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
