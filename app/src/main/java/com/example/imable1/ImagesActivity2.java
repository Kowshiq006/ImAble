package com.example.imable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity2 extends AppCompatActivity implements ImageAdapter1.OnItemClickListener {
        private RecyclerView mRecyclerView;
        private ImageAdapter1 mAdapter;
        private ProgressBar mProgressCircle;
        private FirebaseStorage mStorage;
        private DatabaseReference mDatabaseRef;
        private ValueEventListener mDBListener;
        //private List<Upload1> mUploads;
        private List<Upload> mUploads;


        Button back;
        Button uppload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images2);

            mRecyclerView = findViewById(R.id.recycler_view1);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mProgressCircle = findViewById(R.id.progress_circle1);
            mUploads = new ArrayList<>();
            mAdapter = new ImageAdapter1(ImagesActivity2.this, mUploads);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(ImagesActivity2.this);

            back=findViewById(R.id.homepagezaoa);
            uppload=findViewById(R.id.blogupload);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iaoo= new Intent(ImagesActivity2.this,homepage.class);
                    startActivity(iaoo);
                }
            });

            uppload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent izoo= new Intent(ImagesActivity2.this,blogs.class);
                    startActivity(izoo);
                }
            });



            mStorage = FirebaseStorage.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("blogs");
            mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUploads.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Upload upload = postSnapshot.getValue(Upload.class);
                        upload.setKey(postSnapshot.getKey());
                        mUploads.add(upload);
                    }
                    mAdapter.notifyDataSetChanged();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ImagesActivity2.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }
        @Override
        public void onItemClick(int position) {
            Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onWhatEverClick(int position) {
            Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onDeleteClick(int position) {
            Upload selectedItem = mUploads.get(position);
            final String selectedKey = selectedItem.getKey();
            StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mDatabaseRef.child(selectedKey).removeValue();
                    Toast.makeText(ImagesActivity2.this, "Item deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            mDatabaseRef.removeEventListener(mDBListener);
        }
    }




