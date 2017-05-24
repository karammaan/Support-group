package com.tech.karam.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class expert_signup_detail_2 extends AppCompatActivity {

    private ImageView iv;

    private String image_s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_signup_detail_2);

        iv = (ImageView) findViewById(R.id.image_view);
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
                Bitmap bitmap2 = decodeUri(expert_signup_detail_2.this, filePath, 200);
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

        // sending values to php two values one is image other is where to put that image in database  i.e expert id
        // and it will be get from shared preference in which i store previously :-)
        JSONObject job = new JSONObject();

        SharedPreferences sp = getSharedPreferences("expert_info" , MODE_PRIVATE);
        String expert_id = sp.getString("expert_id","");

        try {
            job.put("image", image_s);
            job.put("expert_id" , expert_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobreq = new JsonObjectRequest("http://"+Internet_address.ip+"/upload_docs.php", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("result").equals("done")) {
                        Toast.makeText(expert_signup_detail_2.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

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

        AppController app = new AppController(expert_signup_detail_2.this);

        app.addToRequestQueue(jobreq);
    }
    public void done(View v){
        Intent i =new Intent(expert_signup_detail_2.this , expert_home_layout.class);

        startActivity(i);
        finish();
    }
    }




