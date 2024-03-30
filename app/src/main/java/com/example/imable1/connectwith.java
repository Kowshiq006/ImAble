package com.example.imable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class connectwith extends AppCompatActivity {
    Button homepagebackkk;
    Button org1;
    Button org2;
    Button org3;
    Button org4;
    Button org5;
    Button org6;
    Button org7;
    Button org8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectwith);

        homepagebackkk=findViewById(R.id.homepageback);
        homepagebackkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),homepage.class));
            }
        });



        org1=findViewById(R.id.help1);
        org2=findViewById(R.id.help2);
        org3=findViewById(R.id.help3);
        org4=findViewById(R.id.help4);
        org5=findViewById(R.id.help5);
        org6=findViewById(R.id.help6);
        org7=findViewById(R.id.help7);
        org8=findViewById(R.id.help8);


        org1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("http://dsh.org.bd/pediatric-neuro-surgery/");
            }
        });

        org2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("https://bdnews24.com/health/2018/04/10/bsmmu-gets-institute-of-paediatric-neurodisorder-and-autism");
            }
        });

        org3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("http://www.jpuf.gov.bd/");
            }
        });

        org4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("http://www.acwf-bd.org/");
            }
        });

        org5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("https://www.bpfbd.org/institute-for-special-education/");
            }
        });

        org6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("http://www.autism-swacbd.org/");
            }
        });

        org7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("https://sahic.org/");
            }
        });


        org8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoorgn("https://www.shuchona.org/");
            }
        });

    }

    private void gotoorgn(String s) {
       Uri uriorgs=Uri.parse(s);
       startActivity(new Intent(Intent.ACTION_VIEW,uriorgs));
    }

}