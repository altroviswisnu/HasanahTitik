package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.HasanahPromo;

public class HasanahPromoDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasanah_promo_detail);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Hasanah Promo");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        TextView textViewJudulPromo = (TextView) findViewById(R.id.TextViewJudulPromo);
        TextView textViewTanggalPromoDibuat = (TextView) findViewById(R.id.TextViewTanggalPromoDibuat);
        TextView textViewTanggalPromoMulai = (TextView) findViewById(R.id.TextViewTanggaPromoMulai);
        TextView textViewTanggalPromoSelesai = (TextView) findViewById(R.id.TextViewTanggalPromoSelesai);
        TextView textViewContentsPromo = (TextView) findViewById(R.id.TextViewContentsPromo);

        HasanahPromo hasanahPromo = GlobalVariable.SelectedPromo;

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
