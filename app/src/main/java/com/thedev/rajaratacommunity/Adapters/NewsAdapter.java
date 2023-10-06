package com.thedev.rajaratacommunity.Adapters;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thedev.rajaratacommunity.Models.NewsData;
import com.thedev.rajaratacommunity.NewsViewScreen;
import com.thedev.rajaratacommunity.R;
import com.thedev.rajaratacommunity.RegisterScreen;


import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    ArrayList<NewsData> news;

    public NewsAdapter(Context context, ArrayList<NewsData> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.head.setText(news.get(position).getTitle());
        holder.date.setText(news.get(position).getTdate());

        holder.itemView.setOnClickListener(view -> {


            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(news.get(position).getLink()));
            context.startActivity(intent);

            //Didint opened the activity
//            try {
//                Intent intent=new Intent(context, RegisterScreen.class);
//                intent.putExtra("url",news.get(position).getLink());
//                context.startActivity(intent);
//            }catch (Exception e) {
//                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }

        });
        Glide.with(context).load(news.get(position).getImglink()).centerCrop().into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView head,date,body;
        ImageView newsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.header);
            date=itemView.findViewById(R.id.date);

            newsImage=itemView.findViewById(R.id.newsImage);
        }
    }
}
