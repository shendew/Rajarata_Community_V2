package com.thedev.rajaratacommunity.Helpers;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thedev.rajaratacommunity.R;

import java.util.HashMap;

import io.paperdb.Paper;

public class AddCommentDialog {

    Activity context;
    AlertDialog.Builder builder;
    Dialog dialog;
    DatabaseReference QARef;


    public AddCommentDialog(Activity context) {
        this.context = context;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = context.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.addcomment_dialog, null));




        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        dialog.show();

        EditText question_text=dialog.findViewById(R.id.question_text);
        AppCompatButton submt=dialog.findViewById(R.id.submit_btn);

        submt.setOnClickListener(view -> {
            if (!question_text.getText().toString().isEmpty()){
                updateQuestions(question_text.getText().toString());
            }

        });
    }

    public void hideDialog(){
        dialog.dismiss();
    }

    private void updateQuestions(String s) {
        Paper.init(context);
        HashMap<String,String> map = new HashMap<>();
        Long id =System.currentTimeMillis();
        QARef= FirebaseDatabase.getInstance().getReference("QA");
        map.put("a","");
        map.put("q",s);
        map.put("owner",Paper.book().read("email"));
        QARef.child(String.valueOf(id)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Comment added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(context, "Comment added failed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
}