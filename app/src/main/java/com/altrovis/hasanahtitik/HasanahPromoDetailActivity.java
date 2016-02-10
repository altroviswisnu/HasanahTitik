package com.altrovis.hasanahtitik;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.HasanahPromo;

public class HasanahPromoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasanah_promo_detail);

        HasanahPromo hasanahPromo = GlobalVariable.SelectedPromo;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(hasanahPromo.getNama());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);

            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.logo);
            android.support.v7.app.ActionBar.LayoutParams layoutParams = new android.support.v7.app.ActionBar.LayoutParams(
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);
            layoutParams.rightMargin = 10;
            layoutParams.width = 120;
            layoutParams.height = 80;
            imageView.setLayoutParams(layoutParams);
            actionBar.setCustomView(imageView);
        }


        TextView textViewJudulPromo = (TextView) findViewById(R.id.TextViewJudulPromo);
        TextView textViewTanggalPromoDibuat = (TextView) findViewById(R.id.TextViewTanggalPromoDibuat);
        TextView textViewTanggalPromoMulai = (TextView) findViewById(R.id.TextViewTanggaPromoMulai);
        TextView textViewTanggalPromoSelesai = (TextView) findViewById(R.id.TextViewTanggalPromoSelesai);
        TextView textViewContentsPromo = (TextView) findViewById(R.id.TextViewContentsPromo);

        textViewJudulPromo.setText(hasanahPromo.getNama());
        textViewTanggalPromoDibuat.setText(hasanahPromo.getDateCreated());
        textViewTanggalPromoMulai.setText(hasanahPromo.getDateStart());
        textViewTanggalPromoSelesai.setText(hasanahPromo.getDateEnd());
        textViewContentsPromo.setText(hasanahPromo.getContents());
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
