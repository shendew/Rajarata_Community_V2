package com.thedev.rajaratacommunity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.thedev.rajaratacommunity.Models.Answer;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

public class AAdapter extends RecyclerView.Adapter<AAdapter.ViewHolder> {

    Context context;
    ArrayList<Answer> answers;

    public AAdapter(Context context, ArrayList<Answer> answers) {
        this.context = context;
        this.answers = answers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.a_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Answer answer=answers.get(position);

        holder.uemail.setText(answer.getEmail());
        holder.ucomment.setText(answer.getComment());

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView uemail,ucomment;
        ShapeableImageView upro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            uemail=itemView.findViewById(R.id.uemail);
            ucomment=itemView.findViewById(R.id.ucomment);
            upro=itemView.findViewById(R.id.upro);
        }
    }
}
