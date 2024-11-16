package com.college.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.grievancemanagement.R;
import com.college.student.StudentRegister;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacultyRegister extends AppCompatActivity {

    EditText edt_name,edt_email,edt_phone,edt_password,edt_conf_pass,edt_designation,edt_id_no;
    Spinner spinner_department;
    RadioGroup rd_group;
    RadioButton rd_btn1,rd_btn2;
    Button btn_register;

    ArrayList<String> alDepartment = new ArrayList<String>();
    ArrayAdapter<String> adapterDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_register);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.edt_email);
        edt_phone=findViewById(R.id.edt_phone);
        edt_password=findViewById(R.id.edt_password);
        edt_conf_pass=findViewById(R.id.edt_conf_pass);
        edt_designation=findViewById(R.id.edt_designation);
        edt_id_no=findViewById(R.id.edt_id_no);
        spinner_department=findViewById(R.id.spinner_department);
        rd_group=findViewById(R.id.rd_group);
        rd_btn1=findViewById(R.id.rd_btn1);
        rd_btn2=findViewById(R.id.rd_btn2);
        btn_register=findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f_name = edt_name.getText().toString().trim();
                String f_gender = "";
                if(rd_btn1.isChecked())
                    f_gender = "1";
                if(rd_btn2.isChecked())
                    f_gender = "2";
                String f_email = edt_email.getText().toString().trim();
                String f_phone = edt_phone.getText().toString().trim();
                String f_password = edt_password.getText().toString().trim();
                String f_password_conf = edt_conf_pass.getText().toString().trim();
                String f_designation = edt_designation.getText().toString().trim();
                String f_id_no = edt_id_no.getText().toString().trim();
                String f_department = alDepartment.get(spinner_department.getSelectedItemPosition());

                if(f_name.equals("") || f_email.equals("") || f_phone.equals("") || f_password.equals("") || f_password_conf.equals("") ||
                        f_designation.equals("") || f_id_no.equals("") || f_department.equals(""))
                    Toast.makeText(FacultyRegister.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if(!f_password.equals(f_password_conf))
                    Toast.makeText(FacultyRegister.this, "Password and Confirm password must be same", Toast.LENGTH_SHORT).show();
                else if(f_phone.length() != 10)
                    Toast.makeText(FacultyRegister.this, "Phone number must be 10 digit", Toast.LENGTH_SHORT).show();
                else if(f_password.length() < 6)
                    Toast.makeText(FacultyRegister.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(f_email).matches())
                    Toast.makeText(FacultyRegister.this, "Email ID must be in proper format", Toast.LENGTH_SHORT).show();
                else if(f_id_no.length() != 4)
                    Toast.makeText(FacultyRegister.this, "Faculty ID must be of 4 digit", Toast.LENGTH_SHORT).show();
                else
                    registerFaculty(f_name, f_gender, f_email, f_phone, f_password, f_designation, f_id_no, f_department);
            }
        });

        getAllDepartment();

    }

    private void registerFaculty(String f_name, String f_gender, String f_email, String f_phone, String f_password, String f_designation, String f_id_no, String f_department) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Faculty.FACULTY_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1"))
                        finish();
                    Toast.makeText(FacultyRegister.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(FacultyRegister.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("f_name", f_name);
                params.put("f_gender", f_gender);
                params.put("f_email", f_email);
                params.put("f_phone", f_phone);
                params.put("f_password", f_password);
                params.put("f_designation", f_designation);
                params.put("f_id_no", f_id_no);
                params.put("f_department", f_department);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    private void getAllDepartment() {
        alDepartment.clear();
        StringRequest request = new StringRequest(Keys.Common.GET_ALL_DEPARTMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++)
                            alDepartment.add(data.getString(i));

                        adapterDepartment = new ArrayAdapter<String>(FacultyRegister.this, android.R.layout.simple_spinner_item, alDepartment);
                        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_department.setAdapter(adapterDepartment);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(FacultyRegister.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().add(request);
    }


}