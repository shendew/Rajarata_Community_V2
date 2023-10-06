package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.skydoves.elasticviews.ElasticButton;

public class NewsViewScreen extends AppCompatActivity {
    String url;
    ProgressDialog dialog;
    ElasticButton open_in,back;
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view_screen);

        open_in=findViewById(R.id.openin);
        web=findViewById(R.id.web);
        back=findViewById(R.id.back);
        web.getSettings().setJavaScriptEnabled(true);

        //dialog=new ProgressDialog(this);

//        url=getIntent().getStringExtra("url").toString();
//        if (url != null){
//            dialog.show();
//            web.loadUrl(url);
//            web.setWebViewClient(new WebViewClient(){
//
//                @Override
//                public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                    super.onPageStarted(view, url, favicon);
//                    dialog.show();
//                }
//
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    dialog.dismiss();
//                }
//            });
//
//        }else {
//            Toast.makeText(this, "Process Error", Toast.LENGTH_SHORT).show();
//        }

        open_in.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        back.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onBackPressed() {

        if (web.canGoBack()){
            web.goBack();
        }else {
            super.onBackPressed();
        }
    }
}