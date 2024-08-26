package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Helpers.LoadingDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddSliderScreen extends AppCompatActivity {

    private static final int REQUEST_CODE_LOAD_IMAGE =100 ;
    Bitmap IMG_background;
    Uri Image_BACK;
    ImageView image;
    EditText title;
    EditText desc;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_slider);
        image=findViewById(R.id.sliderImg);
        title=findViewById(R.id.sliderTitle);
        desc=findViewById(R.id.sliderDesc);
        Button add=findViewById(R.id.sliderAdd);
        ElasticImageView back=findViewById(R.id.back);
        loadingDialog=new LoadingDialog(this);

        image.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(intent, REQUEST_CODE_LOAD_IMAGE);
        });

        add.setOnClickListener(view -> {

            String Ttitle=title.getText().toString();
            String Tdesc=desc.getText().toString();

            if (!Ttitle.isEmpty() && Image_BACK!=null){
                loadingDialog.showDialog();
                long id= System.currentTimeMillis();
                StorageReference ref=FirebaseStorage.getInstance().getReference("SliderImages").child(id+"jpg");
                ref.putFile(Image_BACK).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()){
                                        updateData(id,Ttitle,Tdesc,task.getResult().toString());
                                    }else{
                                        Toast.makeText(AddSliderScreen.this, "Failed to get file url", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(AddSliderScreen.this, "No Image FOound", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(this, "Fill the required fields", Toast.LENGTH_SHORT).show();
            }

        });
        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void updateData(long id,String ttitle, String tdesc, String url) {
        Map<String,Object> map=new HashMap<>();
        map.put("title",ttitle);
//        map.put("desc",tdesc);
        map.put("id",id);
        map.put("url",url);
        FirebaseDatabase.getInstance().getReference("Sliders").child(String.valueOf(id)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingDialog.hideDialog();
                if (task.isSuccessful()){
                    Toast.makeText(AddSliderScreen.this, "Slider added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AddSliderScreen.this, "Something went wrong,please try again later", Toast.LENGTH_SHORT).show();
                }
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


                Glide.with(AddSliderScreen.this).load(bitmap).centerCrop().into(image);


                //background.setImageBitmap(bitmap);


            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}