package com.example.imable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class register2 extends AppCompatActivity {
     Button signin;
     Button signupp;
     private  long backPressedTime;
     private Toast backToast;
     Button mapcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mapcheck=findViewById(R.id.lacate_child);

        mapcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),emergency.class));

            }
        });




        signin=findViewById(R.id.welcome_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
        signupp=findViewById(R.id.welcome_signup);
        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
        return;}
        else
        {
           backToast= Toast.makeText(getBaseContext(),"PRESS BACK AGAIN TO EXIT",Toast.LENGTH_SHORT);
           backToast.show();
        }
        backPressedTime=System.currentTimeMillis();


    }
}