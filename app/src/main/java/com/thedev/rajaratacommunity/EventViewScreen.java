package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skydoves.elasticviews.ElasticImageView;

public class EventViewScreen extends AppCompatActivity {
    ElasticImageView back;
    ImageView eveImage;
    TextView eveDesc,eveTitle;
    String TeveTitle,TeveImage,TeveDesc,TeveOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_screen);
        back=findViewById(R.id.back);
        eveImage=findViewById(R.id.eveImage);

        eveDesc=findViewById(R.id.eveDesc);
        eveTitle=findViewById(R.id.eveTitle);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            TeveTitle= extras.getString("title");
            TeveImage= extras.getString("image");
            TeveDesc= extras.getString("desc");


        } else {
            TeveTitle= (String) savedInstanceState.getSerializable("title");
            TeveImage= (String) savedInstanceState.getSerializable("image");
            TeveDesc= (String) savedInstanceState.getSerializable("desc");

        }

        eveTitle.setText(TeveTitle);
        eveDesc.setText(TeveDesc);

        Glide.with(getApplicationContext()).load(TeveImage).into(eveImage);

        back.setOnClickListener(view -> {
            finish();
        });
    }
}