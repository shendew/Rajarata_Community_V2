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
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        loadingDialog=new LoadingDialog(getActivity());
        loadingDialog.showDialog();


        userName=Paper.book().read("name");
        userEmail=Paper.book().read("email");
        userFac=Paper.book().read("faculty");
        userImg=Paper.book().read("user_img");
        userYear=Paper.book().read("year");
        userRole="student";


        pro_name.setText(userName);
        pro_yr.setText(userYear);
        Glide.with(container.getContext()).load(userImg).centerCrop().into(pro_pic);

//faculties
        if (userFac.equals("tec")){
            pro_fac.setText("Faculty of Technology");
        } else if (userFac.equals("mgt")) {
            pro_fac.setText("Faculty of Management Studies");
        } else if (userFac.equals("fas")) {
            pro_fac.setText("Faculty of Applied Science");
        }

//status
        if (userRole.contains("student")){
            pro_status.setText("Undergratuate");
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
        return v;
    }
}