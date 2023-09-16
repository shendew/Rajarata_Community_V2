package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.skydoves.elasticviews.ElasticImageView;

public class AboutScreen extends AppCompatActivity {
    ElasticImageView back;
    ImageView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        back=findViewById(R.id.back);
        map=findViewById(R.id.mapView);

        back.setOnClickListener(view -> {
            finish();
        });
        map.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/maps/H635DSnBUFArp6kY7"));
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}