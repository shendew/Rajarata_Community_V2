package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.AAdapter;
import com.thedev.rajaratacommunity.Models.Answer;

import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;

public class CommentView extends AppCompatActivity {
    String q,email,id;
    ArrayList<String> ans;
    ArrayList<Answer> answers;
    TextView q_title,uemail;
    RecyclerView answerRview;
    AAdapter adapter;
    EditText comment_input;
    ImageView send_img;
    ElasticImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        q_title=findViewById(R.id.q_title);
        uemail=findViewById(R.id.uemail);
        answerRview=findViewById(R.id.answerRview);
        comment_input=findViewById(R.id.comment_input);
        send_img=findViewById(R.id.send_img);
        back=findViewById(R.id.back);
        ans=new ArrayList<>();
        answers=new ArrayList<>();
        Paper.init(this);


        answerRview.setHasFixedSize(true);
        answerRview.setLayoutManager(new LinearLayoutManager(this));


        Intent intent=getIntent();
        ans=intent.getStringArrayListExtra("ans");
        q=intent.getStringExtra("q");
        email=intent.getStringExtra("email");
        id=intent.getStringExtra("id");

        try {
            for (String an: ans){
                String[] comment=an.split(":");
                answers.add(new Answer(comment[0],comment[1]));
            }
        }catch (Exception e){

        }



        adapter=new AAdapter(this,answers);

        answerRview.setAdapter(adapter);
        q_title.setText(q);
        uemail.setText(email);

        send_img.setOnClickListener(view -> {
            if (!comment_input.getText().toString().isEmpty()){
                send_img.setFocusable(false);
                send_img.setClickable(false);
                addComment(comment_input.getText().toString());
            }
        });

        back.setOnClickListener(view -> {
            finish();
        });

    }

    private void addComment(String com) {
        FirebaseDatabase.getInstance().getReference("QA").child(id).child("a").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String newdata=snapshot.getValue(String.class)+Paper.book().read("email")+":"+com+",";

                FirebaseDatabase.getInstance().getReference("QA")
                        .child(id)
                        .child("a").setValue(newdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                send_img.setFocusable(true);
                                send_img.setClickable(true);
                                if (task.isSuccessful()){
                                    Toast.makeText(CommentView.this, "Comment Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(CommentView.this, "Comment Added Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommentView.this, "CMTV_106__"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }
}