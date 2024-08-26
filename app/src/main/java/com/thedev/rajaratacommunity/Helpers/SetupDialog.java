package com.thedev.rajaratacommunity.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.thedev.rajaratacommunity.HomeScreen;
import com.thedev.rajaratacommunity.LoginScreen;
import com.thedev.rajaratacommunity.R;

import java.util.HashMap;

import io.paperdb.Paper;

public class SetupDialog {
    Activity context;
    Spinner year,fac;
    Dialog dialog;
    Button save;
    String modiEmail;
    String userName;
    FirebaseDatabase Fdb;

    public SetupDialog(Activity context, String modiEmail, String userName) {
        this.context = context;
        this.modiEmail = modiEmail;
        this.userName = userName;
    }

    public void showDialog(){
        Fdb=FirebaseDatabase.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = context.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.login_setup, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        year=dialog.findViewById(R.id.year);
        fac=dialog.findViewById(R.id.faculty);
        save=dialog.findViewById(R.id.save);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(context.getApplicationContext(),
                R.array.faculty_array,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(context.getApplicationContext(),
                R.array.years_array,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        year.setAdapter(adapter2);
        fac.setAdapter(adapter);

        save.setOnClickListener(view -> {
            localSave(year.getSelectedItem().toString(),fac.getSelectedItem().toString());
            saveDB(context,year.getSelectedItem().toString(),fac.getSelectedItem().toString());
        });


    }

    private void saveDB(Activity context, String year, String fac) {




        HashMap<String,String> map=new HashMap<>();

        map.put("faculty",fac);
        map.put("year",year);

        Fdb.getReference("Users").child(modiEmail).child("faculty").setValue(fac).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Fdb.getReference("Users").child(modiEmail).child("year").setValue(year).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dismissDialog();
                                Toast.makeText(context, "Welcome "+userName, Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, HomeScreen.class));
                                context.finish();
                            }else{
                                Toast.makeText(context, "LGN_STP_106__"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else{
                    Toast.makeText(context, "LGN_STP_112__"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });



//        Fdb.getReference("Users").child(modiEmail).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            dismissDialog();
//                            Toast.makeText(context, "Welcome "+userName, Toast.LENGTH_SHORT).show();
//                            context.startActivity(new Intent(context, HomeScreen.class));
//                            context.finish();
//                        }else{
//                            Toast.makeText(context, "LGN_STP_102__"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    private void localSave(String year, String fac) {

        Paper.book().write("faculty",fac);
        Paper.book().write("year",year);

    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
