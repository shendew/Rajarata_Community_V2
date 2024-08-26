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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import io.paperdb.Paper;

public class AddPostScreen extends AppCompatActivity {
    Bitmap IMG_background;
    Uri Image_BACK;
    String ImageUrl;
    LoadingDialog loadingDialog;
    EditText Ptitle,Pdesc;
    ImageView image;
    Button add;
    int REQUEST_CODE_LOAD_IMAGE=100;
    ElasticImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_screen);
        add=findViewById(R.id.addPostBT);
        Ptitle=findViewById(R.id.postTitle);
        Pdesc=findViewById(R.id.postDesc);
        image=findViewById(R.id.postImg);
        back=findViewById(R.id.back);
        Paper.init(this);
        loadingDialog=new LoadingDialog(this);

        image.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);


            startActivityForResult(intent, REQUEST_CODE_LOAD_IMAGE);
        });

        add.setOnClickListener(view -> {
            String Ttitle=Ptitle.getText().toString();
            String Tdesc=Pdesc.getText().toString();
            long id= System.currentTimeMillis();
            if (Image_BACK != null){
                loadingDialog.showDialog();

                StorageReference ref= FirebaseStorage.getInstance().getReference("PostImages").child(id+"jpg");
                ref.putFile(Image_BACK).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    if (task.isSuccessful()){
                                        ImageUrl=task.getResult().toString();
                                        if (!Ttitle.isEmpty() && !Tdesc.isEmpty()){
                                            uploadTask(id,Ttitle,Tdesc,ImageUrl);
                                        }else{
                                            loadingDialog.hideDialog();
                                            Toast.makeText(AddPostScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        loadingDialog.hideDialog();
                                        ImageUrl=null;
                                        Toast.makeText(AddPostScreen.this, "Image upload failed,Please try again later", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }else{
                            loadingDialog.hideDialog();
                            Toast.makeText(AddPostScreen.this, "No Image Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                if (!Ttitle.isEmpty() && !Tdesc.isEmpty()){
                    uploadTask(id,Ttitle,Tdesc,ImageUrl);
                }
            }
        });

        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void uploadTask(long id, String ttitle, String tdesc, String imageUrl) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Map<String,Object> map=new HashMap<>();
        map.put("auther",Paper.book().read("email"));
        map.put("desc",tdesc);
        map.put("title",ttitle);
        map.put("id",String.valueOf(id));
        if (imageUrl != null){
            map.put("url",imageUrl);
        }else{
            map.put("url","none");
        }
        map.put("user",user.getUid());

        FirebaseDatabase.getInstance().getReference("Posts").child(String.valueOf(id)).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadingDialog.hideDialog();
                        Toast.makeText(AddPostScreen.this, "Post Addded, You can check it in My Posts", Toast.LENGTH_SHORT).show();
                        finish();
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


                Glide.with(AddPostScreen.this).load(bitmap).centerCrop().into(image);


                //background.setImageBitmap(bitmap);


            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}