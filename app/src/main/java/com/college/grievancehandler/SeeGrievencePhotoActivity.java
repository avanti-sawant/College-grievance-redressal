package com.college.grievancehandler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.college.grievancemanagement.R;
import com.college.util.Keys;
import com.squareup.picasso.Picasso;

public class SeeGrievencePhotoActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_grievence_photo);
        getSupportActionBar().setTitle("See Grievance Photo");

        iv = findViewById(R.id.imageView);

        Intent i = getIntent();
        String path = i.getStringExtra("g_photo");

        Picasso.get()
                .load(Keys.Common.DOCUMENT_PATH + path)
                .into(iv);
    }
}