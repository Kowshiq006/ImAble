package com.example.imable1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText timebaby;
    private Button savetime;

    private ExampleDialogListener listener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    public String userId = firebaseUser.getUid();




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.updatedialog,null);
        builder.setView(view).setTitle("UPDATE TIME").setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String time=timebaby.getText().toString();
                Map<String, Object> data = new HashMap<>();
                data.put("time", time);
                db.collection("Time").document(userId).set(data);
                listener.applyTexts(time);
            }
        });

        timebaby=view.findViewById(R.id.addtime);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(ExampleDialogListener) context;
        } catch (ClassCastException e) {
            //e.printStackTrace();
            throw  new ClassCastException(context.toString()+
                    "must implement example dialog listener");
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String tiime);
    }


}
