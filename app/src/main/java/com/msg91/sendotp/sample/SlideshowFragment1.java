package com.msg91.sendotp.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SlideshowFragment1 extends Fragment {

    View root;
    List<Cheque3> productList;
    SwipeRefreshLayout s;
    //the recyclerview
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    SharedPreferences sh;
    Chequeadapter3 adapter;
    EditText edt;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        root = inflater.inflate(R.layout.fragment_slideshow1, container, false);
        loadProducts();


        recyclerView = root.findViewById(R.id.recylcerViewc12);
        edt = root.findViewById(R.id.editTextsearch12);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe = root.findViewById(R.id.swiperefresh12);
        sh = getActivity().getSharedPreferences("Official", MODE_PRIVATE);
        productList = new ArrayList<>();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList.clear();

                loadProducts();


            }
        });
        return root;
    }

    private void loadProducts() {




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Vehicle_Managemen_system/view_vechil_detailss.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_LONG).show();

                        swipe.setRefreshing(false);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = array.length() - 1; i >= 0; i--) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productList.add(new Cheque3(
                                        product.getString("name"),
                                        product.getString("phone"),
                                        product.getString("image"),
                                        product.getString("address"),
                                        product.getString("idd")

                                ));
                            }

                            adapter = new Chequeadapter3(getActivity(), productList);
                            // adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //  params.put("d",dis.getSelectedItem().toString().toLowerCase());
                // params.put("ph",shh.getString("phone",null));


                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void filter(String text) {
        ArrayList<Cheque3> filteredList = new ArrayList<>();

        for (Cheque3 item : productList) {
            if (item.getImage().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList1(filteredList);
    }

}

//    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Orphanage_manegument_system/orphanege_view.php",
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    //   Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_LONG).show();
//
//                    swipe.setRefreshing(false);
//                    try {
//                        //converting the string to json array object
//                        JSONArray array = new JSONArray(response);
//
//                        //traversing through all the object
//                        for (int i = array.length() - 1; i >= 0; i--) {
//
//
//                            //getting product object from json array
//                            JSONObject product = array.getJSONObject(i);
//                            //adding the product to product list
//                            productList.add(new Cheque(
//                                    product.getString("name"),
//                                    product.getString("phone"),
//                                    product.getString("image"),
//                                    product.getString("orphanage_name"),
//                                    product.getString("latitude"),
//                                    product.getString("longitude")
//
//                            ));
//                        }
