package com.college.grievancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AdminMenu extends AppCompatActivity {

    CardView cd_add_grievance,cd_add_new_batch,cd_approval,cd_approval_users,cd_all_grievance_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        cd_add_grievance=findViewById(R.id.cd_add_grievance);
        cd_add_new_batch=findViewById(R.id.cd_add_new_batch);
        cd_approval=findViewById(R.id.cd_approval);
        cd_approval_users=findViewById(R.id.cd_approval_users);
        cd_all_grievance_handler=findViewById(R.id.cd_all_grievance_handler);
        getSupportActionBar().setTitle("Menu");
        cd_add_grievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMenu.this,AddGrievance.class);
                startActivity(i);
            }
        });
        cd_add_new_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMenu.this,AddNewBatch.class);
                startActivity(i);
            }
        });

        cd_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMenu.this,ApprovalUsers.class);
                startActivity(i);
            }
        });
        cd_approval_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMenu.this,ShowApproval.class);
                startActivity(i);
            }
        });
        cd_all_grievance_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMenu.this,ShowGrievance.class);
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
                Intent i = new Intent(AdminMenu.this, AdminLogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}