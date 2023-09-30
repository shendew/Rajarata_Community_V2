package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Adapters.QAAdapter;
import com.thedev.rajaratacommunity.Helpers.AddCommentDialog;
import com.thedev.rajaratacommunity.Models.QA;

import java.util.ArrayList;
import java.util.Arrays;

public class QAScreen extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference QARef;
    ArrayList<QA> qas;
    RecyclerView qaRview;
    FloatingActionButton addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qascreen);
        db=FirebaseDatabase.getInstance();
        qaRview=findViewById(R.id.qaRview);
        addComment=findViewById(R.id.addComment);
        QARef=db.getReference("QA");

        qaRview.setHasFixedSize(true);
        qaRview.setLayoutManager(new LinearLayoutManager(this));

        getQuestions();

        addComment.setOnClickListener(view -> {
            AddCommentDialog dialog =new AddCommentDialog(QAScreen.this);
            dialog.showDialog();
        });
    }

    private void getQuestions() {

        QARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                qas=new ArrayList<>();

                for (DataSnapshot ds:snapshot.getChildren())
                {

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
                QAAdapter adapter=new QAAdapter(QAScreen.this,qas);
                qaRview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}