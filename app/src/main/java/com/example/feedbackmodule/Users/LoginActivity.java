package com.example.feedbackmodule.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feedbackmodule.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public Button crt_acct;
    Intent i;

    private FirebaseDatabase firebase;
    private DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebase = FirebaseDatabase.getInstance();
        databaseReference = firebase.getReference("registerData");

        SharedPreferences preferences=getSharedPreferences("userDetails",MODE_PRIVATE);
        SharedPreferences.Editor ed=preferences.edit();
        ed.clear();

        ed.commit();

        i = new Intent(this, genRegistration.class);
        crt_acct = findViewById(R.id.crt_acct);
        crt_acct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.btnLogin);








        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Query mQueryRef = databaseReference.child("registration");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       if( dataSnapshot.hasChild(usernameEditText.getText().toString()))
                       {

                          genRegister student=dataSnapshot.child(usernameEditText.getText().toString()).getValue(genRegister.class);

                         if(passwordEditText.getText().toString().equals(student.getPassword()))
                         {
                             SharedPreferences preferences=getSharedPreferences("userDetails",MODE_PRIVATE);
                             SharedPreferences.Editor ed=preferences.edit();
                             ed.clear();
                             ed.putString("username",usernameEditText.getText().toString());
                             ed.putString("utype",student.getUserType());
                             ed.commit();
                             Toast.makeText(LoginActivity.this,"Logged IN ", Toast.LENGTH_LONG).show();
                             startActivity(new Intent(  LoginActivity.this,UserHome.class));

                         }

                       }
                       else
                       {
                           Toast.makeText(LoginActivity.this,"Login Unsucessfull ", Toast.LENGTH_LONG).show();


                       }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }


}
