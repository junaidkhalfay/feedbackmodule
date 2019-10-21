package com.example.feedbackmodule.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedbackmodule.R;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {


    private FirebaseDatabase firebase;
    private DatabaseReference databaseReference;
    TextView name,email,mno,ut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences preferences=getSharedPreferences("userDetails",MODE_PRIVATE);
     String userName=   preferences.getString("username","na");

        name=(TextView)findViewById(R.id.proName);
        email=(TextView)findViewById(R.id.proEmail);
        mno=(TextView)findViewById(R.id.proMobile);
        ut=(TextView)findViewById(R.id.proType);



        firebase = FirebaseDatabase.getInstance();
        databaseReference = firebase.getReference("registerData");




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot.hasChild(userName))
                {

                    genRegister student=dataSnapshot.child(userName).getValue(genRegister.class);


                    name.setText(student.getName());
                    email.setText(student.getEmail());
                    mno.setText(student.getMobileno());
                    ut.setText(student.getUserType());







                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
