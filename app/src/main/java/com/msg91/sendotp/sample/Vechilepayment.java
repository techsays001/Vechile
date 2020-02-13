package com.msg91.sendotp.sample;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msg91.sendotpandroid.library.Verification;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Vechilepayment extends AppCompatActivity implements PaymentResultListener {
        EditText date;
        Button pay;
        private DatePicker datePicker;
Intent j;
    SharedPreferences sh;
        private TextView dateView;
        DatePickerDialog datePickerDialog;
        int year;
        int month;
        int dayOfMonth;
        Calendar calendar;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechilepayment);
        date = findViewById(R.id.datepay);
        pay = findViewById(R.id.paybt);
           sh=getSharedPreferences("data",MODE_PRIVATE);
         //   Toast.makeText(Vechilepayment.this, sh.getString("phone",null),Toast.LENGTH_LONG).show();
j=getIntent();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(Vechilepayment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (date.getText().toString().isEmpty()) {

                    date.setError("null");

                }
                else {

                    new SweetAlertDialog(Vechilepayment.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("You  Must  Pay 10% of total ammount")
                            .setContentText("ok")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog
//                                            .setTitleText("Logining...!")
//
//                                            .setConfirmText("OK")

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    startPayment();
                                                }
                                            })
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();
                }




//




            }

            private void startPayment() {

                final View.OnClickListener activity = this;

                final Checkout co = new Checkout();

                try {
                    JSONObject options = new JSONObject();
                    options.put("name", "Razorpay Corp");
                    options.put("description", "Demoing Charges");
                    //You can omit the image option to fetch the image from dashboard
                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                    options.put("currency", "INR");
                    options.put("amount",  String.valueOf(Integer.parseInt(j.getStringExtra("rs"))/(1000)*(10000)));

                    JSONObject preFill = new JSONObject();
                    preFill.put("email", "test@razorpay.com");
                    preFill.put("contact", "9876543210");

                    options.put("prefill", preFill);

                    co.open(Objects.requireNonNull(Vechilepayment.this), options);
                } catch (Exception e) {
                    Toast.makeText(Vechilepayment.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    e.printStackTrace();
                }





            }







        });


    }



    @Override
    public void onPaymentSuccess(String s) {
        data();
    }

    private void data() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Vehicle_Managemen_system/Advance_payment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        if(response.contains("ok"))
                        {

                            new SweetAlertDialog(Vechilepayment.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Payment Success")
                                    .setContentText("Back to Dashboard!")
                                    .setConfirmText("ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog
                                                    .setTitleText("Logining...!")

                                                    .setConfirmText("OK")

                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            Intent in=new Intent(Vechilepayment.this,MainActivity2.class);
                                                            startActivity(in);
                                                        }
                                                    })
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        }
                                    })
                                    .show();




//
                        }


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");


                            }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("kl", j.getStringExtra("kl"));
                params.put("sc",  j.getStringExtra("sc"));
                params.put("p",  j.getStringExtra("place"));
                params.put("d",  j.getStringExtra("d"));
                params.put("img", j.getStringExtra("img"));
                params.put("rs",j.getStringExtra("rs"));
                params.put("ph",sh.getString("phone",null ));
                params.put("idd",j.getStringExtra("idd"));
                params.put("date",date.getText().toString());
//

//returning parameter
                return params;
            }

        };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(Vechilepayment.this);
        requestQueue.add(stringRequest);
    }





    @Override
    public void onPaymentError(int i, String s) {

    }

}