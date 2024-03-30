package com.example.imable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class articles extends AppCompatActivity {
    Button goingtohomepage;
    Button bigone;
    Button testt1;
    Button testt2;
    Button testt3;
    Button testt4;
    Button articlee1;
    Button articlee2;
    Button articlee3;
    Button articlee4;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        goingtohomepage=findViewById(R.id.backtohomepage);
        bigone=findViewById(R.id.bigbutton);
        testt1=findViewById(R.id.test1);
        testt2=findViewById(R.id.test2);
        testt3=findViewById(R.id.test3);
        testt4=findViewById(R.id.test4);
        articlee1=findViewById(R.id.article1);
        articlee2=findViewById(R.id.article2);
        articlee3=findViewById(R.id.article3);
        articlee4=findViewById(R.id.article4);


        goingtohomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),homepage.class));
            }
        });

        bigone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://need2knowbooks.co.uk/autism-spectrum-disorder-a-difference-not-a-disability/");
            }
        });




        //tests
        testt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.psycom.net/child-autism-test");
            }
        });


        testt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://psychology-tools.com/test/cast");
            }
        });


        testt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.autismspeaks.org/screen-your-child");
            }
        });


        testt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://psychcentral.com/quizzes/autism-test#1");
            }
        });

        articlee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.helpguide.org/articles/autism-learning-disabilities/helping-your-child-with-autism-thrive.htm");
            }
        });


        articlee2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.appliedbehavioranalysisprograms.com/things-parents-of-children-on-the-autism-spectrum-want-you-to-know/");
            }
        });


        articlee3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://theplaceforchildrenwithautism.com/autism-blog/five-mustread-books-for-parents-with-children-on-the-autism-spectrum/");
            }
        });


        articlee4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.sciencedirect.com/science/article/pii/S0010440X18301925");
            }
        });




    }

    private void gotoUrl(String s) {
        Uri uriarticles=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uriarticles));

    }
}