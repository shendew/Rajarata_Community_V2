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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    public SetupDialog(Activity context, String modiEmail, String userName) {
        this.context = context;
        this.modiEmail = modiEmail;
        this.userName = userName;
    }

    public void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = context.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_lay, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        year=dialog.findViewById(R.id.year);
        fac=dialog.findViewById(R.id.fac);
        save=dialog.findViewById(R.id.save);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(context.getApplicationContext(),
                R.array.faculty_array,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(context.getApplicationContext(),
                R.array.years_array,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        year.setAdapter(adapter);
        fac.setAdapter(adapter2);

        save.setOnClickListener(view -> {
            localSave(year.getSelectedItem().toString(),fac.getSelectedItem().toString());
            saveDB(context,year.getSelectedItem().toString(),fac.getSelectedItem().toString());
        });

        dialog.show();
    }

    private void saveDB(Activity context, String year, String fac) {
        FirebaseDatabase Fdb;
        Fdb=FirebaseDatabase.getInstance();


        HashMap<String,String> map=new HashMap<>();

        map.put("faculty",fac);
        map.put("year",year);


        Fdb.getReference("Users").child(modiEmail).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dismissDialog();
                Toast.makeText(context, "Welcome "+userName, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, HomeScreen.class));
                context.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "LGN_STP_99__"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void localSave(String year, String fac) {

        Paper.book().write("faculty",fac);
        Paper.book().write("year",year);

    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
