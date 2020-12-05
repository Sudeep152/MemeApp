package com.sudeep.memestudio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Button nextBtn, shareBtn;
     String currenturl=null;
     String Downlaod="https://mega.nz/file/N0V3DSgY#BKDLQY9o70w9I0yy5qWMnt2bW1nhNi1bwJ7px3-dhWY";
     private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.meme);

        nextBtn = findViewById(R.id.next);
        shareBtn = findViewById(R.id.share);
      progressBar=new ProgressDialog(MainActivity.this);
      progressBar.setMessage("Loading...");
      progressBar.setCancelable(true);
      progressBar.show();
        loadmeme();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.show();
                loadmeme();
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "! Hi buddy check this epic meme \uD83D\uDE02 This is come from "+ currenturl + "Download meme Apk  "+ Downlaod);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

    }

    private void loadmeme() {
        RequestQueue queue = Volley.newRequestQueue(this);
         String url = "https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            currenturl= response.getString("url");
                            Glide.with(MainActivity.this).load(currenturl).into(img);
                            progressBar.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();


                    }
                });

        queue.add(jsonObjectRequest);

    }
}