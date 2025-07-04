package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Helpers.SetupDialog;

import java.util.HashMap;

import io.paperdb.Paper;

public class LoginScreen extends AppCompatActivity {
    private static final int RC_SIGN_IN = 10;
    private String verificationId;
    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    LottieAnimationView google_login_button;
    FirebaseDatabase Fdb;
    AppCompatButton logn_btn;
    EditText useremail_txt,userpass_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        google_login_button=findViewById(R.id.google_login_button);
//        logn_btn=findViewById(R.id.logn_btn);
//        useremail_txt=findViewById(R.id.useremail_txt);
//        userpass_txt=findViewById(R.id.userpass_txt);
        mAuth= FirebaseAuth.getInstance();
        Fdb=FirebaseDatabase.getInstance();
        Paper.init(LoginScreen.this);



        //create a GoogleSignInOptions object using webclientID.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("889287265633-1kk7c3mkfk80e6j84cvmgo5h57e7pbgf.apps.googleusercontent.com")
                .requestEmail()
                .build();
        //getting ready googleSignInClient using gso
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        google_login_button.setOnClickListener(view -> {
            signIn();
        });

//        logn_btn.setOnClickListener(view -> {
//            String userEmail=useremail_txt.getText().toString();
//            String userPasswrod=userpass_txt.getText().toString();
//            if (!userEmail.isEmpty() && !userPasswrod.isEmpty()){
//                Fdb.getReference("Users").child(userEmail).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (!snapshot.exists()){
//                            Toast.makeText(LoginScreen.this, "You Haven't Registered yet.", Toast.LENGTH_SHORT).show();
//                        }else{
//                            if(snapshot.child("password").getValue(String.class).equals(userPasswrod)){
//                                Paper.book().write("login_mode","up");
//
//                                Paper.book().write("email",userEmail);
//                                Paper.book().write("name",snapshot.child("name").getValue(String.class));
//                                Paper.book().write("faculty",snapshot.child("faculty").getValue(String.class));
//                                Paper.book().write("role",snapshot.child("role").getValue(String.class));
//                                Paper.book().write("user_img",snapshot.child("user_img").getValue(String.class));
//
//                                Toast.makeText(LoginScreen.this, "Welcome "+userEmail, Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(LoginScreen.this,HomeScreen.class));
//                                finish();
//                            }else{
//                                Toast.makeText(LoginScreen.this, "Password is wrong.", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(LoginScreen.this, "LGN_109__"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }else{
//                Toast.makeText(this, "Please fill details", Toast.LENGTH_SHORT).show();
//            }
//        });

    }



    //Start account picker intent.
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check for the request code
        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //check permitted emails
                GoogleSignInAccount account=task.getResult(ApiException.class);
                if (account.getEmail().contains("rjt.ac.lk")){
                    firebaseLogn(account.getIdToken());
                }else{
                    googleSignInClient.signOut();
                    Toast.makeText(this, "You have no permissions.", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Toast.makeText(this, "LGN_81__"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseLogn(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //get user and create modiEmail to Firebase database folder.
                            FirebaseUser user= mAuth.getCurrentUser();
                            String modiEmail=user.getEmail().replace(".","-");
                            Fdb.getReference("Users").child(modiEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //write user data to local database
                                    Paper.book().write("email",user.getEmail());
                                    Paper.book().write("name",user.getDisplayName());
                                    Paper.book().write("user_img",String.valueOf(user.getPhotoUrl()));
                                        //upload the iser data folder to firebase database
                                        HashMap<String,String> map=new HashMap();
                                        map.put("name", user.getDisplayName());
                                        map.put("faculty","");
                                        map.put("mobile", user.getPhoneNumber());
                                        map.put("role","student");
                                        map.put("fav","");
                                        map.put("year","");
                                        map.put("user_img",String.valueOf(user.getPhotoUrl()));

                                        Fdb.getReference("Users").child(modiEmail).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                //Toast.makeText(LoginScreen.this, "Welcome "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                SetupDialog dialog=new SetupDialog(LoginScreen.this,modiEmail,user.getDisplayName());
                                                dialog.showDialog();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(LoginScreen.this, "LGN_126__"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(LoginScreen.this, "LGN_184__"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }else{
                            Toast.makeText(LoginScreen.this, "LGN_98__task error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}