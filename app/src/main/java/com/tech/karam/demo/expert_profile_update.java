package com.tech.karam.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class expert_profile_update extends AppCompatActivity {


    private ImageView iv;
    private String image_s = "";

    EditText name_ett, email_ett, gender_ett, mobile_ett, expert_ett;
    Button update_et, upload_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile_update);
        iv = (ImageView) findViewById(R.id.image_edit);
        upload_et = (Button) findViewById(R.id.upload_image);
        update_et = (Button) findViewById(R.id.done_button);

        name_ett = (EditText) findViewById(R.id.name_id);
        email_ett = (EditText) findViewById(R.id.email_id);
        gender_ett = (EditText) findViewById(R.id.gender_id);
        mobile_ett = (EditText) findViewById(R.id.mobile_id);
        expert_ett = (EditText) findViewById(R.id.expertt_id);

        fetch_values();

    }

    public void fetch_values() {
        SharedPreferences sp = getSharedPreferences("expert_info", MODE_PRIVATE);

        String expert_id = sp.getString("expert_id", "");


        JSONObject job = new JSONObject();
        try {
            job.put("expert_id", expert_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(job);
        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/fetch_values.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);
                try {
                    JSONObject job = response.getJSONObject("result");
                    String email_s = job.getString("Email_id");
                    String name_s = job.getString("Name");
                    String contact_s = job.getString("Contact_no");
                    String gender_s = job.getString("Gender");
                    String expert_s = job.getString("Expert_in");
                    String image_s = job.getString("Image_upload");


                    if(image_s.length() > 100)
                    {
                        iv.setImageBitmap(StringToBitMap(image_s));
                    }

                    name_ett.setText(name_s);
                    email_ett.setText(email_s);
                    gender_ett.setText(gender_s);
                    mobile_ett.setText(contact_s);
                    expert_ett.setText(expert_s);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }
        });
        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        AppController app = new AppController(expert_profile_update.this);
        app.addToRequestQueue(jobreq);


    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void update_profile(View v) {


        String name_et = name_ett.getText().toString();

        String gender_et = gender_ett.getText().toString();

        String mobile_et = mobile_ett.getText().toString();

        String expert_et =expert_ett.getText().toString();
        String email_et = email_ett.getText().toString();

        SharedPreferences sp = getSharedPreferences("expert_info", MODE_PRIVATE);

        String expert_id = sp.getString("expert_id", "");


        JSONObject job = new JSONObject();

        try {
            job.put("name_key", name_et);
            job.put("mobile_key", mobile_et);
            job.put("email_key", email_et);
            job.put("gender_key", gender_et);
            job.put("expert_key", expert_et);
            job.put("expert_id", expert_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(job);

        JsonObjectRequest jobreq = new JsonObjectRequest("http://" + Internet_address.ip + "/update_values.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("result").equals("done")) {

                        Toast.makeText(expert_profile_update.this , "profile updated successfully" ,Toast.LENGTH_SHORT).show();


                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

            }
        });

        jobreq.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        AppController app = new AppController(expert_profile_update.this);
        app.addToRequestQueue(jobreq);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void select_image(View view) {


        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");

        //File file = new File(Environment.getExternalStorageDirectory(),
        //      counter+".jpg");
        //Uri photoPath = Uri.fromFile(file);
        // i.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
        startActivityForResult(i, 100);
    }


    // function to convert bitmap to string

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100 && data != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap2 = decodeUri(expert_profile_update.this, filePath, 700);
                image_s = getStringImage(bitmap2);
                //Setting the Bitmap to ImageView
                iv.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // function to scale down image
    public Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }


    // function to upload image to server

    public void upload(View v) {

        JSONObject job = new JSONObject();
        SharedPreferences sp = getSharedPreferences("expert_info", MODE_PRIVATE);

        String expert_id = sp.getString("expert_id", "");

        try {
            job.put("image", image_s);
            job.put("expert_id" , expert_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+ Internet_address.ip +"/upload_pofile_image_expert.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("result").equals("done")) {
                        Toast.makeText(expert_profile_update.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)

            {
                System.out.println(error);
            }
        });

        AppController app = new AppController(expert_profile_update.this);

        app.addToRequestQueue(jobreq);
    }
}