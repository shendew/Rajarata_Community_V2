package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get user from FirebaseAuth, Can be null.
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        //Delay 1000ms to start next screen.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("login process", "run: ___ runneeddd");
                //check if user null or not,if user is not null,Already logged in.
                if (user!=null){
                    startActivity(new Intent(MainActivity.this,HomeScreen.class));
                    finish();
                }else{
                    startActivity(new Intent(MainActivity.this,LoginScreen.class));
                    finish();
                }

            }
        },1000);
    }
}