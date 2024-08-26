package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skydoves.elasticviews.ElasticImageView;

public class PostViewScreen extends AppCompatActivity {
    ElasticImageView back;
    ImageView postImage;
    TextView postOwner,postDesc,postTitle;
    String TpostTitle,TpostImage,TpostDesc,TpostOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_screen);

        back=findViewById(R.id.back);
        postImage=findViewById(R.id.postImage);
        postOwner=findViewById(R.id.postOwner);
        postDesc=findViewById(R.id.postDesc);
        postTitle=findViewById(R.id.postTitle);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            TpostTitle= extras.getString("title");
            TpostImage= extras.getString("image");
            TpostDesc= extras.getString("desc");
            TpostOwner= extras.getString("owner");

        } else {
            TpostTitle= (String) savedInstanceState.getSerializable("title");
            TpostImage= (String) savedInstanceState.getSerializable("image");
            TpostDesc= (String) savedInstanceState.getSerializable("desc");
            TpostOwner= (String) savedInstanceState.getSerializable("owner");
        }

        postTitle.setText(TpostTitle);
        postDesc.setText(TpostDesc);
        postOwner.setText(TpostOwner);
        Glide.with(getApplicationContext()).load(TpostImage).into(postImage);





        back.setOnClickListener(view -> {
            finish();
        });
    }
}