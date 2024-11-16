package com.college.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.college.faculty.FacultyLogin;
import com.college.faculty.FacultyMenu;
import com.college.grievancemanagement.AdminMenu;
import com.college.grievancemanagement.R;
import com.college.grievancemanagement.ShowGrievance;

public class StudentMenu extends AppCompatActivity {

    CardView cd_add_post_grievance,cd_see_post_grievance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        getSupportActionBar().setTitle("Menu");
        cd_add_post_grievance=findViewById(R.id.cd_add_post_grievance);
        cd_see_post_grievance=findViewById(R.id.cd_see_post_grievance);
        cd_add_post_grievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentMenu.this,StudentPostGrievance.class);
                startActivity(i);

            }
        });
        cd_see_post_grievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentMenu.this,StudentSeePostGrievance.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                Intent i = new Intent(StudentMenu.this, StudentLogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}