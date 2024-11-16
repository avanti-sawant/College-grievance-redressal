package com.college.grievancehandler;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class GrievanceLogin extends AppCompatActivity {

    Button btn_login;
    EditText edt_email,edt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_login);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        btn_login=findViewById(R.id.btn_login);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermissionStatus()){
                    String gh_email = edt_email.getText().toString().trim();
                    String gh_password = edt_password.getText().toString().trim();

                    if(gh_email.equals("") || gh_password.equals(""))
                        Toast.makeText(GrievanceLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    else if(!Patterns.EMAIL_ADDRESS.matcher(gh_email).matches())
                        Toast.makeText(GrievanceLogin.this, "Email ID must be in proper format", Toast.LENGTH_SHORT).show();
                    else if(gh_password.length() < 6)
                        Toast.makeText(GrievanceLogin.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    else
                        grevienceHandlerLogin(gh_email, gh_password);
                }
            }
        });

    }

    private boolean checkPermissionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 123);
                return false;
            }else
                return true;
        }else{
            return  true;
        }
    }

    private void grevienceHandlerLogin(String gh_email, String gh_password) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Handler.HANDLER_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONObject data = json.getJSONObject("data");
                        SharedPreference.save("gh_id", data.getString("gh_id"));
                        SharedPreference.save("gh_name", data.getString("gh_name"));
                        SharedPreference.save("gh_email", data.getString("gh_email"));
                        SharedPreference.save("gh_phone", data.getString("gh_phone"));

                        Intent i=new Intent(GrievanceLogin.this,SeeGrievenceDetails.class);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(GrievanceLogin.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(GrievanceLogin.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gh_email", gh_email);
                params.put("gh_password", gh_password);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}