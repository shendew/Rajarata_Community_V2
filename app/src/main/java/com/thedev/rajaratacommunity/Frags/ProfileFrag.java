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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thedev.rajaratacommunity.Helpers.LoadingDialog;
import com.thedev.rajaratacommunity.R;

import io.paperdb.Paper;


public class ProfileFrag extends Fragment {


    FirebaseUser user;
    FirebaseAuth auth;
    String userName,userFac,userImg,userEmail,userRole;
    TextView pro_name,pro_fac,pro_status;
    AppCompatButton logout_btn;
    ShapeableImageView pro_pic;
    LoadingDialog loadingDialog;
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
        loadingDialog=new LoadingDialog(getActivity());
        loadingDialog.showDialog();


        userName=Paper.book().read("name");
        userEmail=Paper.book().read("email");
        userFac=Paper.book().read("faculty");
        userImg=Paper.book().read("user_img");
        userRole="student";


        pro_name.setText(userName);
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

        return v;
    }
}