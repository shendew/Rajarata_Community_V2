package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;

import android.app.ActivityManager;
import android.app.FragmentContainer;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skydoves.elasticviews.ElasticImageView;

import com.thedev.rajaratacommunity.Frags.FavFrag;
import com.thedev.rajaratacommunity.Frags.HomeFrag;
import com.thedev.rajaratacommunity.Frags.NotiFrag;
import com.thedev.rajaratacommunity.Frags.ProfileFrag;
import com.thedev.rajaratacommunity.Services.NotificationService;

import io.paperdb.Paper;


public class HomeScreen extends AppCompatActivity{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView home_menu;
    RelativeLayout toolBar;
    FragmentContainerView fragment_container_view;

    String logSts="";
    FirebaseUser user;
    FirebaseAuth auth;
    FloatingActionButton addPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Paper.init(HomeScreen.this);


        auth=FirebaseAuth.getInstance();



//        logSts=Paper.book().read("login_mode");
//        if (!logSts.equals("up")){
            if (auth.getCurrentUser() !=null){
                user=auth.getCurrentUser();
            }else{
                Toast.makeText(this, "Security Warning..... Logging out automatically", Toast.LENGTH_SHORT).show();
            }

//        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        drawerLayout =findViewById(R.id.my_drawer_layout);
        navigationView=findViewById(R.id.side_navi);
        home_menu=findViewById(R.id.home_menu);
        toolBar=findViewById(R.id.toolBar);
        fragment_container_view=findViewById(R.id.fragment_container_view);
        addPosts=findViewById(R.id.addPosts);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel= new NotificationChannel("RUSTLE","RUSL", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationChannel defaultvhannel= new NotificationChannel("default","Default", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager defaultmanager =getSystemService(NotificationManager.class);
            defaultmanager.createNotificationChannel(defaultvhannel);

        }



        navigationView.bringToFront();
        drawerLayout.closeDrawer(GravityCompat.START);
        home_menu.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                if (id == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("home")
                            .replace(R.id.fragment_container_view, HomeFrag.class, null,"home")
                            .commit();
                } else if (id == R.id.qa) {
                    startActivity(new Intent(HomeScreen.this, QAScreen.class));
                } else if (id == R.id.contact) {
                    startActivity(new Intent(HomeScreen.this, ContactScreen.class));
                } else if (id == R.id.about) {
                    startActivity(new Intent(HomeScreen.this,AboutScreen.class));
                }
                return true;
            }
        });

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

        addPosts.setOnClickListener(view -> {
            startActivity(new Intent(this,AddPostScreen.class));
        });


    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isMyServiceRunning(NotificationService.class)){
            try {
                startService(new Intent(this, NotificationService.class));
            }catch (Exception e){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

            }

    }



    }
}