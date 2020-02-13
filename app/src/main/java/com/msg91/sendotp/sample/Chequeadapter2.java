package com.msg91.sendotp.sample;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter2 extends RecyclerView.Adapter<Chequeadapter2.ProductViewHolder> {
    Intent i;


    private Context mCtx;
    private List<Cheque2> productList;

    public Chequeadapter2(Context mCtx, List<Cheque2> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_c1, null);
        return new ProductViewHolder(view);








    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final   Cheque2 cheque;   cheque = productList.get(position);


        holder.kl.setText(cheque.getPrize1());
        holder.uphone.setText(cheque.getPrize2());
        holder.date.setText(cheque.getPrize3());
        holder.idd.setText(cheque.getPrize5());
        holder.place.setText(cheque.getPrize6());
        holder.ammount.setText(cheque.getPrize7());
        holder.capacity.setText(cheque.getPrize9());
        Picasso.get().load(cheque.getPrize8()).into(holder.image);

        sh= mCtx.getSharedPreferences("data",MODE_PRIVATE);


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+cheque.getPrize7()));
                mCtx.startActivity(intent);




            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(holder.msgg.getText().toString().isEmpty()){


    holder.msgg.setError("null");
}
else {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Vehicle_Managemen_system/sent_sms.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//If we are getting success from server
                    //  Toast.makeText(mCtx,response,Toast.LENGTH_LONG).show();

                    holder.msgg.getText().clear();
                    if (response.equals("Password sent to your registerd phonenumber")) {
//
                        new SweetAlertDialog(mCtx, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("MSG sent to  registerd phonenumber")
                                .setContentText("Back To Home!")
                                .show();

                    } else {


                        new SweetAlertDialog(mCtx, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("MSG sent to  registerd phonenumber")
                                .setContentText("Back To Home!")
                                .show();


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
//Adding parameters to request
            params.put("sms", holder.msgg.getText().toString());
            params.put("phone", cheque.getPrize7());

//returning parameter
            return params;
        }

    };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
    RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
    requestQueue.add(stringRequest);


}
            }
        });



    }




    @Override
    public int getItemCount() {
        return productList.size();
    }
    SharedPreferences sh;
    class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView idd,kl,capacity,place,duration,ammount,date,uphone,call,send;
       ImageView image;

EditText msgg;

        public ProductViewHolder(View itemView) {
            super(itemView);

     kl= itemView.findViewById(R.id.kl33);
          capacity= itemView.findViewById(R.id.capacity33);
       place= itemView.findViewById(R.id.place33);
        // duration= itemView.findViewById(R.id.d);
          ammount= itemView.findViewById(R.id.ammount33);
           idd= itemView.findViewById(R.id.id33);
            date = itemView.findViewById(R.id.date33);
          uphone= itemView.findViewById(R.id.userph33);
          image= itemView.findViewById(R.id.busimg33);
            call= itemView.findViewById(R.id.usercall);
         send = itemView.findViewById(R.id.send33);
        msgg= itemView.findViewById(R.id.msg33);




        }


    }

}