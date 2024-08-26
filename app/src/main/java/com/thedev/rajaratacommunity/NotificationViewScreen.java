package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skydoves.elasticviews.ElasticImageView;

public class NotificationViewScreen extends AppCompatActivity {
    ElasticImageView back;
    ImageView notfiImage;
    TextView notfiDesc,notfiTitle;
    String TnotfiTitle,TnotfiImage,TnotfiDesc,TnotfiOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view_screen);

        back=findViewById(R.id.back);
        notfiImage=findViewById(R.id.notfiImage);

        notfiDesc=findViewById(R.id.notfiDesc);
        notfiTitle=findViewById(R.id.notfiTitle);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            TnotfiTitle= extras.getString("title");
            TnotfiImage= extras.getString("image");
            TnotfiDesc= extras.getString("desc");


        } else {
            TnotfiTitle= (String) savedInstanceState.getSerializable("title");
            TnotfiImage= (String) savedInstanceState.getSerializable("image");
            TnotfiDesc= (String) savedInstanceState.getSerializable("desc");

        }

        notfiTitle.setText(TnotfiTitle);
        notfiDesc.setText(TnotfiDesc);

        Glide.with(getApplicationContext()).load(TnotfiImage).into(notfiImage);

        back.setOnClickListener(view -> {
            finish();
        });
    }
}