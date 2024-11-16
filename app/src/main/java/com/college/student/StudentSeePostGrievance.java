package com.college.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.adapter.SeeGrievanceDetailsAdapter;
import com.college.grievancehandler.SeeGrievenceDetails;
import com.college.grievancemanagement.R;
import com.college.parents.ParentMenu;
import com.college.parents.SeePostGrievance;
import com.college.pojo.GrievenceDetail;
import com.college.pojo.SeeGrievenceDetail;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentSeePostGrievance extends AppCompatActivity {
    RecyclerView recycler_see_grievence_details;
    ArrayList<GrievenceDetail> seeGrievenceDetails=new ArrayList<>();
    StudentGrievanceDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_post_grievance);
        recycler_see_grievence_details=findViewById(R.id.recycler_see_grievence_details);
        getSupportActionBar().setTitle("See Grievance Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        /*seeGrievenceDetails.add(new GrievenceDetail("John Mathew","lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.","Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending","08-10-2021 10:24","Rahul Mathew","9874561230"));
        seeGrievenceDetails.add(new GrievenceDetail("John Mathew","lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.","Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending","08-10-2021 10:24","Rahul Mathew","9874561230"));
        seeGrievenceDetails.add(new GrievenceDetail("John Mathew","lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.","Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending","08-10-2021 10:24","Rahul Mathew","9874561230"));
        seeGrievenceDetails.add(new GrievenceDetail("John Mathew","lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.","Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending","08-10-2021 10:24","Rahul Mathew","9874561230"));
        seeGrievenceDetails.add(new GrievenceDetail("John Mathew","lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.","Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending","08-10-2021 10:24","Rahul Mathew","9874561230"));
        seeGrievenceDetails.add(new GrievenceDetail("John Mathew","lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.","Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending","08-10-2021 10:24","Rahul Mathew","9874561230"));*/
        LinearLayoutManager layoutManager=new LinearLayoutManager(StudentSeePostGrievance.this);
        recycler_see_grievence_details.setLayoutManager(layoutManager);
        recycler_see_grievence_details.setHasFixedSize(true);
        /*adapter=new StudentGrievanceDetailsAdapter(StudentSeePostGrievance.this,seeGrievenceDetails);
        recycler_see_grievence_details.setAdapter(adapter);*/

        getMyGrievence();

    }

    private void getMyGrievence() {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Common.GET_MY_GRIEVENCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++){
                            JSONObject j = data.getJSONObject(i);
                            GrievenceDetail g = new GrievenceDetail(j.getString("g_id"), j.getString("g_type"), j.getString("g_u_id"), j.getString("gs_id"),
                                    j.getString("g_name"), j.getString("g_phone"), j.getString("g_description"), j.getString("g_photo"), j.getString("g_reply_id"),
                                    j.getString("g_reply"), j.getString("g_time"));
                            seeGrievenceDetails.add(g);
                        }
                        adapter = new StudentGrievanceDetailsAdapter(StudentSeePostGrievance.this, seeGrievenceDetails);
                        recycler_see_grievence_details.setAdapter(adapter);
                    }
                    Toast.makeText(StudentSeePostGrievance.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(StudentSeePostGrievance.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("g_type", "1");
                params.put("g_u_id", SharedPreference.get("s_id"));
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(StudentSeePostGrievance.this, StudentMenu.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
class StudentGrievanceDetailsAdapter extends RecyclerView.Adapter<StudentGrievanceDetailsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GrievenceDetail> list;
    String value="0";


    public StudentGrievanceDetailsAdapter(Context context, ArrayList<GrievenceDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentGrievanceDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_grievance_details, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new StudentGrievanceDetailsAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentGrievanceDetailsAdapter.ViewHolder holder, int position) {
        GrievenceDetail seeGrievenceDetail =list.get(position);
        holder.txt_u_name.setText(seeGrievenceDetail.getG_name());
        holder.txt_subject.setText("Subject : "+seeGrievenceDetail.getGs_id());
        holder.txt_description.setText("Description : "+seeGrievenceDetail.getG_description());
        holder.txt_document.setText("Document : ");
        Picasso.get()
                .load(Keys.Common.DOCUMENT_PATH + seeGrievenceDetail.getG_photo())
                .into(holder.ivDocument);
        //holder.txt_status.setText("Status : "+seeGrievenceDetail.getG_reply_id());
        holder.txt_time.setText(seeGrievenceDetail.getG_time());
        holder.txt_reply.setVisibility(View.GONE);
        /*holder.layout_grievance_name.setVisibility(View.VISIBLE);
        holder.layout_grievance_phone.setVisibility(View.VISIBLE);*/
        holder.txt_g_name.setText(seeGrievenceDetail.getG_name());
        holder.txt_g_phone.setText(seeGrievenceDetail.getG_phone());

        if(seeGrievenceDetail.getG_reply_id().equals("0"))
            holder.txt_status.setText("Status : Pending");
        else{
            holder.txt_status.setText("Status : Solved");
            holder.txt_reply.setVisibility(View.GONE);
            holder.txt_grievence_reply.setVisibility(View.VISIBLE);
            holder.txt_grievence_reply.setText("Grievence Reply :- " + seeGrievenceDetail.getG_reply());
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_u_name,txt_subject,txt_description,txt_document,txt_status,txt_reply,txt_time,txt_g_name,txt_g_phone, txt_grievence_reply;
        CardView cd_grievance_details;
        LinearLayout layout_reply,layout_grievance_phone,layout_grievance_name;
        EditText edt_reply;
        Button btn_submit;
        ImageView ivDocument;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_u_name=itemView.findViewById(R.id.txt_u_name);
            txt_subject=itemView.findViewById(R.id.txt_subject);
            txt_description=itemView.findViewById(R.id.txt_description);
            txt_document=itemView.findViewById(R.id.txt_document);
            txt_status=itemView.findViewById(R.id.txt_status);
            txt_reply=itemView.findViewById(R.id.txt_reply);
            layout_reply=itemView.findViewById(R.id.layout_reply);
            edt_reply=itemView.findViewById(R.id.edt_reply);
            btn_submit=itemView.findViewById(R.id.btn_submit);
            cd_grievance_details=itemView.findViewById(R.id.cd_grievance_details);
            txt_time=itemView.findViewById(R.id.txt_time);
            layout_grievance_phone=itemView.findViewById(R.id.layout_grievance_phone);
            layout_grievance_name=itemView.findViewById(R.id.layout_grievance_name);
            txt_g_name=itemView.findViewById(R.id.txt_g_name);
            txt_g_phone=itemView.findViewById(R.id.txt_g_phone);
            txt_grievence_reply = itemView.findViewById(R.id.txt_grievence_reply);
            ivDocument = itemView.findViewById(R.id.imageViewDocument);
        }
    }
}