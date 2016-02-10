package com.altrovis.hasanahtitik;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.altrovis.hasanahtitik.Business.GlobalFunction;
import com.altrovis.hasanahtitik.Entitties.GlobalVariable;

import java.io.IOException;

public class JuzAmmaDetailActivity extends AppCompatActivity {

    WebView webViewJuzAmma;
    private final int REFRESH_TIME_OUT = 1000;
    Context context;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juz_amma_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(GlobalVariable.TitleActionBar);
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

        context = JuzAmmaDetailActivity.this;

        webViewJuzAmma = (WebView) findViewById(R.id.WebViewJuzAmma);

        webViewJuzAmma.setWebChromeClient(new MyWebViewClient());
        webViewJuzAmma.getSettings().setJavaScriptEnabled(true);
        webViewJuzAmma.getSettings().setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        webViewJuzAmma.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JuzAmmaDetailActivity.this);
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

                webViewJuzAmma.loadUrl("about:blank");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(".mp3")) {
                    webViewJuzAmma.loadUrl(url);
                    Log.e("Test","Masuk");
                } else {
                    playAudio(url);
                }
                return true;
            }
        });

        webViewJuzAmma.setBackgroundColor(0x00000000);

        if(GlobalFunction.isOnline(context)) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    webViewJuzAmma.loadUrl(GlobalVariable.UrlWebView);

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            webViewJuzAmma.reload();
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

        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public void playAudio(String url)
    {
        try {
            if(mediaPlayer != null && mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                progress = ProgressDialog.show(JuzAmmaDetailActivity.this, "",
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
