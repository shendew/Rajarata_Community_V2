package com.thedev.rajaratacommunity.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Helpers.LoadingDialog;
import com.thedev.rajaratacommunity.Models.SliderData;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

public class SliderCenterAdapter extends RecyclerView.Adapter<SliderCenterAdapter.ViewHolder> {

    Context context;
    ArrayList<SliderData> sliders;
    LoadingDialog loadingDialog;
    Activity activity;

    public SliderCenterAdapter(Context context, ArrayList<SliderData> sliders) {
        this.context = context;
        this.sliders = sliders;
        activity=(Activity) context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.center_slider_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadingDialog=new LoadingDialog(activity);
        SliderData item=sliders.get(position);
        holder.head.setText(item.getTitle());
        Glide.with(context.getApplicationContext()).load(item.getImgUrl()).centerCrop().into(holder.image);

        holder.edit.setOnClickListener(view -> {
            editItem(item.getId());
        });

        holder.del.setOnClickListener(view -> {
            deleteItem(item.getId());
        });
    }

    private void editItem(long id) {
    }

    private void deleteItem(long id) {
        loadingDialog.showDialog();
        FirebaseDatabase.getInstance().getReference("Sliders").child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingDialog.showDialog();
                if (task.isSuccessful()){
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView head;
        ImageView image;
        ElasticImageView edit,del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            head=itemView.findViewById(R.id.head);
            image=itemView.findViewById(R.id.image);
            edit=itemView.findViewById(R.id.edit);
            del=itemView.findViewById(R.id.del);
        }
    }
}
