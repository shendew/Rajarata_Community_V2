package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.SliderCenterAdapter;

import com.thedev.rajaratacommunity.Models.SliderData;


import java.util.ArrayList;

public class SlidersCenterActivity extends AppCompatActivity {

    ElasticImageView back;
    RecyclerView view;
    FloatingActionButton addSlide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliders_center);
        back=findViewById(R.id.back);
        view=findViewById(R.id.recview);
        addSlide=findViewById(R.id.addSlider);



        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));

        setSlider();

        addSlide.setOnClickListener(view -> {
            startActivity(new Intent(this,AddSliderScreen.class));
        });

        back.setOnClickListener(view -> {
            finish();
        });
    }

    protected void setSlider(){
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference("Sliders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!sliderDataArrayList.isEmpty()){
                    sliderDataArrayList.clear();
                }
                for (DataSnapshot ds:snapshot.getChildren()){
                    sliderDataArrayList.add(new SliderData(ds.child("url").getValue(String.class),ds.child("title").getValue(String.class),ds.child("id").getValue(Long.class)));
                }

                SliderCenterAdapter adapter=new SliderCenterAdapter(SlidersCenterActivity.this,sliderDataArrayList);

                view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
}