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
import com.college.grievancemanagement.R;
import com.college.pojo.FacultyApprove;
import com.college.pojo.ParentApprove;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParentFragment extends Fragment {
    RecyclerView recyclerView_parent;
    ArrayList<ParentApprove> parentApproves=new ArrayList<>();
    ParentApproveAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        recyclerView_parent=view.findViewById(R.id.recycler_parent_approval_users);
        /*parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));
        parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));
        parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));
        parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));
        parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));
        parentApproves.add(new ParentApprove("1","john","rahul","rahul@gmail.com","7894561230",
                "Father","IT","2021-22","MN12345"));*/
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView_parent.setLayoutManager(layoutManager);
        recyclerView_parent.setHasFixedSize(true);

        getPendingParent();
        /*adapter=new ParentApproveAdapter(getContext(),parentApproves);
        recyclerView_parent.setAdapter(adapter);*/


        return view;
    }

    private void getPendingParent() {
        parentApproves.clear();
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.GET_ALL_PARENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++){
                            JSONObject j = data.getJSONObject(i);
                            ParentApprove pa =    new ParentApprove(j.getString("p_id"), j.getString("p_name"),
                                    j.getString("p_email"), j.getString("p_phone"), j.getString("p_password"), j.getString("p_relation"),
                                    j.getString("p_admission_no"), j.getString("cd_id"), j.getString("b_id"),
                                    j.getString("s_name"), j.getString("p_status"), j.getString("p_time"));
                            parentApproves.add(pa);
                        }
                        adapter=new ParentApproveAdapter(getContext(),parentApproves, getActivity());
                        recyclerView_parent.setAdapter(adapter);
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
                params.put("p_status", "0");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
