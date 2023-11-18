package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.PostAdapter;
import com.thedev.rajaratacommunity.Adapters.QAAdapter;
import com.thedev.rajaratacommunity.Models.Post;
import com.thedev.rajaratacommunity.Models.QA;

import java.util.ArrayList;
import java.util.Arrays;

import io.paperdb.Paper;

public class MyCommentsScreen extends AppCompatActivity {
    String email;
    ArrayList<QA> qas = new ArrayList<>();
    ArrayList<QA> myqas = new ArrayList<>();

    QAAdapter adapter;
    ElasticImageView back;
    RecyclerView MyqRview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments_screen);
        Paper.init(this);
        email=Paper.book().read("email");
        MyqRview=findViewById(R.id.MyqRview);
        MyqRview.setHasFixedSize(true);
        MyqRview.setLayoutManager(new LinearLayoutManager(this));
        back=findViewById(R.id.back);

        getQAs();

        back.setOnClickListener(view -> {
            finish();
        });
    }

    //TODO:Q & A Delete option

    private void getQAs() {
        FirebaseDatabase.getInstance().getReference("QA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    //qas.add(ds.getValue(QA.class));

                    if (ds.child("a").getValue(String.class).isEmpty()){
                        qas.add(new QA(
                                ds.getKey(),
                                ds.child("owner").getValue(String.class),
                                ds.child("q").getValue(String.class),
                                null)
                        );
                    }else{
                        ArrayList<String> qs=new ArrayList<>(Arrays.asList(ds.child("a").getValue(String.class).split(",")));

                        qas.add(new QA(
                                ds.getKey(),
                                ds.child("owner").getValue(String.class),
                                ds.child("q").getValue(String.class),
                                qs)
                        );
                    }

                }
                for (QA qa: qas){
                    if (qa.getEmail().equals(email)){
                        myqas.add(qa);
                    }
                }
                adapter=new QAAdapter(MyCommentsScreen.this, myqas);
                MyqRview.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}