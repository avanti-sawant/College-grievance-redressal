package com.college.parents;

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
import com.college.grievancemanagement.R;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParentLogin extends AppCompatActivity {

    TextView txt_register;
    Button btn_login;
    EditText edt_email,edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        txt_register=findViewById(R.id.txt_register);
        btn_login=findViewById(R.id.btn_login);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ParentLogin.this, ParentRegister.class);
                startActivity(i);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p_email = edt_email.getText().toString().trim();
                String p_password = edt_password.getText().toString().trim();

                if(p_email.equals("") || p_password.equals(""))
                    Toast.makeText(ParentLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(p_email).matches())
                    Toast.makeText(ParentLogin.this, "Email ID must be in proper format", Toast.LENGTH_SHORT).show();
                else if(p_password.length() < 6)
                    Toast.makeText(ParentLogin.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                else
                    parentLogin(p_email, p_password);
            }
        });
    }

    private void parentLogin(String p_email, String p_password) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Parent.PARENT_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONObject data = json.getJSONObject("data");
                        SharedPreference.save("p_id", data.getString("p_id"));
                        SharedPreference.save("p_name", data.getString("p_name"));
                        SharedPreference.save("p_phone", data.getString("p_phone"));

                        Intent i=new Intent(ParentLogin.this, ParentMenu.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    }
                    Toast.makeText(ParentLogin.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ParentLogin.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_email", p_email);
                params.put("p_password", p_password);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}