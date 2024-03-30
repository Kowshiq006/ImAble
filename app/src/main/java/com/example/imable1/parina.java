package com.example.imable1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class parina extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    FirebaseAuth fAuth;

    String userId;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parina);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back=findViewById(R.id.backkkk);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iaaa= new Intent(parina.this,homepage.class);
                startActivity(iaaa);
            }
        });




        fAuth=FirebaseAuth.getInstance();
        //fstore=FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();


        db=FirebaseFirestore.getInstance();

        userArrayList=new ArrayList<User>();

        myAdapter= new MyAdapter(parina.this,userArrayList);

        recyclerView.setAdapter(myAdapter);


        EventChangeListener();



    }

    private void EventChangeListener() {

        db.collection(userId).orderBy("date",Query.Direction.DESCENDING).orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error!=null)
                        {
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()){

                            if(dc.getType()==DocumentChange.Type.ADDED){

                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }

                            myAdapter.notifyDataSetChanged();

                        }


                    }
                });
    }
}