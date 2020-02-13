package com.msg91.sendotp.sample;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Registration extends AppCompatActivity {
    Location location;
    Intent intent ;
    private DatePicker datePicker;

    private TextView dateView;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    EditText name, email,addres,dob;
    TextView nametxt, emailtxt, dobtxt,addresstxt, login_title;
    TextView logo;
    String ph;
    SharedPreferences sh;
    LinearLayout already_have_account_layout;
    CardView register_card;
    String address, city, state, country, postalCode, knownName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        intent = getIntent();
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        nametxt = findViewById(R.id.nametxt);
        email = findViewById(R.id.email);
        emailtxt = findViewById(R.id.emailtxt);

        dobtxt = findViewById(R.id.dobtxt);
        addres = findViewById(R.id.address);
        addresstxt = findViewById(R.id.addresstxt);
//        logo = findViewById(R.id.logo);

        already_have_account_layout = findViewById(R.id.already_have_account_text);
        register_card = findViewById(R.id.register_card);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(Registration.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dob.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }
//    String emailPattern = "@gmail.com";

    public void registerButton(View view) {
        if (name.getText().toString().isEmpty()){

            name.setError("Empty Field");
        }
        else if ( email.getText().toString().isEmpty()){

            email.setError("Empty Field");
        }

        else if (addres.getText().toString().isEmpty()){
            addres.setError("Empty Field");

        }

        else
        {
            Intent i=new Intent(getApplicationContext(), regtwo.class);
            i.putExtra("name",name.getText().toString());
              i.putExtra("dob",dob.getText().toString());
            i.putExtra("email",email.getText().toString());
            i.putExtra("address",addres.getText().toString());

            startActivity(i);
            //  Toast.makeText(getApplicationContext(),i.getStringExtra(farmername.getText().toString()), Toast.LENGTH_LONG).show();
        }

    }



    }


