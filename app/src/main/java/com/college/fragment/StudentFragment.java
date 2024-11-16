package com.college.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.adapter.FacultyApproveAdapter;
import com.college.adapter.ParentApproveAdapter;
import com.college.adapter.StudentApproveAdapter;
import com.college.grievancemanagement.R;
import com.college.pojo.FacultyApprove;
import com.college.pojo.ParentApprove;
import com.college.pojo.StudentApprove;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentFragment extends Fragment {
    RecyclerView recyclerView_student;
    ArrayList<StudentApprove> studentApproves=new ArrayList<>();
    StudentApproveAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        recyclerView_student=view.findViewById(R.id.recycler_student_approval_users);
        /*studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));
        studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));
        studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));
        studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));
        studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));
        studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));
        studentApproves.add(new StudentApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","2021-22","MN1234"));*/
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView_student.setLayoutManager(layoutManager);
        recyclerView_student.setHasFixedSize(true);

        getPendingStudent();
        /*adapter=new StudentApproveAdapter(getContext(),studentApproves);
        recyclerView_student.setAdapter(adapter);*/
        return view;
    }

    private void getPendingStudent() {
        studentApproves.clear();
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.GET_ALL_STUDENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++){
                            JSONObject j = data.getJSONObject(i);
                            StudentApprove sa =    new StudentApprove(j.getString("s_id"), j.getString("s_name"),
                                    j.getString("cd_id"), j.getString("b_id"), j.getString("s_admission_no"), j.getString("s_email"),
                                    j.getString("s_phone"), j.getString("s_password"), j.getString("s_gender"), j.getString("s_status"), j.getString("s_time"));
                            studentApproves.add(sa);
                        }
                        adapter=new StudentApproveAdapter(getContext(),studentApproves, getActivity());
                        recyclerView_student.setAdapter(adapter);
                    }
                    Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("s_status", "0");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
