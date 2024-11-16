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
import com.college.adapter.ParentApproveAdapter;
import com.college.grievancemanagement.R;
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

public class SeeParentApproveFragment extends Fragment {
    RecyclerView recyclerView_parent;
    ArrayList<ParentApprove> parentApproves=new ArrayList<>();
    SeeParentApproveAdapter adapter;
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

        getApprovedParent();

        /*adapter=new SeeParentApproveAdapter(getContext(),parentApproves);
        recyclerView_parent.setAdapter(adapter);*/


        return view;
    }

    private void getApprovedParent() {
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
                        adapter=new SeeParentApproveAdapter(getContext(),parentApproves);
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
                params.put("p_status", "1");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }
}
class SeeParentApproveAdapter extends RecyclerView.Adapter<SeeParentApproveAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ParentApprove> list;


    public SeeParentApproveAdapter(Context context, ArrayList<ParentApprove> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SeeParentApproveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_approve_users, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new SeeParentApproveAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeParentApproveAdapter.ViewHolder holder, int position) {
        ParentApprove parentApprove=list.get(position);
        holder.txt_u_name.setText(parentApprove.getP_name());
        holder.txt_email.setText(parentApprove.getP_email());
        holder.txt_phone.setText(parentApprove.getP_phone());
        holder.txt_batch.setVisibility(View.VISIBLE);
        holder.txt_course.setVisibility(View.VISIBLE);
        holder.txt_admission_no.setVisibility(View.VISIBLE);
        holder.txt_relation_with_Student.setVisibility(View.VISIBLE);
        holder.txt_batch.setText("Batch : "+parentApprove.getB_id());
        holder.txt_course.setText("Course : "+parentApprove.getCd_id());
        holder.txt_admission_no.setText("Admission No : "+parentApprove.getP_admission_no());
        holder.txt_relation_with_Student.setText("Relationship With Student : "+parentApprove.getP_relation());
        holder.btn_approve.setVisibility(View.GONE);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_u_name,txt_email,txt_phone,txt_batch,txt_course,txt_admission_no,txt_relation_with_Student;
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
            txt_relation_with_Student=itemView.findViewById(R.id.txt_relation_with_Student);

        }
    }
}
