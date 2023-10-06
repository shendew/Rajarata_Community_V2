package com.thedev.rajaratacommunity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.shaun.htmlviewsunny.htmlview;
import com.thedev.rajaratacommunity.Models.NotificationData;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    ArrayList<NotificationData> notifications;

    public NotificationAdapter(Context context, ArrayList<NotificationData> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(notifications.get(position).getIMAGE()).centerCrop().into(holder.image);
        holder.title.setText(notifications.get(position).getTITLE());

        holder.desc.setText(notifications.get(position).getDESC());


        holder.desc.setOnViewTouchedListener(new htmlview.OnViewTouchedListener() {
            @Override
            public void imageClicked(@Nullable String s) {

            }
        });


        holder.itemView.setOnClickListener(view -> {
//            Intent intent=new Intent(context,NotificationViewActivity.class);
//
//            intent.putExtra("id",notifications.get(position).getID());
//            intent.putExtra("title",notifications.get(position).getTITLE());
//            intent.putExtra("desc",notifications.get(position).getDESC());
//            intent.putExtra("image",notifications.get(position).getIMAGE());
//            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        com.shaun.htmlviewsunny.htmlview desc;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            image=itemView.findViewById(R.id.image);

        }
    }
}