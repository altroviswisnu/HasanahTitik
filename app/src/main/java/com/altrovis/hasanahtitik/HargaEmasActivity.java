package com.altrovis.hasanahtitik;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Business.GlobalFunction;
import com.altrovis.hasanahtitik.Business.HargaEmasAdapter;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;
import com.altrovis.hasanahtitik.Entitties.HargaEmas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HargaEmasActivity extends AppCompatActivity {

    WebView webViewHargaEmas;
    private final int REFRESH_TIME_OUT = 1000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_emas);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Harga Emas");
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

        context = HargaEmasActivity.this;

        webViewHargaEmas = (WebView) findViewById(R.id.WebViewHargaEmas);

        webViewHargaEmas.setWebChromeClient(new MyWebViewClient());
        webViewHargaEmas.getSettings().setJavaScriptEnabled(true);
        webViewHargaEmas.getSettings().setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        webViewHargaEmas.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HargaEmasActivity.this);
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

                webViewHargaEmas.loadUrl("about:blank");
            }
        });

        webViewHargaEmas.setBackgroundColor(0x00000000);

        if(GlobalFunction.isOnline(context)) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    webViewHargaEmas.loadUrl(GlobalVariable.UrlHargaEmas);

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            webViewHargaEmas.reload();
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

    private class MyWebViewClient extends WebChromeClient {
        ProgressDialog progress;
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(progress == null)
            {
                progress = ProgressDialog.show(HargaEmasActivity.this, "",
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
