package com.example.feedbackmodule.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.feedbackmodule.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class genRegistration extends AppCompatActivity {


    static final String[] usertype = new String[]{"UserType","Student", "Teacher"};
    Intent intent;
    FirebaseDatabase firebase;
    DatabaseReference databaseReference;

    EditText etname,etemailid,etmobileno,etaddress,etrollno;
    Spinner sputype;
    Button btsubmit;
    String name,email,mobileno,utype,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_registration);





        intent=new Intent(this,LoginActivity.class);
        firebase = FirebaseDatabase.getInstance();
        databaseReference = firebase.getReference("registerData");





        etname=(EditText)findViewById(R.id.gnname);
        etemailid=(EditText)findViewById(R.id.gnemail);
        etmobileno=(EditText)findViewById(R.id.gnmobileno);


        sputype=(Spinner) findViewById(R.id.gnuserType);


        btsubmit=(Button)findViewById(R.id.gnsubmit);

        // start year spinner Data
        ArrayAdapter<String> startYearArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,usertype);
        startYearArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sputype= (Spinner) findViewById(R.id.gnuserType);
        sputype.setAdapter(startYearArray);
        sputype.setSelection(0);



        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genRegister genregistration=new genRegister();

                getRegData();
                genregistration.setName(name);
                genregistration.setEmail(email);
                genregistration.setMobileno(mobileno);
                genregistration.setPassword(password);
                genregistration.setUserType(utype);



                String e=email.substring(0,email.indexOf("@"));

                databaseReference.child(e).setValue(genregistration);

                Toast.makeText(genRegistration.this,"Register Successfully ", Toast.LENGTH_LONG).show();
                startActivity(intent);


            }
        });




    }


    private void  getRegData()
    {
        name=etname.getText().toString();
        email=etemailid.getText().toString();
        mobileno=etmobileno.getText().toString();



        utype=sputype.getSelectedItem().toString();

        password=PasswordGen();

    }

    public  String PasswordGen()
    {
        int len=6;
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";



        String values = Capital_chars + Small_chars +
                numbers ;

        // Using random method
        Random rndm_method = new Random();

        String password="" ;

        for (int i = 0; i < len; i++)
        {
            password+=values.charAt(rndm_method.nextInt(values.length()));

        }
        return password;
    }

}
