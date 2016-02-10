package com.altrovis.hasanahtitik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Business.DoaManasikAdapter;
import com.altrovis.hasanahtitik.Business.DoaManasikHelper;
import com.altrovis.hasanahtitik.Entitties.DoaManasik;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;

import java.util.ArrayList;

public class DoaManasikListActivity extends AppCompatActivity {

    ListView listViewDoaManasik;
    ArrayList<DoaManasik> listofDoaManasik;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_manasik_list);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Doa Manasik");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);

            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.logoo);
            android.support.v7.app.ActionBar.LayoutParams layoutParams = new android.support.v7.app.ActionBar.LayoutParams(
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);
            layoutParams.rightMargin = 10;
            layoutParams.width = 120;
            layoutParams.height = 0;
            imageView.setLayoutParams(layoutParams);
            actionBar.setCustomView(imageView);
        }

        context = DoaManasikListActivity.this;


        listViewDoaManasik = (ListView) findViewById(R.id.ListViewDoaManasik);
        new DoaManasikAsyncTask().execute();
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

    private class DoaManasikAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        private DoaManasikAsyncTask(){
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
                listofDoaManasik = DoaManasikHelper.getListOfDoaManasik(GlobalVariable.UrlDoaManasik);
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

            DoaManasikAdapter adapter = new DoaManasikAdapter(context, R.layout.list_view_doa_manasik, listofDoaManasik);
            listViewDoaManasik.setAdapter(adapter);

            listViewDoaManasik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.UrlWebView = listofDoaManasik.get(position).getUrlWebView();
                    GlobalVariable.TitleActionBar = listofDoaManasik.get(position).getNama();
                    Intent intent = new Intent(DoaManasikListActivity.this, DoaManasikDetailActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}
