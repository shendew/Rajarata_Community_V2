package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.PostAdapter;
import com.thedev.rajaratacommunity.Models.Post;

import java.util.ArrayList;

public class AllPostsScreen extends AppCompatActivity {
    RecyclerView AllPostsRview;
    ElasticImageView back;
    ArrayList<Post> posts=new ArrayList<>();
    PostAdapter POadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts_screen);

        AllPostsRview=findViewById(R.id.AllPostsRview);
        back=findViewById(R.id.back);

        AllPostsRview.setHasFixedSize(true);
        AllPostsRview.setLayoutManager(new GridLayoutManager(this,2));
        back.setOnClickListener(view -> {
            finish();
        });

        getPost();
    }

    private void getPost() {
        FirebaseDatabase.getInstance().getReference("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    posts.add(ds.getValue(Post.class));
                }
                POadapter=new PostAdapter(AllPostsScreen.this, posts);
                AllPostsRview.setAdapter(POadapter);
                

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}