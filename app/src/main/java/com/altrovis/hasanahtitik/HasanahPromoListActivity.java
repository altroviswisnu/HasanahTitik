package com.altrovis.hasanahtitik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Business.HasanahPromoAdapter;
import com.altrovis.hasanahtitik.Business.HasanahPromoHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.HasanahPromo;

import java.util.ArrayList;

public class HasanahPromoListActivity extends AppCompatActivity {

    ListView listViewHasanahPromo;
    ArrayList<HasanahPromo> listofHasanahPromo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasanah_promo_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Hasanah Promo");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);

            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.Logo);
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);
            layoutParams.rightMargin = 10;
            layoutParams.width = 120;
            layoutParams.height = 80;
            imageView.setLayoutParams(layoutParams);
            actionBar.setCustomView(imageView);
        }

        context = HasanahPromoListActivity.this;


        listViewHasanahPromo = (ListView) findViewById(R.id.ListViewHasanahPromo);
        new HasanahPromoAsyncTask().execute();
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

    private class HasanahPromoAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        private HasanahPromoAsyncTask(){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Silahkan Tunggu");
            progressDialog.show();
        }

        protected void onPreExecute() {
            super.onPreExecute();

            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                listofHasanahPromo = HasanahPromoHelper.getListOfHasanahPromo(GlobalVariable.UrlPromo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            HasanahPromoAdapter adapter = new HasanahPromoAdapter(context, R.layout.list_view_hasanah_promo, listofHasanahPromo);
            listViewHasanahPromo.setAdapter(adapter);

            listViewHasanahPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.SelectedPromo = listofHasanahPromo.get(position);
                    Intent intent = new Intent(HasanahPromoListActivity.this, HasanahPromoDetailActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

}
