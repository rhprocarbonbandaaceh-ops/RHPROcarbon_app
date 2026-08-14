package com.rhprocarbon.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

```
private WebView webView;
private LinearLayout splashLayout;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow().setStatusBarColor(Color.BLACK);
    getWindow().setNavigationBarColor(Color.BLACK);

    splashLayout = new LinearLayout(this);
    splashLayout.setOrientation(LinearLayout.VERTICAL);
    splashLayout.setGravity(Gravity.CENTER);

    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.rgb(3, 7, 15));
    splashLayout.setBackground(background);

    ImageView logo = new ImageView(this);
    logo.setImageResource(R.drawable.logo);
    logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

    LinearLayout.LayoutParams logoParams =
            new LinearLayout.LayoutParams(dp(280), dp(280));

    splashLayout.addView(logo, logoParams);

    TextView title = new TextView(this);
    title.setText("RHPROCARBON");
    title.setTextColor(Color.WHITE);
    title.setTextSize(25);
    title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    title.setGravity(Gravity.CENTER);

    splashLayout.addView(title);

    TextView subtitle = new TextView(this);
    subtitle.setText("BANDA ACEH");
    subtitle.setTextColor(Color.rgb(0, 140, 255));
    subtitle.setTextSize(14);
    subtitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    subtitle.setGravity(Gravity.CENTER);

    LinearLayout.LayoutParams subParams =
            new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

    subParams.topMargin = dp(5);
    splashLayout.addView(subtitle, subParams);

    TextView loading = new TextView(this);
    loading.setText("MEMUAT...");
    loading.setTextColor(Color.LTGRAY);
    loading.setTextSize(12);
    loading.setGravity(Gravity.CENTER);

    LinearLayout.LayoutParams loadingParams =
            new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

    loadingParams.topMargin = dp(30);
    splashLayout.addView(loading, loadingParams);

    setContentView(splashLayout);

    ScaleAnimation zoom = new ScaleAnimation(
            0.75f, 1.0f,
            0.75f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    );

    zoom.setDuration(1000);
    zoom.setFillAfter(true);
    logo.startAnimation(zoom);

    AlphaAnimation fade = new AlphaAnimation(0.0f, 1.0f);
    fade.setDuration(1000);
    fade.setFillAfter(true);
    title.startAnimation(fade);

    AlphaAnimation subtitleFade = new AlphaAnimation(0.0f, 1.0f);
    subtitleFade.setStartOffset(500);
    subtitleFade.setDuration(800);
    subtitleFade.setFillAfter(true);
    subtitle.startAnimation(subtitleFade);

    AlphaAnimation loadingFade = new AlphaAnimation(0.0f, 1.0f);
    loadingFade.setStartOffset(900);
    loadingFade.setDuration(800);
    loadingFade.setFillAfter(true);
    loading.startAnimation(loadingFade);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            openWebsite();
        }
    }, 2500);
}

private void openWebsite() {

    webView = new WebView(this);

    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);

    webView.setWebViewClient(new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view,
                String url) {

            if (url.startsWith("whatsapp://")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }

                return true;
            }

            if (url.startsWith("http://")
                    || url.startsWith("https://")) {

                view.loadUrl(url);
                return true;
            }

            return true;
        }
    });

    webView.loadUrl(
            "https://rhprocarbonbandaaceh-ops.github.io/rhprocarbon_bna.com/"
    );

    setContentView(webView);
}

@Override
public void onBackPressed() {

    if (webView != null && webView.canGoBack()) {
        webView.goBack();
    } else {
        super.onBackPressed();
    }
}

private int dp(int value) {
    return (int) (
            value * getResources()
                    .getDisplayMetrics()
                    .density
    );
}


}
