package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.EventCenterAdapter;
import com.thedev.rajaratacommunity.Models.Event;

import java.util.ArrayList;

public class EventCenterActivity extends AppCompatActivity {
    ElasticImageView back;
    FirebaseUser user;
    FloatingActionButton addEV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_center);
        back=findViewById(R.id.back);
        addEV=findViewById(R.id.addEV);
        user= FirebaseAuth.getInstance().getCurrentUser();

        getEvents();
        addEV.setOnClickListener(view -> {
            startActivity(new Intent(this,AddEventScreen.class));
        });
        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void getEvents() {
        RecyclerView eventRview=findViewById(R.id.view);
        ArrayList<Event> events=new ArrayList<>();
        eventRview.setHasFixedSize(true);
        eventRview.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference("Events");


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!events.isEmpty()){
                    events.clear();
                }
                for (DataSnapshot event:snapshot.getChildren()){
                    String title=event.child("title").getValue(String.class);
                    String image=event.child("image").getValue(String.class);
                    String desc=event.child("desc").getValue(String.class);
                    long id=event.child("id").getValue(Long.class);

                    events.add(new Event(desc,image,title,id));


//                    try {
//                        String d=event.child("joined").child(user.getUid()).getValue(String.class);
//
//                        if (d.equals(user.getPhoneNumber())){
//
//                            events.add(new Event(image,title,id));
//                        }else{
//                            events.add(new Event(image,title,id));
//
//                        }
//
//
//                    }catch (Exception e){
//                        events.add(new EventData(id,title,image,false));
//                    }

                }

                EventCenterAdapter adapter=new EventCenterAdapter(EventCenterActivity.this,events);
                eventRview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}