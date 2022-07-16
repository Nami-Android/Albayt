package com.apps.albayt.uis.activity_about_app;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apps.albayt.R;
import com.apps.albayt.databinding.ActivityAboutAppBinding;
import com.apps.albayt.uis.activity_base.BaseActivity;

public class AboutAppActivity extends BaseActivity {
    private ActivityAboutAppBinding binding;
    private String type;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_app);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        url = intent.getStringExtra("url");

    }

    private void initView() {
        String title = "";
        if (type.equals("1")) {
            title = getString(R.string.terms_and_conditions);
        } else {
            title = getString(R.string.privacy_policy);
        }
        setUpToolbar(binding.toolbar, title, R.color.white, R.color.black, R.drawable.small_rounded_grey4, true);

        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setAllowContentAccess(true);
        binding.webView.loadUrl(url);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                binding.progBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progBar.setVisibility(View.GONE);

            }
        });
    }
}