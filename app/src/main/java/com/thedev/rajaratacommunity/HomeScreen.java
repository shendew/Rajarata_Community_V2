package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thedev.rajaratacommunity.Frags.FavFrag;
import com.thedev.rajaratacommunity.Frags.HomeFrag;
import com.thedev.rajaratacommunity.Frags.NotiFrag;
import com.thedev.rajaratacommunity.Frags.ProfileFrag;


public class HomeScreen extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);



        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.home) {

                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack("home")
                                .replace(R.id.fragment_container_view, HomeFrag.class, null,"home")
                                .commit();

//                        getSupportFragmentManager().popBackStack();



                } else if (itemId == R.id.fav) {

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("fav")
                            .replace(R.id.fragment_container_view, FavFrag.class, null,"fav")
                            .commit();


                } else if (itemId == R.id.notifications) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("noti")
                            .replace(R.id.fragment_container_view, NotiFrag.class, null,"noti")
                            .commit();

                } else if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("prof")
                            .replace(R.id.fragment_container_view, ProfileFrag.class, null,"prof")
                            .commit();

                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, HomeFrag.class, null)
                    .commit();
        }


    }



}