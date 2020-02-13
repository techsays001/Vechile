package com.msg91.sendotp.sample;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter extends RecyclerView.Adapter<Chequeadapter.ProductViewHolder> {
    Intent i;
SharedPreferences sh;

    private Context mCtx;
    private List<Cheque> productList;

    public Chequeadapter(Context mCtx, List<Cheque> productList) {
        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
        this.mCtx = mCtx;
        this.productList = productList;
       // sh=mCtx.getSharedPreferences("Official1",MODE_PRIVATE);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_c, null);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Cheque cheque = productList.get(position);

        holder.name.setText(cheque.getImage());
        holder.detalis.setText(cheque.getStatus());

        Picasso.get().load(cheque.getUser1()).into(holder.image);
        holder.ph.setText(cheque.getUser2());
        holder.idd.setText(cheque.getUser3());

        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);

holder.call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+cheque.getStatus()));
        mCtx.startActivity(intent);




    }
});

//        holder.map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+cheque.getUser3()+","+cheque.getUser4()));
//                mCtx.startActivity(intent);
//
//
//
//
//
//
//
//            }
//        });


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(mCtx, Eventok.class);
                j.putExtra("id",cheque.getUser3());
                mCtx.startActivity(j);


            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = cheque.getUser1()+"\t\t\t\t"+"phone number :"+"\t\t\t"+cheque.getStatus()+"\t\t\t\t"+"busname :"+"\t\t\t"+cheque.getUser2()+"\t\t\t\t"+"oner name :"+"\t\t\t"+cheque.getImage();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mCtx.startActivity(sharingIntent);

            }

        });




        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(holder.sms.getText().toString().isEmpty()){

    holder.sms.setError("Enter a message");
}
else {


    Uri uri = Uri.parse("smsto:" + cheque.getStatus());
    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
    i.putExtra("sms_body", holder.sms.getText().toString());
    i.setPackage("com.android.mms");
    mCtx.startActivity(i);
    holder.sms.getText().clear();
}

            }
        });


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView name,view, detalis, a, ph,app,call,msg,lo,la, map,share,idd;
        ImageView image;
        EditText sms;
        Button buy, del;


        public ProductViewHolder(View itemView) {
            super(itemView);

            sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
            name = itemView.findViewById(R.id.t_discription1);
            ph = itemView.findViewById(R.id.pph2);
            detalis = itemView.findViewById(R.id.pph1);
            image = itemView.findViewById(R.id.t_name11);

        call = itemView.findViewById(R.id.phcall);
          msg= itemView.findViewById(R.id.phmsg);
        idd= itemView.findViewById(R.id.id123);
           sms= itemView.findViewById(R.id.smstxt);
            share= itemView.findViewById(R.id.map2);
          view= itemView.findViewById(R.id.view);
        }

//    @Override
//    public int getItemCount() {
//        return productList.size();
//    }
//
//    class ProductViewHolder extends RecyclerView.ViewHolder {
//
//

//        public ProductViewHolder(View itemView) {
//            super(itemView);
//
//

////            review=itemView.findViewById(R.id.re);
////            viewreview=itemView.findViewById(R.id.ve);
////          //  pid=itemView.findViewById(R.id.productidd);
//
//        }
//
//    }

    }

    public void filterList(ArrayList<Cheque> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }

}
