package com.college.grievancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class AdminLogin extends AppCompatActivity {

    Button btn_login;
    EditText edt_email,edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        btn_login=findViewById(R.id.btn_login);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a_email = edt_email.getText().toString().trim();
                String a_password = edt_password.getText().toString().trim();

                if(a_email.equals("") || a_password.equals("")){
                    Toast.makeText(AdminLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else
                    login(a_email, a_password);

                /*Intent i=new Intent(AdminLogin.this,AdminMenu.class);
                startActivity(i);*/
            }
        });
    }

    private void login(String a_email, String a_password) {

        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.ADMIN_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONObject data = json.getJSONObject("data");
                        SharedPreference.save("a_id", data.getString("a_id"));
                        SharedPreference.save("a_email", data.getString("a_email"));
                        SharedPreference.save("a_name", data.getString("a_name"));
                        SharedPreference.save("a_phone", data.getString("a_phone"));
                        Intent i=new Intent(AdminLogin.this,AdminMenu.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AdminLogin.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("a_email", a_email);
                params.put("a_password", a_password);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}