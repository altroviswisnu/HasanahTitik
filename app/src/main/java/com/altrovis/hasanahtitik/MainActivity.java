package com.altrovis.hasanahtitik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.altrovis.hasanahtitik.Business.DatabaseHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
            databaseHelper.close();
        } catch (Exception e){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToPageHasanahTitik(View ev) {
        Intent intent = new Intent(this, HasanahTitikActivity.class);
        startActivity(intent);
    }

    public void goToPageHajiUmroh(View ev) {
        Intent intent = new Intent(this, HajiUmrohActivity.class);
        startActivity(intent);
    }

    public void goToPageMigranHasanah(View ev) {
        Intent intent = new Intent(this, MigranHasanahActivity.class);
        startActivity(intent);
    }
}
