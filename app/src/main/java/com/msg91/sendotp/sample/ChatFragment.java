package com.msg91.sendotp.sample;







import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment  {

    View root;
    SharedPreferences shq, shh, logout;
    Button btn, im;
    Location location;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bitmap;
    private Uri filePath;
    EditText name,ph,add,idd;
    SharedPreferences sh;
    final int RequestPermissionCode = 1;
    ImageView imgview;
    String address, city, state, country, postalCode, knownName;
    Button update;
    static final int REQUEST_CODE = 1;
    List<String> imagesEncodedList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        imgview=root.findViewById(R.id.imgtravell);
     name=root.findViewById(R.id.tvame);
      ph=root.findViewById(R.id.tvph);
     add=root.findViewById(R.id.tvadd);
      update=root.findViewById(R.id.tvbt);
       idd=root.findViewById(R.id.tvid);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }



        });







        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().isEmpty()) {

                    name.setError("null");
                } else if (ph.getText().toString().isEmpty()) {

                    ph.setError("null");
                } else if (add.getText().toString().isEmpty()) {

                    add.setError("null");
                } else if (idd.getText().toString().isEmpty()) {

                    idd.setError("null");
                } else {


                    class UploadImage extends AsyncTask<Bitmap, Void, String> {

                        ProgressDialog loading;
                        RequestHandler rh = new RequestHandler();

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(getContext(), "Uploading...", null, true, false);
                        }


                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            // Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                            if (s.equals("success")) {

                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("UPLOADED")
                                        .setContentText("Back To Home!")
                                        .setConfirmText("Yes")
                                        .show();

                             name.getText().clear();
                             ph.getText().clear();
                                add.getText().clear();
                              idd.getText().clear();

                            }


                        }

                        @SuppressLint("WrongThread")
                        @Override
                        protected String doInBackground(Bitmap... params) {
                            bitmap = params[0];
                            String uploadImage = getStringImage(bitmap);

                            HashMap<String, String> data = new HashMap<>();


                            data.put("name", name.getText().toString());
                            data.put("ph", ph.getText().toString());
                            data.put("add", add.getText().toString());
                            data.put("idd", idd.getText().toString());
                            data.put("img", uploadImage);
                            String result = rh.sendPostRequest("https://androidprojectstechsays.000webhostapp.com/Vehicle_Managemen_system/trval_vechil_details_upload.php", data);

                            return result;
                        }
                    }
                    UploadImage ui = new UploadImage();
                    ui.execute(bitmap);

                }


            }

        });
return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imgview.setImageBitmap(bitmap);
                getStringImage(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

}}



