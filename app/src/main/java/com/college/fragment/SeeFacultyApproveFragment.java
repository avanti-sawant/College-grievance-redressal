package com.college.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class SeeFacultyApproveFragment extends Fragment {
    RecyclerView recyclerView_faculty;
    ArrayList<FacultyApprove> facultyApproves=new ArrayList<>();
    SeeFacultyApproveAdapter adapter;
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

        getApprovedFaculty();
        /*adapter=new SeeFacultyApproveAdapter(getContext(),facultyApproves);
        recyclerView_faculty.setAdapter(adapter);*/
        return view;
    }

    private void getApprovedFaculty() {
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
                        adapter=new SeeFacultyApproveAdapter(getContext(),facultyApproves);
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
                params.put("f_status", "1");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
class SeeFacultyApproveAdapter extends RecyclerView.Adapter<SeeFacultyApproveAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FacultyApprove> list;


    public SeeFacultyApproveAdapter(Context context, ArrayList<FacultyApprove> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SeeFacultyApproveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_approve_users, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new SeeFacultyApproveAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeFacultyApproveAdapter.ViewHolder holder, int position) {
        FacultyApprove facultyApprove=list.get(position);
        holder.txt_u_name.setText(facultyApprove.getF_name());
        holder.txt_email.setText(facultyApprove.getF_email());
        holder.txt_phone.setText(facultyApprove.getF_phone());
        holder.txt_gender.setVisibility(View.VISIBLE);
        holder.txt_department.setVisibility(View.VISIBLE);
        holder.txt_designation.setVisibility(View.VISIBLE);
        holder.txt_id_no.setVisibility(View.VISIBLE);
        holder.txt_gender.setText("Gender : "+facultyApprove.getF_gender());
        holder.txt_department.setText("Department : "+facultyApprove.getF_department());
        holder.txt_designation.setText("Designation : "+facultyApprove.getF_designation());
        holder.txt_id_no.setText("I'd No : "+facultyApprove.getF_id_no());
        holder.btn_approve.setVisibility(View.GONE);


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_u_name,txt_email,txt_phone,txt_gender,txt_department,txt_designation,txt_id_no;
        Button btn_approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_u_name=itemView.findViewById(R.id.txt_u_name);
            txt_email=itemView.findViewById(R.id.txt_email);
            txt_phone=itemView.findViewById(R.id.txt_phone);
            txt_gender=itemView.findViewById(R.id.txt_gender);
            txt_department=itemView.findViewById(R.id.txt_department);
            txt_designation=itemView.findViewById(R.id.txt_designation);
            btn_approve=itemView.findViewById(R.id.btn_approve);
            txt_id_no=itemView.findViewById(R.id.txt_id_no);

        }
    }
}
