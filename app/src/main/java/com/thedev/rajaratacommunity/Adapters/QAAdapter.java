package com.thedev.rajaratacommunity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thedev.rajaratacommunity.CommentView;
import com.thedev.rajaratacommunity.Models.Answer;
import com.thedev.rajaratacommunity.Models.QA;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;

public class QAAdapter extends RecyclerView.Adapter<QAAdapter.Viewholder>{

    Context context;
    ArrayList<QA> qas;
    ArrayList<Answer> answers=new ArrayList<>();

    public QAAdapter() {
    }

    public QAAdapter(Context context, ArrayList<QA> qas) {
        this.context = context;
        this.qas = qas;
    }

    @NonNull
    @Override
    public QAAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.q_a_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull QAAdapter.Viewholder holder, int position) {
        QA qa=qas.get(position);

        if (!answers.isEmpty()){
            answers.clear();
        }

        if (answers!= null){
            try {
                for (String ans: qa.getanswers()){
                    String[] comment=ans.split(":");
                    answers.add(new Answer(comment[0],comment[1]));
                }
            }catch (Exception e){

            }

        }





        AAdapter adapter=new AAdapter(context,answers);

        holder.answerRview.setHasFixedSize(true);
        holder.answerRview.setLayoutManager(new LinearLayoutManager(context));
        holder.answerRview.setAdapter(adapter);



        holder.q_title.setText(qa.getQuestion());
        holder.uemail.setText(qa.getEmail());
        holder.itemView.setOnClickListener(view -> {

                context.startActivity(new Intent(context, CommentView.class)
                        .putExtra("id",qa.getId())
                        .putExtra("q",qa.getQuestion())
                        .putExtra("email",qa.getEmail())
                        .putStringArrayListExtra("ans",qa.getanswers())
                );

        });

    }

    @Override
    public int getItemCount() {
        return qas.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView q_title,uemail;
        RecyclerView answerRview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            q_title=itemView.findViewById(R.id.q_title);
            uemail=itemView.findViewById(R.id.uemail);
            answerRview=itemView.findViewById(R.id.answerRview);
        }
    }
}

