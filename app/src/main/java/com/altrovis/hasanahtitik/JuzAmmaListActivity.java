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

import com.altrovis.hasanahtitik.Business.JuzAmmaAdapter;
import com.altrovis.hasanahtitik.Business.JuzAmmaHelper;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.JuzAmma;

import java.util.ArrayList;

public class JuzAmmaListActivity extends AppCompatActivity {

    ListView listViewJuzAmma;
    ArrayList<JuzAmma> listofJuzAmma;
    Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juz_amma_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Juz Amma");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);

            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.logo);
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);
            layoutParams.rightMargin = 10;
            layoutParams.width = 120;
            layoutParams.height = 80;
            imageView.setLayoutParams(layoutParams);
            actionBar.setCustomView(imageView);
        }


        context = JuzAmmaListActivity.this;


        listViewJuzAmma = (ListView) findViewById(R.id.ListViewJuzAmma);
        new JuzAmmaAsyncTask().execute();

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

    private class JuzAmmaAsyncTask extends AsyncTask<Void, Void, Void>{

        ProgressDialog progressDialog;

        private JuzAmmaAsyncTask(){
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
                listofJuzAmma = JuzAmmaHelper.getListOfJuzAmma(GlobalVariable.UrlJuzAmma);
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

            JuzAmmaAdapter adapter = new JuzAmmaAdapter(context, R.layout.list_view_juz_amma, listofJuzAmma);
            listViewJuzAmma.setAdapter(adapter);

            listViewJuzAmma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GlobalVariable.UrlWebView = listofJuzAmma.get(position).getUrlWebView();
                    GlobalVariable.TitleActionBar = listofJuzAmma.get(position).getNama();
                    Intent intent = new Intent(JuzAmmaListActivity.this, JuzAmmaDetailActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}
