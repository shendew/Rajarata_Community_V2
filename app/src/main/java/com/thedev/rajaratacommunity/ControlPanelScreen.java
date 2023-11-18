package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;

public class ControlPanelScreen extends AppCompatActivity {

    CardView notificationc,livepanel,slidercc,eventcc;
    ElasticImageView back;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel_screen);
        notificationc=findViewById(R.id.notificaionct);
        livepanel=findViewById(R.id.livepannel);
        back=findViewById(R.id.back);
        slidercc=findViewById(R.id.slidercc);
        eventcc=findViewById(R.id.eventcc);
        user= FirebaseAuth.getInstance().getCurrentUser();

        back.setOnClickListener(view -> {
            finish();
        });
        RoleCheck();


//        TODO: add other functions

        notificationc.setOnClickListener(view -> {
            /*NotificationCenterActivity
            *  >> NotificationAddActivity
            *  >> NotificationCenterAdapter
            *
            * */
            startActivity(new Intent(this, NotificationCenterActivity.class));
        });
        livepanel.setOnClickListener(view -> {

            startActivity(new Intent(this,LivePannel.class));
        });
        eventcc.setOnClickListener(view -> {
            //startActivity(new Intent(this,EventCenterActivity.class));
        });
        slidercc.setOnClickListener(view -> {
            //startActivity(new Intent(this,SlidersCenterActivity.class));
        });








    }

    public void RoleCheck(){
        String upemail= user.getEmail().replace(".","-");
        FirebaseDatabase.getInstance().getReference("Users").child(upemail).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue(String.class).equals("admin")) {

                }else {
                    Dialog dialog=new Dialog(ControlPanelScreen.this);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.setContentView(R.layout.session_ex_dialog);
                    dialog.setCancelable(false);
                    dialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(ControlPanelScreen.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                            dialog.dismiss();

                        }
                    },3000);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}