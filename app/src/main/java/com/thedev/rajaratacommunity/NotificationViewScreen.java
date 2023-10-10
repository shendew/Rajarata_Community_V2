package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skydoves.elasticviews.ElasticImageView;

public class NotificationViewScreen extends AppCompatActivity {
    ElasticImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view_screen);
        back=findViewById(R.id.back);


        back.setOnClickListener(view -> {
            finish();
        });
    }
}