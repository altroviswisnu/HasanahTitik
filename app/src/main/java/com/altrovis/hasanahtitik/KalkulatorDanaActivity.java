package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class KalkulatorDanaActivity extends Activity {

    WebView webViewKalkulatorDana;
    private final int REFRESH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator_dana);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Kalkulator Dana");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        webViewKalkulatorDana = (WebView) findViewById(R.id.WebViewKalkulatorDana);

        webViewKalkulatorDana.setWebChromeClient(new MyWebViewClient());
        webViewKalkulatorDana.getSettings().setJavaScriptEnabled(true);
        webViewKalkulatorDana.getSettings().setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        webViewKalkulatorDana.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KalkulatorDanaActivity.this);
                builder.setMessage(description)
                        .setTitle("Perhatian")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                webViewKalkulatorDana.loadUrl("about:blank");
            }
        });

        webViewKalkulatorDana.setBackgroundColor(0x00000000);

        if(isConnected(this)) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    webViewKalkulatorDana.loadUrl("http://www.bnisyariah.co.id/calculator?simulasi=tabungan");

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            webViewKalkulatorDana.reload();
                        }
                    }, REFRESH_TIME_OUT);
                }
            }, REFRESH_TIME_OUT);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Perangkat Anda tidak terhubung dengan internet.")
                    .setTitle("Perhatian")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

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
    public boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private class MyWebViewClient extends WebChromeClient {
        ProgressDialog progress;
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(progress == null)
            {
                progress = ProgressDialog.show(KalkulatorDanaActivity.this, "",
                        "Sedang mengambil data...", true);
            }
            if (newProgress == 100) {
                if(progress != null)
                {
                    progress.dismiss();
                    progress = null;
                }
            }

            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
        }


    }
}
