package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thedev.rajaratacommunity.Helpers.LoadingDialog;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationAddActivity extends AppCompatActivity{
    Spinner year,fac,event;
    EditText wtitle,wbody,wsimpbody;
    AppCompatButton next,send;
    LinearLayout getdlay,getodeance;
    NotificationFilterTool tool;
    Dialog dialog;
    AppCompatButton lb;
    ImageView notificationImg;
    private static final int REQUEST_CODE_LOAD_IMAGE =100 ;
    Bitmap IMG_background;
    Uri Image_BACK;
    String ImageUrl;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_add);

        year=findViewById(R.id.year);
        fac=findViewById(R.id.fac);
        event=findViewById(R.id.event);
        wtitle=findViewById(R.id.title);
        wbody=findViewById(R.id.body);
        wsimpbody=findViewById(R.id.simpbody);
        next=findViewById(R.id.nextbtn);
        getdlay=findViewById(R.id.getdelay);
        getodeance=findViewById(R.id.getodeance);
        send=findViewById(R.id.sendbtn);
        notificationImg=findViewById(R.id.notificationImg);
        loadingDialog=new LoadingDialog(this);

        tool=new NotificationFilterTool();

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.years_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);


        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        year.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,
                R.array.faculty_array,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        adapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        fac.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,
                R.array.event_array,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        adapter3.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        event.setAdapter(adapter3);

        //year.setOnItemClickListener(getApplicationContext());

        notificationImg.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(intent, REQUEST_CODE_LOAD_IMAGE);
        });


        next.setOnClickListener(view -> {

            String title=wtitle.getText().toString();
            String simpbody=wsimpbody.getText().toString();
            String body=wbody.getText().toString();

            if (title.isEmpty()){
                wtitle.setError("Required");

            }else{
                if (simpbody.isEmpty()){
                    wsimpbody.setError("Required");

                }else {
                    if (body.isEmpty()){
                        wbody.setError("Required");

                    }else {
                        if (Image_BACK!=null){
                            loadingDialog.showDialog();
                            long id= System.currentTimeMillis();
                            StorageReference ref= FirebaseStorage.getInstance().getReference("NotificationImages").child(id+"jpg");
                            ref.putFile(Image_BACK).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                loadingDialog.hideDialog();
                                                if (task.isSuccessful()){
                                                    ImageUrl=task.getResult().toString();
                                                }else{
                                                    ImageUrl=null;
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(NotificationAddActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        getdlay.setVisibility(View.GONE);
                        getodeance.setVisibility(View.VISIBLE);
                    }
                }
            }

        });



        send.setOnClickListener(view -> {
            dialogShow();
        });
    }



    private void dialogShow(){
        dialog=new Dialog(NotificationAddActivity.this);

        dialog.setContentView(R.layout.send_not);

        lb=dialog.findViewById(R.id.confirm);
        TextView title=dialog.findViewById(R.id.title);
        TextView simpdesc=dialog.findViewById(R.id.simpdesc);
        TextView desc=dialog.findViewById(R.id.desc);
        TextView filter=dialog.findViewById(R.id.filter);

        title.setText(wtitle.getText().toString());
        simpdesc.setText(wsimpbody.getText().toString());
        desc.setText(wbody.getText().toString());
        String FILT=tool.NotificationFilterTool(year.getSelectedItem().toString(),fac.getSelectedItem().toString(),event.getSelectedItem().toString());
        filter.setText(FILT);

        lb.setOnClickListener(view -> {
            UpdateData(FILT);
        });
        //dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        dialog.show();
    }

    private void UpdateData(String FILT){
        dialog.dismiss();
        loadingDialog.showDialog();
        DatabaseReference dr=FirebaseDatabase.getInstance().getReference("Notifications");
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    DatabaseReference ddr=dr.child(String.valueOf(snapshot.getChildrenCount()+1));
                    HashMap<String,String> data=new HashMap<>();
                    data.put("STATUS","newnot");
                    data.put("ID",String.valueOf(snapshot.getChildrenCount()+1));
                    data.put("TITLE",wtitle.getText().toString());
                    data.put("DESC",wbody.getText().toString());
                    data.put("NOT_DESC",wsimpbody.getText().toString());
                    if (ImageUrl !=null){
                        data.put("IMAGE",ImageUrl);
                    }else{
                        data.put("IMAGE","img");
                    }

                    data.put("FILT",FILT);
                    ddr.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loadingDialog.hideDialog();
                            if (task.isSuccessful()){
                                Toast.makeText(NotificationAddActivity.this, "Notification  is running", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(NotificationAddActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream;

                imageStream = getContentResolver().openInputStream(imageUri);
                IMG_background = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IMG_background.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//Store the compressed data in ByteArrayInputStream
                Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "nisadas_arana", null);
                Image_BACK = Uri.parse(path);


                Glide.with(NotificationAddActivity.this).load(bitmap).centerCrop().into(notificationImg);


                //background.setImageBitmap(bitmap);


            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }




}