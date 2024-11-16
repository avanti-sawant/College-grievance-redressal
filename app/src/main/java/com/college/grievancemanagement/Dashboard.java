package com.college.grievancemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.college.faculty.FacultyLogin;
import com.college.grievancehandler.GrievanceLogin;
import com.college.parents.ParentLogin;
import com.college.student.StudentLogin;

public class Dashboard extends AppCompatActivity {

    CardView cd_admin,cd_student,cd_faculty,cd_parents,cd_grievance_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        cd_admin=findViewById(R.id.cd_admin);
        cd_student=findViewById(R.id.cd_student);
        cd_faculty=findViewById(R.id.cd_faculty);
        cd_parents=findViewById(R.id.cd_parents);
        cd_grievance_handler=findViewById(R.id.cd_grievance_handler);
        cd_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,AdminLogin.class);
                startActivity(i);
            }
        });

        cd_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this, StudentLogin.class);
                startActivity(i);
            }
        });
        cd_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this, FacultyLogin.class);
                startActivity(i);
            }
        });
        cd_parents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this, ParentLogin.class);
                startActivity(i);
            }
        });
        cd_grievance_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this, GrievanceLogin.class);
                startActivity(i);
            }
        });
    }
}