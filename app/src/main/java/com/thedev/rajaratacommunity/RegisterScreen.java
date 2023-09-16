package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class RegisterScreen extends AppCompatActivity {

    AppCompatButton register_btn;
    EditText regemail_txt,regpass_txt,regrepass_txt;
    FirebaseDatabase Fdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        register_btn=findViewById(R.id.register_btn);
        regemail_txt=findViewById(R.id.regemail_txt);
        regpass_txt=findViewById(R.id.regpass_txt);
        regrepass_txt=findViewById(R.id.regrepass_txt);
        Fdb=FirebaseDatabase.getInstance();


        register_btn.setOnClickListener(view -> {
            String regEmail=regemail_txt.getText().toString();
            String regPass=regpass_txt.getText().toString();
            String regRePass=regrepass_txt.getText().toString();

            if (!regEmail.isEmpty() && !regPass.isEmpty() && !regRePass.isEmpty()){
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            }else{
                if (regEmail.contains("rjt.ac.lk")){
                    Fdb.getReference("Users").child(regEmail).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (!snapshot.exists()){
                                HashMap<String,String> map=new HashMap();
                                map.put("name", "");
                                if (regEmail.contains("tec")){
                                    map.put("faculty","tech");
                                }
                                map.put("mobile", "");
                                map.put("role","student");
                                map.put("user_img","");
                                map.put("password","");
                                Fdb.getReference("Users").child(regEmail).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterScreen.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterScreen.this, "RJS_74__"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(RegisterScreen.this, "You're already registered.", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RegisterScreen.this, "RJS_85__"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(this, "You have no access", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterScreen.this,HomeScreen.class));
                finish();
            }
        },1000);
    }
}