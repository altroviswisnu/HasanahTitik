package com.altrovis.hasanahtitik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.altrovis.hasanahtitik.Business.DatabaseHelper;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                    DatabaseHelper databaseHelper = new DatabaseHelper(SplashScreenActivity.this);
                    databaseHelper.createDataBase();
                    databaseHelper.openDataBase();
                    databaseHelper.close();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }
}
