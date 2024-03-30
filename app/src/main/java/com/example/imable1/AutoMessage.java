package com.example.imable1;



import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AutoMessage extends Service {
    //public String time = "4000000000";
    public String time;
    public String phoneNumber;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    public String userId=firebaseUser.getUid(); ;
    public FirebaseFirestore db=FirebaseFirestore.getInstance();

    //public String timecheck="30000";
    //public String Phone="+8801304196213";
    public String str;








    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);


            if (locationResult != null && locationResult.getLastLocation() != null) {

                fetchdataAndSendMessage(locationResult);
            }
        }
    };


    private void sendSMS(LocationResult locationResult)
    {
        double latitude = locationResult.getLastLocation().getLatitude();
        double longitude = locationResult.getLastLocation().getLongitude();

        String message = "Regular Location update Latitude = " + latitude + "Longitude = " + longitude;
        SmsManager smsManager = SmsManager.getDefault();

        try {
            if(phoneNumber != null) {
                smsManager.sendTextMessage(phoneNumber, null, message, null, null); // Problem, Raising SecurityException
            }
        }
        catch (SecurityException error)
        {
            Toast.makeText(getApplicationContext(),"Error Occured while sending message.",Toast.LENGTH_SHORT).show();
        }
        Log.d("LOCATION_UPDATE", latitude + ", " + longitude);
    }


    private void fetchdataAndSendMessage(LocationResult locationResult) {
        //Log.d("MainActi",Phone);
        DocumentReference documentReference=db.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                         phoneNumber = document.getString("phone");
                         sendSMS((locationResult));
                    } else {
                        Log.d("Fetchdata", "No such document");
                    }
                } else {
                    Log.d("Fetchdata", "get failed with ", task.getException());
                }
            }

        });








    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startLocationService(Intent currentIntent) {
        String channelId = "location_notification_channel";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = currentIntent;
        time=resultIntent.getStringExtra("time");
//        Log.d("TIME", time == null?time:"No Time");


        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(),
                channelId
        );

        builder.setSmallIcon(R.mipmap.icooooon);
        builder.setContentTitle("Location Service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null
                    && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }








        //LocationRequest locationRequest = new LocationRequest();
        //locationRequest.setInterval(Long.parseLong(time)*60*60*1000);
        //locationRequest.setFastestInterval(Long.parseLong(time)*60*60*1000);
        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(Long.parseLong(time));
        locationRequest.setFastestInterval(Long.parseLong(time));
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(Constants.LOCATION_SERVICE_ID,builder.build());


    }



    private void stopLocationService() {
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            String action = intent.getAction();
            if(action != null) {
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE)) {

                    startLocationService(intent);
                } else if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)) {
                    stopLocationService();
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


}
