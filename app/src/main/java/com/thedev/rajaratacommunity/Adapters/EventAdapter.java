package com.thedev.rajaratacommunity.Adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thedev.rajaratacommunity.Models.Event;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Viewholder> {

    Context context;
    ArrayList<Event> events;

    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.event_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.Viewholder holder, int position) {
        Event event=events.get(position);
        holder.event_title.setText(event.getTitle());
        Glide.with(context).load(event.getImage()).centerCrop().into(holder.event_img);

        holder.event_more_button.setOnClickListener(view -> {
            Toast.makeText(context, ""+event.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView event_title;
        ImageView event_img;
        AppCompatButton event_more_button;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            event_title=itemView.findViewById(R.id.event_title);
            event_img=itemView.findViewById(R.id.event_img);
            event_more_button=itemView.findViewById(R.id.event_more_button);
        }
    }
}
