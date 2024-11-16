package com.college.faculty;

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
import com.college.grievancemanagement.R;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FacultyLogin extends AppCompatActivity {

    TextView txt_register;
    Button btn_login;
    EditText edt_email, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        txt_register = findViewById(R.id.txt_register);
        btn_login = findViewById(R.id.btn_login);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FacultyLogin.this, FacultyRegister.class);
                startActivity(i);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f_email = edt_email.getText().toString().trim();
                String f_password = edt_password.getText().toString().trim();

                if (f_email.equals("") || f_password.equals(""))
                    Toast.makeText(FacultyLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(f_email).matches())
                    Toast.makeText(FacultyLogin.this, "Email ID must be in proper format", Toast.LENGTH_SHORT).show();
                else if(f_password.length() < 6)
                    Toast.makeText(FacultyLogin.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                else
                    facultyLogin(f_email, f_password);
                /*Intent i=new Intent(FacultyLogin.this,FacultyMenu.class);
                startActivity(i);*/
            }
        });
    }

    private void facultyLogin(String f_email, String f_password) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Faculty.FACULTY_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getString("success").equals("1")) {
                        JSONObject data = json.getJSONObject("data");
                        SharedPreference.save("f_id", data.getString("f_id"));
                        SharedPreference.save("f_name", data.getString("f_name"));
                        SharedPreference.save("f_phone", data.getString("f_phone"));
                        SharedPreference.save("f_gender", data.getString("f_gender"));

                        Intent i = new Intent(FacultyLogin.this, FacultyMenu.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(FacultyLogin.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(FacultyLogin.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("f_email", f_email);
                params.put("f_password", f_password);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}