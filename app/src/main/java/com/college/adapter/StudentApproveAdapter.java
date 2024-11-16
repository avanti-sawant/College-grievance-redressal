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
import com.college.pojo.ParentApprove;
import com.college.pojo.StudentApprove;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentApproveAdapter extends RecyclerView.Adapter<StudentApproveAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StudentApprove> list;
    private Activity activity;


    public StudentApproveAdapter(Context context, ArrayList<StudentApprove> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StudentApproveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_approve_users, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new StudentApproveAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentApproveAdapter.ViewHolder holder, int position) {
        StudentApprove studentApprove=list.get(position);
        holder.txt_u_name.setText(studentApprove.getS_name());
        holder.txt_email.setText(studentApprove.getS_email());
        holder.txt_phone.setText(studentApprove.getS_phone());
        holder.txt_batch.setVisibility(View.VISIBLE);
        holder.txt_course.setVisibility(View.VISIBLE);
        holder.txt_admission_no.setVisibility(View.VISIBLE);
        holder.txt_gender.setVisibility(View.VISIBLE);
        holder.txt_batch.setText("Batch No : "+studentApprove.getB_id());
        holder.txt_course.setText("Course : "+studentApprove.getCd_id());
        holder.txt_admission_no.setText("Admission No : "+studentApprove.getS_admission_no());
        holder.txt_gender.setText("Gender : "+studentApprove.getS_gender());

        holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Click Button", Toast.LENGTH_SHORT).show();
                approveStudent(studentApprove.getS_id());
            }
        });


    }

    private void approveStudent(String s_id) {
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
                params.put("id", s_id);
                params.put("u_type", "1");
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

        TextView txt_u_name,txt_email,txt_phone,txt_batch,txt_course,txt_admission_no,txt_gender;
        Button btn_approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_u_name=itemView.findViewById(R.id.txt_u_name);
            txt_email=itemView.findViewById(R.id.txt_email);
            txt_phone=itemView.findViewById(R.id.txt_phone);
            txt_batch=itemView.findViewById(R.id.txt_batch);
            txt_course=itemView.findViewById(R.id.txt_course);
            txt_admission_no=itemView.findViewById(R.id.txt_admission_no);
            btn_approve=itemView.findViewById(R.id.btn_approve);
            txt_gender=itemView.findViewById(R.id.txt_gender);

        }
    }
}
