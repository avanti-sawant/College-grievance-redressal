package com.college.grievancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddNewBatch extends AppCompatActivity {

    EditText edtNewBatch, edtGrievenceSubject;
    Button btnNewBatch, btnAddSubject;
    RadioGroup rd_group;
    RadioButton rd_btn1,rd_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_batch);
        getSupportActionBar().setTitle("Add New Batch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        edtNewBatch = findViewById(R.id.edt_add_new_batch);
        btnNewBatch = findViewById(R.id.btn_add_new_batch);
        edtGrievenceSubject = findViewById(R.id.edt_add_grievance_subject);
        btnAddSubject = findViewById(R.id.btn_add_subject);
        rd_group=findViewById(R.id.rd_group);
        rd_btn1=findViewById(R.id.rd_btn1);
        rd_btn2=findViewById(R.id.rd_btn2);

        btnNewBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b_name = edtNewBatch.getText().toString().trim();

                if(b_name.equals(""))
                    Toast.makeText(AddNewBatch.this, "Please enter batch name", Toast.LENGTH_SHORT).show();
                else
                    addNewBatch(b_name);
            }
        });

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gs_title = edtGrievenceSubject.getText().toString().trim();
                String gs_type = "";
                if(rd_btn1.isChecked())
                    gs_type = "1";
                if(rd_btn2.isChecked())
                    gs_type = "2";
                if(gs_title.equals(""))
                    Toast.makeText(AddNewBatch.this, "Enter Grievence Subject", Toast.LENGTH_SHORT).show();
                else
                    addGrievenceSubject(gs_title, gs_type);
            }
        });


    }

    private void addGrievenceSubject( String gs_title, String gs_type) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.ADD_GRIEVENCE_SUBJECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject j = new JSONObject(response);
                    if(j.getString("success").equals("1"))
                        edtGrievenceSubject.setText("");
                    Toast.makeText(AddNewBatch.this, j.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddNewBatch.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gs_title", gs_title);
                params.put("gs_type", gs_type);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    private void addNewBatch(String b_name) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.ADD_NEW_BATCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject j = new JSONObject(response);
                    if(j.getString("success").equals("1")){
                        edtNewBatch.setText("");
                    }
                    Toast.makeText(AddNewBatch.this, j.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddNewBatch.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("b_name", b_name);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(AddNewBatch.this,AdminMenu.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}