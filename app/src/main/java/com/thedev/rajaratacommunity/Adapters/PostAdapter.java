package com.thedev.rajaratacommunity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thedev.rajaratacommunity.Models.Post;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

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

        Glide.with(context).load(post.getUrl()).centerCrop().into(holder.post_back);
        holder.post_heart.setOnClickListener(view -> {

        });
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView post_back,post_heart;
        TextView post_owner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_owner=itemView.findViewById(R.id.post_owner);
            post_back=itemView.findViewById(R.id.post_back);
            post_heart=itemView.findViewById(R.id.post_heart);
        }
    }
}
