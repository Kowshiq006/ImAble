package com.example.imable1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG="activity";
    private static final String TIME="time";
    private static final String DATE="date";


    String CurrentDate;
    String CurrentTime;



    Button verify;
    TextView notverified;
    FirebaseAuth fauth;
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();;
    String userId;
    DrawerLayout drawerlayoutt;
    NavigationView navigationvieww;
    Toolbar toolbarr;
    public Uri imageUri;
    Button btnAdd;
    EditText editTxt;
    TextView customText;
    ImageView customImg;
    FirebaseStorage storage;
    StorageReference storageReference;
    Button customm;
    Button emergencyy;

    private  long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


             drawerlayoutt = findViewById(R.id.drawer_logout);
             navigationvieww=findViewById(R.id.nav_view);
             toolbarr=findViewById(R.id.toolbar1);

             setSupportActionBar(toolbarr);
             getSupportActionBar().setTitle("ImAble");
             navigationvieww.bringToFront();

             ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this,drawerlayoutt,toolbarr,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
             drawerlayoutt.addDrawerListener(toggle1);
             toggle1.syncState();

            navigationvieww.setNavigationItemSelectedListener(this);



            customm=findViewById(R.id.button113);
            customm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ir = new Intent(homepage.this, ImagesActivity.class);
                    startActivity(ir);
                }
            });



         emergencyy=findViewById(R.id.emergency);
         emergencyy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent irg = new Intent(homepage.this, emergency.class);
                 startActivity(irg);
             }
         });





        fauth= FirebaseAuth.getInstance();
        //fstore=FirebaseFirestore.getInstance();
        verify=findViewById(R.id.verifybutton);
        notverified=findViewById(R.id.emailnotverified);
        userId= fauth.getCurrentUser().getUid();
        FirebaseUser user = fauth.getCurrentUser();

        if(!user.isEmailVerified())
        {
            notverified.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Verification email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Email not sent" + e.getMessage());

                        }
                    });
                }
            });

        }




        //buuton sound setting

        final MediaPlayer helpme= MediaPlayer.create(this,R.raw.helpme);
        Button helpmee= findViewById(R.id.helpme);
        helpmee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpme.start();
                String title=helpmee.getText().toString();
                savedata(title);
            }
        });



        final MediaPlayer okay= MediaPlayer.create(this,R.raw.okay);
        Button okayy= findViewById(R.id.okay);
        okayy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okay.start();
                String title=okayy.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer no= MediaPlayer.create(this,R.raw.no);
        Button noo= findViewById(R.id.no);
        noo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.start();
                String title=noo.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer wanteatt= MediaPlayer.create(this,R.raw.hungry);
        Button wanteat= findViewById(R.id.wantToeat);
        wanteat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wanteatt.start();
                String title=wanteat.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer drinkk= MediaPlayer.create(this,R.raw.drink);
        Button wantwater= findViewById(R.id.drinkwater);
        wantwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkk.start();
                String title=wantwater.getText().toString();
                savedata(title);
            }
        });

        final MediaPlayer toilett= MediaPlayer.create(this,R.raw.toilet);
        Button toiilet= findViewById(R.id.toilet);
        toiilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toilett.start();
                String title=toiilet.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer happyy= MediaPlayer.create(this,R.raw.happy);
        Button happ= findViewById(R.id.happy);
        happ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyy.start();
                String title=happ.getText().toString();
                savedata(title);
            }
        });

        final MediaPlayer sadd= MediaPlayer.create(this,R.raw.cry);
        Button saad= findViewById(R.id.sad);
        saad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadd.start();
                String title=saad.getText().toString();
                savedata(title);
            }
        });
        final MediaPlayer scaredd= MediaPlayer.create(this,R.raw.scared);
        Button scareed= findViewById(R.id.scared);
        scareed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaredd.start();
                String title=scareed.getText().toString();
                savedata(title);
            }
        });

        final MediaPlayer angryyy= MediaPlayer.create(this,R.raw.angry);
        Button angryy= findViewById(R.id.angry);
        angryy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angryyy.start();
                String title=angryy.getText().toString();
                savedata(title);
            }
        });
        final MediaPlayer sick= MediaPlayer.create(this,R.raw.sick);
        Button sickk= findViewById(R.id.sick);
        sickk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sick.start();
                String title=sickk.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer sleep= MediaPlayer.create(this,R.raw.sleepy);
        Button sleeep= findViewById(R.id.sleep);
        sleeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleep.start();
                String title=sleeep.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer mom= MediaPlayer.create(this,R.raw.mom);
        Button mommy= findViewById(R.id.wheresmom);
        mommy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mom.start();
                String title=mommy.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer play= MediaPlayer.create(this,R.raw.play);
        Button wantpaly= findViewById(R.id.wantToplay);
        wantpaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.start();
                String title=wantpaly.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer wash= MediaPlayer.create(this,R.raw.washhands);
        Button washing= findViewById(R.id.washHands);
        washing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wash.start();
                String title=washing.getText().toString();
                savedata(title);
            }
        });


        final MediaPlayer uncomfy= MediaPlayer.create(this,R.raw.uncomfortable);
        Button uncomfortable= findViewById(R.id.uncomfortable);
        uncomfortable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncomfy.start();
                String title=uncomfortable.getText().toString();
                savedata(title);
            }
        });



        final MediaPlayer touch= MediaPlayer.create(this,R.raw.donttouch);
        Button donttouch= findViewById(R.id.dontTouch);
        donttouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touch.start();
                String title=donttouch.getText().toString();
                savedata(title);
            }
        });



        final MediaPlayer thank= MediaPlayer.create(this,R.raw.thanks);
        Button thanks= findViewById(R.id.thankyou);
        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thank.start();
                String title=thanks.getText().toString();
                savedata(title);
            }
        });



        final MediaPlayer soory= MediaPlayer.create(this,R.raw.sorry);
        Button sooory= findViewById(R.id.sorry);
        sooory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soory.start();
                String title=sooory.getText().toString();
                savedata(title);
            }
        });








    }

    @Override
    public void onBackPressed() {
        if(drawerlayoutt.isDrawerOpen(GravityCompat.START))
        {
            drawerlayoutt.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId())
         {
             case R.id.drawer_history:
                 Intent abc = new Intent(homepage.this, parina.class);
                 startActivity(abc);
                 break;
             case R.id.drawer_parent:
                 Intent abe = new Intent(homepage.this,parentsSectioon.class);
                 startActivity(abe);
                 break;
             case  R.id.drawer_article:
                 Intent jn = new Intent(homepage.this, articles.class);
                 startActivity(jn);
                 break;
             case R.id.drawer_logout:
                 FirebaseAuth.getInstance().signOut();
                 Intent kn = new Intent(homepage.this, login.class);
                 startActivity(kn);
                 finish();
                 break;
             case  R.id.drawer_help:
                 Intent in = new Intent(homepage.this, connectwith.class);
                 startActivity(in);
                 break;

             case R.id.drawer_imagecustomizatio:
                 Intent iz= new Intent(homepage.this,customizedimages.class);
                 startActivity(iz);
                 break;
             case R.id.drawer_blogs:
                 Intent izaoo= new Intent(homepage.this,ImagesActivity2.class);
                 startActivity(izaoo);
                 break;


         }
        drawerlayoutt.closeDrawer(GravityCompat.START);
        return true;
    }




    private void savedata(String title) {

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/ MM/ YYYY");
        CurrentDate=dateFormat.format(calendar.getTime());
        Calendar caltime=Calendar.getInstance();
        SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
        CurrentTime=timeformat.format(caltime.getTime());
        Map<String,Object> data=new HashMap<>();
        data.put(TAG,title);
        data.put(DATE,CurrentDate);
        data.put(TIME,CurrentTime);


        fstore.collection(userId).document().set(data);

    }






}
