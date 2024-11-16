package com.college.grievancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddGrievance extends AppCompatActivity {

    Button btnAddGrievence;
    EditText edtName, edtEmail, edtPhone, edtPass, edtCPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grievance);
        getSupportActionBar().setTitle("Add Grievance Handler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        btnAddGrievence = findViewById(R.id.btn_register);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtPass = findViewById(R.id.edt_password);
        edtCPass = findViewById(R.id.edt_conf_pass);

        btnAddGrievence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gh_name = edtName.getText().toString().trim();
                String gh_email = edtEmail.getText().toString().trim();
                String gh_phone = edtPhone.getText().toString().trim();
                String gh_password = edtPass.getText().toString().trim();
                String gh_c_password = edtCPass.getText().toString().trim();

                if(gh_name.equals("") || gh_phone.equals("") || gh_email.equals("") || gh_password.equals("") || gh_c_password.equals(""))
                    Toast.makeText(AddGrievance.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if(!gh_password.equals(gh_c_password))
                    Toast.makeText(AddGrievance.this, "Password and confirm password not matching", Toast.LENGTH_SHORT).show();
                else
                    grievenceHandlerRegister(gh_name, gh_email, gh_phone, gh_password);
            }
        });

    }

    private void grievenceHandlerRegister(String gh_name, String gh_email, String gh_phone, String gh_password) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.ADD_GRIEVENCE_HANDLER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    Toast.makeText(AddGrievance.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                    if(json.getString("success").equals("1"))
                        finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddGrievance.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gh_name", gh_name);
                params.put("gh_email", gh_email);
                params.put("gh_phone", gh_phone);
                params.put("gh_password", gh_password);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}