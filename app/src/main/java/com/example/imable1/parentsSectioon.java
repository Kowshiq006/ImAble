package com.example.imable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class parentsSectioon extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    Button back;
    Button statusupdate;
    Button Blogs;
    TextView time;
    Button uppu;
    public String timeduration;
    SwitchCompat switchAutoMessage;




    private static final int REQUEST_CODE_LOCATION_PERMISSION=1;



    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    public String userId = firebaseUser.getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_sectioon);
        back=findViewById(R.id.backtothesem);
        statusupdate=findViewById(R.id.statuss);
        Blogs=findViewById(R.id.checkothers);
        time=findViewById(R.id.tme);
        uppu=findViewById(R.id.updatee);
        switchAutoMessage=findViewById(R.id.switch1);



        //fetchdistance();
        fetchdata();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(parentsSectioon.this,homepage.class);
                startActivity(i);
            }
        });

        statusupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iaaa= new Intent(parentsSectioon.this,blogs.class);
                startActivity(iaaa);
            }
        });

        Blogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ia= new Intent(parentsSectioon.this,ImagesActivity2.class);
                startActivity(ia);
            }
        });





        uppu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogbox();
            }
        });





        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

        SharedPreferences sharedPreferences = getSharedPreferences("save",
                MODE_PRIVATE);
        switchAutoMessage.setChecked(sharedPreferences.getBoolean("value", false));

        switchAutoMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchAutoMessage.isChecked() == true) {
                    if (ContextCompat.checkSelfPermission(
                            getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                parentsSectioon.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_LOCATION_PERMISSION
                        );
                    } else {
                        startLocationService();
                        SharedPreferences.Editor editor = getSharedPreferences("save",
                                MODE_PRIVATE).edit();
                        editor.putBoolean("value", true);
                        editor.apply();
                        switchAutoMessage.setChecked(true);


                    }
                }
                else {
                    stopLocationService();
                    SharedPreferences.Editor editor = getSharedPreferences("save",
                            MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    switchAutoMessage.setChecked(false);

                }
            }
        });




    }



    private void openDialogbox() {
        ExampleDialog exampleDialog=new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"Example dialog");
        //fetchdata();
    }


    @Override
    public void applyTexts(String tiime) {
        time.setText(tiime);
        fetchdata();
    }


    private void fetchdata() {

        DocumentReference documentReference=db.collection("Time").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    timeduration=documentSnapshot.getString("time");
                    //Toast.makeText(getApplicationContext(),timeduration,Toast.LENGTH_SHORT).show();
                    time.setText(timeduration+" milliseconds");
                }
                else
                    Toast.makeText(getApplicationContext(),"YOUR PARENTS LEFT YOU ALONE!",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"YOUR PARENTS LEFT YOU ALONE SADLY!",Toast.LENGTH_SHORT).show();

            }
        });
        return;

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private boolean isLocationServiceRunning() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null) {
            for(ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if(AutoMessage.class.getName().equals(service.service.getClassName())) {
                    if(service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }



    private void startLocationService() {
        Log.i("Location", "Start Location Fired");
        if(!isLocationServiceRunning()) {
            Log.i("Location", "Location service running");
            Intent intent = new Intent(getApplicationContext(), AutoMessage.class);
            intent.putExtra("time",timeduration);

            Log.i("Location", "Intet Init");
//            intent.putExtra("TIME", timeduration);

            Log.i("Location", "Setting stuffs");
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);

            Log.i("Location", "Starting Service AutoMessage");
            startService(intent);
            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService() {
        if(isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), AutoMessage.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service stopped", Toast.LENGTH_SHORT).show();
        }
    }


   /* @Override
    public void applyTexts( String time) {
        fetchdata();
        //fetchdistance();
    }*/


}