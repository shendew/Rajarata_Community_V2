package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.util.HashMap;

import io.paperdb.Paper;

public class LoginScreen extends AppCompatActivity {
    private static final int RC_SIGN_IN = 10;
    private String verificationId;
    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    ImageView google_login_button;
    FirebaseDatabase Fdb;
    AppCompatButton logn_btn;
    EditText useremail_txt,userpass_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        google_login_button=findViewById(R.id.google_login_button);
        logn_btn=findViewById(R.id.logn_btn);
        useremail_txt=findViewById(R.id.useremail_txt);
        userpass_txt=findViewById(R.id.userpass_txt);
        mAuth= FirebaseAuth.getInstance();
        Fdb=FirebaseDatabase.getInstance();
        Paper.init(LoginScreen.this);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("889287265633-1kk7c3mkfk80e6j84cvmgo5h57e7pbgf.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        google_login_button.setOnClickListener(view -> {
            signIn();
        });

        logn_btn.setOnClickListener(view -> {
            String userEmail=useremail_txt.getText().toString();
            String userPasswrod=userpass_txt.getText().toString();
            if (!userEmail.isEmpty() && !userPasswrod.isEmpty()){
                Fdb.getReference("Users").child(userEmail).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()){
                            Toast.makeText(LoginScreen.this, "You Haven't Registered yet.", Toast.LENGTH_SHORT).show();
                        }else{
                            if(snapshot.child("password").getValue(String.class).equals(userPasswrod)){
                                Paper.book().write("login_mode","up");

                                Paper.book().write("email",userEmail);
                                Paper.book().write("name",snapshot.child("name").getValue(String.class));
                                Paper.book().write("faculty",snapshot.child("faculty").getValue(String.class));
                                Paper.book().write("role",snapshot.child("role").getValue(String.class));
                                Paper.book().write("user_img",snapshot.child("user_img").getValue(String.class));

                                Toast.makeText(LoginScreen.this, "Welcome "+userEmail, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                                finish();
                            }else{
                                Toast.makeText(LoginScreen.this, "Password is wrong.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginScreen.this, "LGN_109__"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(this, "Please fill details", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
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
                            FirebaseUser user= mAuth.getCurrentUser();
                            Fdb.getReference("Users").child(user.getEmail()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Paper.book().write("login_mode","ga");
                                    if (!snapshot.exists()){
                                        HashMap<String,String> map=new HashMap();
                                        map.put("name", user.getDisplayName());
                                        if (user.getEmail().contains("tec")){
                                            map.put("faculty","tech");
                                        }
                                        map.put("mobile", user.getPhoneNumber());
                                        map.put("role","student");
                                        map.put("user_img",user.getPhotoUrl().toString());
                                        map.put("password","");
                                        Fdb.getReference("Users").child(user.getEmail()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(LoginScreen.this, "Welcome "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(LoginScreen.this, "LGN_126__"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else{
                                        Toast.makeText(LoginScreen.this, "Welcome "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                                        finish();
                                    }
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