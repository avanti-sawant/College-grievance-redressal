package com.college.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.grievancehandler.SeeGrievenceDetails;
import com.college.grievancehandler.SeeGrievencePhotoActivity;
import com.college.grievancemanagement.R;
import com.college.pojo.SeeGrievenceDetail;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeeGrievanceDetailsAdapter extends RecyclerView.Adapter<SeeGrievanceDetailsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SeeGrievenceDetail> list;
    String value="0";
    Activity activity;


    public SeeGrievanceDetailsAdapter(Context context, ArrayList<SeeGrievenceDetail> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SeeGrievanceDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_grievance_details, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new SeeGrievanceDetailsAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeGrievanceDetailsAdapter.ViewHolder holder, int position) {
        SeeGrievenceDetail seeGrievenceDetail =list.get(position);
        holder.txt_u_name.setText(seeGrievenceDetail.getG_name());
        holder.txt_subject.setText("Subject : "+seeGrievenceDetail.getGs_id());
        holder.txt_description.setText("Description : "+seeGrievenceDetail.getG_description());
        holder.txt_document.setText("Document : ");
        if(seeGrievenceDetail.getG_reply_id().equals("0"))
            holder.txt_status.setText("Status : Pending");
        else{
            holder.txt_status.setText("Status : Solved");
            holder.txt_reply.setVisibility(View.GONE);
            holder.txt_grievence_reply.setVisibility(View.VISIBLE);
            holder.txt_grievence_reply.setText("Grievence Reply :- " + seeGrievenceDetail.getG_reply());
        }

        holder.txt_g_type.setVisibility(View.VISIBLE);
        switch (seeGrievenceDetail.getG_type()){
            case "1": holder.txt_g_type.setText("Sent By :- Student"); break;
            case "2": holder.txt_g_type.setText("Sent By :- Parent"); break;
            case "3": holder.txt_g_type.setText("Sent By :- Faculty"); break;
        }

        holder.txt_time.setText(seeGrievenceDetail.getG_time());

        Picasso.get()
                .load(Keys.Common.DOCUMENT_PATH + seeGrievenceDetail.getG_photo())
                .into(holder.ivDocument);

        holder.ivDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, SeeGrievencePhotoActivity.class);
                i.putExtra("g_photo", seeGrievenceDetail.getG_photo());
                context.startActivity(i);
            }
        });

        holder.txt_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value.equals("0")){
                    value="1";
                    holder.layout_reply.setVisibility(View.VISIBLE);
                }else if (value.equals("1")){
                    value="0";
                    holder.layout_reply.setVisibility(View.GONE);
                }
            }
        });
        holder.btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String g_reply = holder.edt_reply.getText().toString().trim();
                if(g_reply.equals(""))
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else
                    replyGrievence(g_reply, seeGrievenceDetail.getG_id(), seeGrievenceDetail.getG_phone());
                //holder.layout_reply.setVisibility(View.GONE);
            }
        });

    }

    private void replyGrievence(String g_reply, String g_id, String g_phone) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Handler.REPLY_GRIEVENCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(g_phone, null, "Grievence Handler Replied to your grievence. Please check in the app.", null, null);

                        Intent i = new Intent(context, SeeGrievenceDetails.class);
                        context.startActivity(i);
                        activity.finish();
                    }
                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("g_id", g_id);
                params.put("g_reply", g_reply);
                params.put("g_reply_id", SharedPreference.get("gh_id"));
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

        TextView txt_u_name,txt_subject,txt_description,txt_document,txt_status,txt_reply,txt_time, txt_grievence_reply, txt_g_type;
        CardView cd_grievance_details;
        LinearLayout layout_reply;
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
            ivDocument = itemView.findViewById(R.id.imageViewDocument);
            txt_grievence_reply = itemView.findViewById(R.id.txt_grievence_reply);
            txt_g_type = itemView.findViewById(R.id.txt_g_type);
        }
    }
}
