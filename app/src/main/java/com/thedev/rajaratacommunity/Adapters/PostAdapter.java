package com.thedev.rajaratacommunity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Models.Post;
import com.thedev.rajaratacommunity.PostViewScreen;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> posts;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post= posts.get(position);
        holder.title.setText(post.getTitle());
        holder.post_owner.setText(post.getAuther());

        Paper.init(context);
        String email= Paper.book().read("email");
        DatabaseReference df=FirebaseDatabase.getInstance().getReference("Users").child(email.replace(".","-")).child("fav");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String favs=snapshot.getValue(String.class);

                if (favs.contains(post.getId())){
                    Glide.with(context.getApplicationContext()).load(context.getDrawable(R.drawable.filled)).into(holder.post_heart);
                }else{
                    Glide.with(context.getApplicationContext()).load(context.getDrawable(R.drawable.unfilled)).into(holder.post_heart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Glide.with(context.getApplicationContext()).load(post.getUrl()).centerCrop().into(holder.post_back);


        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, PostViewScreen.class);
            intent.putExtra("owner",post.getAuther());
            intent.putExtra("title",post.getTitle());
            intent.putExtra("desc",post.getDesc());
            intent.putExtra("image",post.getUrl());

            context.startActivity(intent);
        });
        holder.post_heart.setOnClickListener(view -> {

            addToFav(post.getId());


        });
    }

    private void removeFav(String id) {
        Paper.init(context);
        String email= Paper.book().read("email");
        DatabaseReference df=FirebaseDatabase.getInstance().getReference("Users").child(email.replace(".","-")).child("fav");


        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newString=snapshot.getValue(String.class).replace(id+",","");
                df.setValue(newString).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){
                            Toast.makeText(context, "Removed from favourite", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Failed to remove from favourite", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addToFav(String id) {
        Paper.init(context);
        String email= Paper.book().read("email");

        DatabaseReference df=FirebaseDatabase.getInstance().getReference("Users").child(email.replace(".","-")).child("fav");


        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class).contains(id)){
                    String newString=snapshot.getValue(String.class).replace(id+",","");
                    df.setValue(newString).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){
                                Toast.makeText(context, "Removed from favourite", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Failed to remove from favourite", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    String newString=snapshot.getValue(String.class)+id+",";
                    df.setValue(newString).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){
                                Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Failed to add favourite", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView post_back,post_heart;
        TextView post_owner,title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_owner=itemView.findViewById(R.id.post_owner);
            post_back=itemView.findViewById(R.id.post_back);
            post_heart=itemView.findViewById(R.id.post_heart);
            title=itemView.findViewById(R.id.title);
        }
    }
}
