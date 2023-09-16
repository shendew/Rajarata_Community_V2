package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skydoves.elasticviews.ElasticImageView;

public class ContactScreen extends AppCompatActivity {
    ElasticImageView back;
    TextView gtel1,gtel2,gtel3,gem,vcstele1,vcstele2,vcsema1,vcsema2,regtel1,regtel2,regema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
        back=findViewById(R.id.back);
        initView();




        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void initView() {
        gtel1=findViewById(R.id.gtel1);
        gtel2=findViewById(R.id.gtel2);
        gtel3=findViewById(R.id.gtel3);
        gem=findViewById(R.id.gem);
        vcstele1=findViewById(R.id.vcstele1);
        vcstele2=findViewById(R.id.vcstele2);
        vcsema1=findViewById(R.id.vcsema1);
        vcsema2=findViewById(R.id.vcsema2);
        regtel1=findViewById(R.id.regtel1);
        regtel2=findViewById(R.id.regtel2);
        regema=findViewById(R.id.regema);
    }

    public void onClick(View view) {
        int id=view.getId();

        if (id == R.id.gtel1) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266645")));
        } else if (id == R.id.gtel2) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266646")));
        } else if (id == R.id.gtel3) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266650")));
        } else if (id == R.id.gem) {
            startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.fromParts("mailto", "info@rjt.ac.lk", null)));
        } else if (id == R.id.vcsema1) {
            startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.fromParts("mailto", "vc@rjt.ac.lk", null)));
        } else if (id == R.id.vcsema2) {
            startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.fromParts("mailto", "vcrusl@yahoo.com", null)));
        } else if (id == R.id.vcstele1) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266644")));
        } else if (id == R.id.vcstele2) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266512")));
        } else if (id == R.id.regtel1) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266511")));
        } else if (id == R.id.regtel2) {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0252266511")));
        } else if (id == R.id.regema) {
            startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.fromParts("mailto", " regofffice@rjt.ac.lk", null)));
        }
    }
}
