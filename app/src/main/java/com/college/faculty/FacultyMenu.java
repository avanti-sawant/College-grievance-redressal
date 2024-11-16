package com.college.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.college.grievancemanagement.AdminLogin;
import com.college.grievancemanagement.AdminMenu;
import com.college.grievancemanagement.R;
import com.college.student.StudentMenu;
import com.college.student.StudentPostGrievance;
import com.college.student.StudentSeePostGrievance;

public class FacultyMenu extends AppCompatActivity {
    CardView cd_add_post_grievance,cd_see_post_grievance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_menu);
        getSupportActionBar().setTitle("Menu");
        cd_add_post_grievance=findViewById(R.id.cd_add_post_grievance);
        cd_see_post_grievance=findViewById(R.id.cd_see_post_grievance);
        cd_add_post_grievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FacultyMenu.this, FacultyPostGrievance.class);
                startActivity(i);

            }
        });
        cd_see_post_grievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FacultyMenu.this, SeeFacultyPostGrievance.class);
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
                Intent i = new Intent(FacultyMenu.this, FacultyLogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}