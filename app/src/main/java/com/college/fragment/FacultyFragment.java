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
import com.college.grievancemanagement.R;
import com.college.pojo.FacultyApprove;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacultyFragment extends Fragment {
    RecyclerView recyclerView_faculty;
    ArrayList<FacultyApprove> facultyApproves=new ArrayList<>();
    FacultyApproveAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);
        recyclerView_faculty=view.findViewById(R.id.recycler_faculty_approval_users);
        /*facultyApproves.add(new FacultyApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","Professor","MN1234"));
        facultyApproves.add(new FacultyApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","Professor","MN1234"));
        facultyApproves.add(new FacultyApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","Professor","MN1234"));
        facultyApproves.add(new FacultyApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","Professor","MN1234"));
        facultyApproves.add(new FacultyApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","Professor","MN1234"));
        facultyApproves.add(new FacultyApprove("1","john","rahul@gmail.com","7894561230","male",
                "IT","Professor","MN1234"));*/
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView_faculty.setLayoutManager(layoutManager);
        recyclerView_faculty.setHasFixedSize(true);

        getPendingFaculty();

        /*adapter=new FacultyApproveAdapter(getContext(),facultyApproves);
        recyclerView_faculty.setAdapter(adapter);*/
        return view;
    }

    private void getPendingFaculty() {
        facultyApproves.clear();
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.GET_ALL_FACULTY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++){
                            JSONObject j = data.getJSONObject(i);
                            FacultyApprove fa =    new FacultyApprove(j.getString("f_id"), j.getString("f_name"),
                                    j.getString("f_gender"), j.getString("f_email"), j.getString("f_phone"), j.getString("f_password"),
                                    j.getString("f_designation"), j.getString("f_id_no"), j.getString("f_department"), j.getString("f_status"), j.getString("f_time"));
                            facultyApproves.add(fa);
                        }
                        adapter=new FacultyApproveAdapter(getActivity(), getContext(),facultyApproves);
                        recyclerView_faculty.setAdapter(adapter);
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
                params.put("f_status", "0");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
