package com.example.feedbackmodule.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.feedbackmodule.MainActivity;
import com.example.feedbackmodule.R;

public class UserHome extends AppCompatActivity {

    Button btnProfile,btnFeedback,btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_home);

        btnFeedback=(Button)findViewById(R.id.btnFeedback);
        btnProfile=(Button)findViewById(R.id.btnProfile);
        btnLogout=(Button)findViewById(R.id.btnLogout);

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profile.class));

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("userDetails",MODE_PRIVATE);
                SharedPreferences.Editor ed=preferences.edit();
                ed.clear();

                ed.commit();

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });




    }
}
