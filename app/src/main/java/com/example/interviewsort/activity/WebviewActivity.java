package com.example.interviewsort.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.example.interviewsort.AppConstant;
import com.example.interviewsort.R;
import com.google.android.gms.ads.*;


/**
 * Created by Kapil Vij on 18/01/17.
 */

public class WebviewActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progress_bar;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String title = getIntent().getStringExtra(AppConstant.EXTRAS.TITLE);
        String webUrl = getIntent().getStringExtra(AppConstant.EXTRAS.WEB_URL);

        ((TextView) findViewById(R.id.txvHeader)).setText(title);


        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        webView = (WebView) findViewById(R.id.webView);
        addImplimentation();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress_bar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress_bar.setVisibility(ProgressBar.GONE);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.loadUrl(webUrl);
    }

    private void addImplimentation() {
        final NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView1);
        adView.setVisibility(View.GONE);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(String.valueOf(R.string.appid))
                .build();
        adView.loadAd(request);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        mInterstitialAd = new InterstitialAd(this);

// set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));


// Load ads into Interstitial Ads

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
        } else {
            if (getSupportActionBar() != null)
                getSupportActionBar().show();

        }
    }
}
