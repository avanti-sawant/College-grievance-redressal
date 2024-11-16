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
import com.college.adapter.StudentApproveAdapter;
import com.college.grievancemanagement.R;
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

public class SeeStudentApproveFragment extends Fragment {
    RecyclerView recyclerView_student;
    ArrayList<StudentApprove> studentApproves=new ArrayList<>();
    SeeStudentApproveAdapter adapter;
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

        getApprovedStudent();

        /*adapter=new SeeStudentApproveAdapter(getContext(),studentApproves);
        recyclerView_student.setAdapter(adapter);*/
        return view;
    }

    private void getApprovedStudent() {
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
                        adapter=new SeeStudentApproveAdapter(getContext(),studentApproves);
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
                params.put("s_status", "1");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
class SeeStudentApproveAdapter extends RecyclerView.Adapter<SeeStudentApproveAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StudentApprove> list;


    public SeeStudentApproveAdapter(Context context, ArrayList<StudentApprove> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SeeStudentApproveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_approve_users, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new SeeStudentApproveAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeStudentApproveAdapter.ViewHolder holder, int position) {
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
        holder.btn_approve.setVisibility(View.GONE);

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