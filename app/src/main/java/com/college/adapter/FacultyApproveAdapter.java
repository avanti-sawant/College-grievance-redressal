package com.college.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.grievancemanagement.R;
import com.college.pojo.FacultyApprove;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacultyApproveAdapter extends RecyclerView.Adapter<FacultyApproveAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FacultyApprove> list;
    private Activity activity;

    public FacultyApproveAdapter(Activity activity, Context context, ArrayList<FacultyApprove> list) {
        this.activity = activity;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FacultyApproveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_approve_users, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new FacultyApproveAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyApproveAdapter.ViewHolder holder, int position) {
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

        holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click Button", Toast.LENGTH_SHORT).show();
                approveFaculty(facultyApprove.getF_id());
            }
        });
    }

    private void approveFaculty(String f_id) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Admin.APPROVE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        //Intent i = new Intent(getAc)
                        activity.finish();
                    }
                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
                        //finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", f_id);
                params.put("u_type", "3");
                return params;
            }
        };

        AppController.getInstance().add(request);
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
