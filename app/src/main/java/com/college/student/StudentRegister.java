package com.college.student;

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
import com.college.pojo.Batch;
import com.college.pojo.CourseDepartment;
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
import java.util.regex.Pattern;

public class StudentRegister extends AppCompatActivity {
    EditText edt_name,edt_email,edt_phone,edt_password,edt_conf_pass,edt_admission_no;
    Spinner spinner_course,spinner_batch;
    RadioGroup rd_group;
    RadioButton rd_btn1,rd_btn2;
    Button btn_register;

    ArrayList<Batch> alBatch = new ArrayList<Batch>();
    ArrayList<String> alBName = new ArrayList<String>();
    ArrayList<CourseDepartment> alCd = new ArrayList<CourseDepartment>();
    ArrayList<String> alCName = new ArrayList<String>();

    ArrayAdapter<String> adapterBatch;
    ArrayAdapter<String> adapterCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());
        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.edt_email);
        edt_phone=findViewById(R.id.edt_phone);
        edt_password=findViewById(R.id.edt_password);
        edt_conf_pass=findViewById(R.id.edt_conf_pass);
        spinner_batch=findViewById(R.id.spinner_batch);
        edt_admission_no=findViewById(R.id.edt_admission_no);
        spinner_course=findViewById(R.id.spinner_course);
        rd_group=findViewById(R.id.rd_group);
        rd_btn1=findViewById(R.id.rd_btn1);
        rd_btn2=findViewById(R.id.rd_btn2);
        btn_register=findViewById(R.id.btn_register);

        getAllCourseBatch();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_name = edt_name.getText().toString().trim();
                CourseDepartment cd = alCd.get(spinner_course.getSelectedItemPosition());
                int cd_id = cd.getCd_id();
                Batch b = alBatch.get(spinner_batch.getSelectedItemPosition());
                int b_id = b.getB_id();
                String s_admission_no = edt_admission_no.getText().toString().trim();
                String s_email = edt_email.getText().toString().trim();
                String s_phone = edt_phone.getText().toString().trim();
                String s_password = edt_password.getText().toString().trim();
                String s_password_conf = edt_conf_pass.getText().toString().trim();
                String s_gender = "";
                if(rd_btn1.isChecked())
                    s_gender = "1";
                if(rd_btn2.isChecked())
                    s_gender = "2";

                if(s_name.equals("") || s_admission_no.equals("") || s_email.equals("") || s_phone.equals("") || s_password.equals("") || s_password_conf.equals(""))
                    Toast.makeText(StudentRegister.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if(!s_password.equals(s_password_conf))
                    Toast.makeText(StudentRegister.this, "Password and Confirm password not matching", Toast.LENGTH_SHORT).show();
                else if(s_phone.length() != 10)
                    Toast.makeText(StudentRegister.this, "Phone number must be 10 digit", Toast.LENGTH_SHORT).show();
                else if(s_password.length() < 6)
                    Toast.makeText(StudentRegister.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(s_email).matches())
                    Toast.makeText(StudentRegister.this, "Email ID must be in proper format", Toast.LENGTH_SHORT).show();
                else if(s_admission_no.length() != 8)
                    Toast.makeText(StudentRegister.this, "Admission Number must be of 8 characters.", Toast.LENGTH_SHORT).show();
                else if(!isValidAdmissionNo(s_admission_no))
                    Toast.makeText(StudentRegister.this, "Please enter valid admission no", Toast.LENGTH_SHORT).show();
                else
                    studentRegister(s_name, cd_id, b_id, s_admission_no, s_email, s_phone, s_password, s_gender);
            }
        });
    }

    private boolean isValidAdmissionNo(String s) {
        if(Character.isAlphabetic(s.charAt(0)) && Character.isAlphabetic(s.charAt(1)) && Character.isAlphabetic(s.charAt(2))){
            if(Character.isDigit(s.charAt(3)) && Character.isDigit(s.charAt(4)) && Character.isDigit(s.charAt(5))
                    && Character.isDigit(s.charAt(6)) && Character.isDigit(s.charAt(7)))
                return true;
            else
                return false;
        }else
            return false;
    }

    private void studentRegister(String s_name, int cd_id, int b_id, String s_admission_no, String s_email, String s_phone, String s_password, String s_gender) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Student.STUDENT_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1"))
                        finish();
                    Toast.makeText(StudentRegister.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentRegister.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("s_name", s_name);
                params.put("cd_id", String.valueOf(cd_id));
                params.put("b_id", String.valueOf(b_id));
                params.put("s_admission_no", s_admission_no);
                params.put("s_email", s_email);
                params.put("s_phone", s_phone);
                params.put("s_password", s_password);
                params.put("s_gender", s_gender);
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    private void getAllCourseBatch() {
        alBatch.clear();
        alBName.clear();
        alCd.clear();
        alCName.clear();

        StringRequest request = new StringRequest(Keys.Common.GET_ALL_BATCH_COURSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject j = new JSONObject(response);
                    if(j.getString("success").equals("1")){
                        JSONObject data = j.getJSONObject("data");
                        //Load Batches
                        JSONArray batches = data.getJSONArray("batch");
                        for(int i=0; i<batches.length(); i++){
                            JSONObject b = batches.getJSONObject(i);
                            alBatch.add(new Batch(b.getInt("b_id"), b.getString("b_name"), b.getInt("b_status")));
                            alBName.add(b.getString("b_name"));
                        }
                        adapterBatch = new ArrayAdapter<String>(StudentRegister.this, android.R.layout.simple_spinner_item, alBName);
                        adapterBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_batch.setAdapter(adapterBatch);

                        //Load Course
                        JSONArray courses = data.getJSONArray("course_department");
                        for(int i=0; i<courses.length(); i++){
                            JSONObject c = courses.getJSONObject(i);
                            alCd.add(new CourseDepartment(c.getInt("cd_id"), c.getString("cd_department"), c.getString("cd_course"), c.getInt("cd_status")));
                            alCName.add(c.getString("cd_course"));
                        }
                        adapterCourse = new ArrayAdapter<String>(StudentRegister.this, android.R.layout.simple_spinner_item, alCName);
                        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_course.setAdapter(adapterCourse);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentRegister.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().add(request);
    }
}