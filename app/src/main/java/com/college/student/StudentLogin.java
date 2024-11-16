package com.college.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.faculty.FacultyLogin;
import com.college.faculty.FacultyRegister;
import com.college.grievancemanagement.AddGrievance;
import com.college.grievancemanagement.AdminLogin;
import com.college.grievancemanagement.R;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentLogin extends AppCompatActivity {

    TextView txt_register;
    Button btn_login;
    EditText edt_email,edt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        txt_register=findViewById(R.id.txt_register);
        btn_login=findViewById(R.id.btn_login);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_email = edt_email.getText().toString().trim();
                String s_password = edt_password.getText().toString().trim();
                if(s_email.equals("") || s_password.equals(""))
                    Toast.makeText(StudentLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(s_email).matches())
                    Toast.makeText(StudentLogin.this, "Email ID must be in proper format", Toast.LENGTH_SHORT).show();
                else if(s_password.length() < 6)
                    Toast.makeText(StudentLogin.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                else
                    studentLogin(s_email, s_password);

            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentLogin.this, StudentRegister.class);
                startActivity(i);
            }
        });
    }

    private void studentLogin(String s_email, String s_password) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Student.STUDENT_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONObject data = json.getJSONObject("data");
                        SharedPreference.save("s_id", data.getString("s_id"));
                        SharedPreference.save("s_email", s_email);
                        SharedPreference.save("s_phone", data.getString("s_phone"));
                        SharedPreference.save("s_name", data.getString("s_name"));
                        SharedPreference.save("cd_id", data.getString("cd_id"));
                        SharedPreference.save("b_id", data.getString("b_id"));
                        SharedPreference.save("s_admission_no", data.getString("s_admission_no"));
                        SharedPreference.save("s_gender", data.getString("s_gender"));
                        Intent i=new Intent(StudentLogin.this, StudentMenu.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(StudentLogin.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentLogin.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("s_email", s_email);
                params.put("s_password", s_password);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}