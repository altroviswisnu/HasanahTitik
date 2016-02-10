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
import com.altrovis.hasanahtitik.Business.LokasiATMAdapter;
import com.altrovis.hasanahtitik.Business.LokasiATMHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.LokasiATM;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LokasiATMListActivity extends AppCompatActivity {

    ListView listViewLokasiATM;
    ArrayList<LokasiATM> listofATM;
    Context context;
    AppCompatActivity activity;
    LokasiATMAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_atm_list);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("List ATM");
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

        context = LokasiATMListActivity.this;
        activity = LokasiATMListActivity.this;

        listViewLokasiATM = (ListView) findViewById(R.id.ListViewLokasiATM);
        try {
            listofATM = LokasiATMHelper.getListOFATMFromDatabase(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new LokasiATMAdapter(context, R.layout.list_view_lokasi_atm, listofATM);
        listViewLokasiATM.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayoutATM);
        refreshLayout.setOnRefreshListener(new LokasiATMPullRefresh());

        listViewLokasiATM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LokasiATM atm = listofATM.get(position);
                GlobalVariable.SelectedCoordinate = new LatLng(atm.getLatitude(), atm.getLongitude());
                activity.finish();
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

    private class LokasiATMAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        private LokasiATMAsyncTask(){
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
                listofATM = new ArrayList<LokasiATM>();
                listofATM = LokasiATMHelper.getListOfATMFromWebService(context, GlobalVariable.UrlLokasiATM);
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
            adapter.addAll(listofATM);
            adapter.notifyDataSetChanged();
        }
    }

    private class LokasiATMPullRefresh implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            if(GlobalFunction.isOnline(context)){
                try {
                    LokasiATMHelper.deleteAllATM(context);
                    new LokasiATMAsyncTask().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            refreshLayout.setRefreshing(false);
        }
    }
}
