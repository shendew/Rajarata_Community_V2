package com.thedev.rajaratacommunity.Frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Adapters.PostAdapter;
import com.thedev.rajaratacommunity.Models.Post;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;
import java.util.Arrays;

import io.paperdb.Paper;


public class FavFrag extends Fragment {
    RecyclerView favRview;
    PostAdapter adapter;
    ArrayList<Post> posts,favPosts;
    DatabaseReference postRef;


    public FavFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fav, container, false);

        favRview=v.findViewById(R.id.favRview);
        posts=new ArrayList<>();
        favPosts=new ArrayList<>();
        Paper.init(getContext());
        postRef=FirebaseDatabase.getInstance().getReference("Posts");

        favRview.setHasFixedSize(true);
        favRview.setLayoutManager(new GridLayoutManager(getContext(),2));
        String email=Paper.book().read("email");

        FirebaseDatabase.getInstance().getReference("Users").child(email.replace(".", "-")).child("fav").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> postIDs= new ArrayList<>(Arrays.asList(snapshot.getValue(String.class).split(",")));
                getPosts(postIDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return v;
    }

    private void getPosts(ArrayList<String> postIDs) {

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    posts.add(ds.getValue(Post.class));
                }

                for (Post post : posts){
                    for (String postid: postIDs){
                        if (post.getId().equals(postid)){
                            favPosts.add(post);
                        }
                    }
                }

                adapter=new PostAdapter(getContext(),favPosts);
                favRview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}