package com.example.imable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText GetFullName, GetMail, GetPass,Getphone;
Button buttonRegister;
TextView loginHere;
ProgressBar progressBar;
FirebaseAuth fAuth;
FirebaseFirestore fstore;
String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

      GetFullName= findViewById(R.id.full_name);
      GetMail=findViewById(R.id.emaill);
      GetPass=findViewById(R.id.take_password);
      Getphone=findViewById(R.id.take_phone);
      buttonRegister=findViewById(R.id.registration);
      loginHere=findViewById(R.id.loginhere);
      progressBar=findViewById(R.id.progressbar);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), homepage.class));
            finish();
        }
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = GetMail.getText().toString().trim();
                String password = GetPass.getText().toString().trim();
                String fullname=GetFullName.getText().toString();
                String phoneno=Getphone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    GetMail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    GetPass.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    GetPass.setError("Password must be at least 6 characters long");
                    return;
                }
                if (TextUtils.isEmpty(phoneno)) {
                    GetPass.setError("Phone number is required");
                    return;
                }
                if (phoneno.length() <11) {
                    Getphone.setError("Phone number must be at least 11 characters long");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(register.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure: Email not sent"+ e.getMessage());

                                }
                            });
                            
                            
                            
                            
                            Toast.makeText(register.this, "Congrulations for Registerring!", Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userId);
                                  Map<String,Object> user = new HashMap<>();
                                  user.put("fname",fullname);
                                  user.put("email",email);
                                  user.put("phone",phoneno);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess:user profile created for"+ userId);
                                }
                            });
                                   startActivity(new Intent(getApplicationContext(), homepage.class));

                        } else {
                            Toast.makeText(register.this, "Error Occured!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }


                });
            }

        });
        loginHere.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),login.class));
        }
    });
    }
}



