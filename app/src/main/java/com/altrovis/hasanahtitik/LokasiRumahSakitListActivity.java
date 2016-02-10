package com.altrovis.hasanahtitik;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.altrovis.hasanahtitik.Business.GlobalFunction;
import com.altrovis.hasanahtitik.Business.LokasiRumahSakitAdapter;
import com.altrovis.hasanahtitik.Business.LokasiRumahSakitHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;

import java.util.ArrayList;

public class LokasiRumahSakitListActivity extends AppCompatActivity {

    ListView listViewLokasiRumahSakit;
    ArrayList<LokasiRumahSakit> listofRumahSakit;
    Context context;
    LokasiRumahSakitAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_rumah_sakit_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("List Rumah Sakit");
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

        context = LokasiRumahSakitListActivity.this;

        listViewLokasiRumahSakit = (ListView) findViewById(R.id.ListViewLokasiRumahSakit);
        try {
            listofRumahSakit = LokasiRumahSakitHelper.getListOfRumahSakitFromDataBase(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new LokasiRumahSakitAdapter(context, R.layout.list_view_rumah_sakit, listofRumahSakit);
        listViewLokasiRumahSakit.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayoutRumahSakit);
        refreshLayout.setOnRefreshListener(new LokasiRumahSakitPullRefresh());

        listViewLokasiRumahSakit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    private class LokasiRumahSakitAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        private LokasiRumahSakitAsyncTask(){
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
                listofRumahSakit = new ArrayList<LokasiRumahSakit>();
                listofRumahSakit = LokasiRumahSakitHelper.
                        getListOfRumahSakitFromWebService(context, GlobalVariable.UrlLokasiRumahSakit);
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

            adapter.clear();
            adapter.addAll(listofRumahSakit);
            adapter.notifyDataSetChanged();
        }
    }

    private class LokasiRumahSakitPullRefresh implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            if(GlobalFunction.isOnline(context)){
                try {
                    LokasiRumahSakitHelper.deleteAllRumahSakit(context);
                    new LokasiRumahSakitAsyncTask().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
