package com.example.imable1;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.imable1.databinding.ActivityEmergencyBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class emergency extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private ActivityEmergencyBinding binding;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 60000;
    private final long MIN_DIST = 50;

    private LatLng latLng;
    //private Button emergencyBtn;
    private EditText editTextLong;
    private EditText editTextLat;
    private TextView textView;
    Button backk;
    Button getloc;


    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    public String userId ;
    public FirebaseFirestore db=FirebaseFirestore.getInstance();
    public String phonenumber;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        //binding = ActivityEmergencyBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());


        //firebaseAuth = FirebaseAuth.getInstance();
        //firebaseUser = firebaseAuth.getCurrentUser();
        backk = findViewById(R.id.backtoregidterr);
        textView = findViewById(R.id.textView);
        editTextLat = findViewById(R.id.editTextLat);
        editTextLong = findViewById(R.id.editTextLong);
        getloc=findViewById(R.id.getlocatiiion);
        if(firebaseUser!=null)
        {textView.setVisibility(View.INVISIBLE);
            editTextLat.setVisibility(View.INVISIBLE);
            editTextLong.setVisibility(View.INVISIBLE);
            getloc.setVisibility(View.INVISIBLE);
            userId = firebaseUser.getUid();
            fetchdata();

        }
        else {
            getloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backk.setVisibility(View.INVISIBLE);
                    getLocationDetails(view);

                }

            });
        }

        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iaoo= new Intent(emergency.this,homepage.class);
                startActivity(iaoo);

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        //EventChangeListener();

    }





    private void fetchdata() {

        DocumentReference documentReference=db.collection("users").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    phonenumber=documentSnapshot.getString("phone");
                    //Log.d("Phone",phonenumber);
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
        public void onMapReady (GoogleMap googleMap) {
            mMap = googleMap;
            //fetchdata();

            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));




           // if (firebaseUser != null) {
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        try {

                            latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(latLng).title("My Position"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            //String phoneNumber = "+8801304196213";
                            String myLatidude = String.valueOf(location.getLatitude());
                            String myLongitude = String.valueOf(location.getLongitude());
                            if(firebaseUser!=null){

                            String message = "I am in Danger. My position is, Latitude = " + myLatidude + "  and Longitude = " + myLongitude;
                            Log.d("LocaaTION",myLatidude);
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phonenumber, null, message, null, null);}

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };


                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                try {
                    //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,MIN_DIST,locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }




            public void getLocationDetails(View view) {


                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                if (!(editTextLong.getText().toString().isEmpty() || editTextLat.getText().toString().isEmpty())) {
                    latitude = Double.parseDouble(editTextLat.getText().toString());
                    longitude = Double.parseDouble(editTextLong.getText().toString());
                    latLng = new LatLng(latitude, longitude);
                }

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                String address = null;
                String city = null;
                String state = null;
                String country = null;
                String postalCode = null;
                String knonName = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    postalCode = addresses.get(0).getPostalCode();
                    knonName = addresses.get(0).getFeatureName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in : " + address + city + state + country + postalCode + knonName));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                textView.setText(address + city + state + country + postalCode + knonName);
            }



            }











