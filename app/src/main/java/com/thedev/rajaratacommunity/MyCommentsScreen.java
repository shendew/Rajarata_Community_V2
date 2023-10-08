package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Adapters.PostAdapter;
import com.thedev.rajaratacommunity.Adapters.QAAdapter;
import com.thedev.rajaratacommunity.Models.Post;
import com.thedev.rajaratacommunity.Models.QA;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MyCommentsScreen extends AppCompatActivity {
    String email;
    ArrayList<QA> qas = new ArrayList<>();
    ArrayList<QA> myqas = new ArrayList<>();

    QAAdapter adapter;
    RecyclerView MyqRview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments_screen);
        Paper.init(this);
        email=Paper.book().read("email");
        MyqRview=findViewById(R.id.MyqRview);
        MyqRview.setHasFixedSize(true);
        MyqRview.setLayoutManager(new GridLayoutManager(this,2));

        getQAs();
    }

    private void getQAs() {
        FirebaseDatabase.getInstance().getReference("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    qas.add(ds.getValue(QA.class));
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