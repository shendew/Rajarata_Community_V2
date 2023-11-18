package com.thedev.rajaratacommunity.Frags;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.ControlPanelScreen;
import com.thedev.rajaratacommunity.Helpers.LoadingDialog;
import com.thedev.rajaratacommunity.MyCommentsScreen;
import com.thedev.rajaratacommunity.MyPostsScreen;
import com.thedev.rajaratacommunity.R;

import io.paperdb.Paper;


public class ProfileFrag extends Fragment {


    FirebaseUser user;
    FirebaseAuth auth;
    String userName,userFac,userImg,userEmail,userRole,userYear;
    TextView pro_name,pro_fac,pro_status,pro_yr;
    AppCompatButton logout_btn;
    ShapeableImageView pro_pic;
    LoadingDialog loadingDialog;
    CardView myQues,myPosts;
    ImageView admin_btn;
    int loadingstts=0;
    public ProfileFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_prof, container, false);
        Paper.init(container.getContext());

        pro_name=v.findViewById(R.id.pro_name);
        pro_fac=v.findViewById(R.id.pro_fac);
        pro_status=v.findViewById(R.id.pro_status);
        logout_btn=v.findViewById(R.id.logout_btn);
        pro_pic=v.findViewById(R.id.pro_pic);
        myPosts=v.findViewById(R.id.myPosts);
        myQues=v.findViewById(R.id.myQues);
        pro_yr=v.findViewById(R.id.pro_yr);
        admin_btn=v.findViewById(R.id.admin_btn);
        loadingDialog=new LoadingDialog(getActivity());
        loadingDialog.showDialog();




        userName=Paper.book().read("name");
        userEmail=Paper.book().read("email");
        userFac=Paper.book().read("faculty");
        userImg=Paper.book().read("user_img");
        userYear=Paper.book().read("year");
        userRole=Paper.book().read("role");


        FirebaseDatabase.getInstance().getReference("Users").child(userEmail.replace(".","-")).child("Role")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userRole=snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        userRole="student";
                    }
                });
        userRole="admin";

        pro_name.setText(userName);
        pro_yr.setText(userYear);
        Glide.with(container.getContext()).load(userImg).centerCrop().into(pro_pic);

//faculties
        if (userFac.equals("tech")){
            pro_fac.setText("Faculty of Technology");
        } else if (userFac.equals("fms")) {
            pro_fac.setText("Faculty of Management Studies");
        } else if (userFac.equals("fas")) {
            pro_fac.setText("Faculty of Applied Science");
        }else if (userFac.equals("foa")) {
            pro_fac.setText("Faculty of Agriculture");
        }
        else if (userFac.equals("fmas")) {
            pro_fac.setText("Faculty of Medicine and Allied Sciences");
        }
        else if (userFac.equals("fssh")) {
            pro_fac.setText("Faculty of Social Sciences and Humanities");
        }else{
            pro_fac.setText(userFac);
        }

//status
        if (userRole.contains("student")){
            pro_status.setText("Undergratuate");
        }else{
            pro_status.setText("Admin");
        }

        if (userRole.contains("admin")){
            admin_btn.setVisibility(View.VISIBLE);
        }else{
            admin_btn.setVisibility(View.GONE);
        }

        loadingstts++;
        if (loadingstts==1){
            loadingDialog.hideDialog();
        }


        logout_btn.setOnClickListener(view -> {
            auth.signOut();
        });

        myQues.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MyCommentsScreen.class));
        });
        myPosts.setOnClickListener(view -> {
           startActivity(new Intent(getContext(), MyPostsScreen.class));
        });
        admin_btn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ControlPanelScreen.class));
            getActivity().finish();
        });
        return v;
    }
}