package com.college.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.college.grievancemanagement.R;
import com.college.pojo.GrievanceHandler;

import java.util.ArrayList;

public class GrievanceHandlerAdapter extends RecyclerView.Adapter<GrievanceHandlerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GrievanceHandler> list;


    public GrievanceHandlerAdapter(Context context, ArrayList<GrievanceHandler> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GrievanceHandlerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.custom_grievance_handler, parent, false);
        listItem.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new GrievanceHandlerAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GrievanceHandlerAdapter.ViewHolder holder, int position) {
        GrievanceHandler grievanceHandler=list.get(position);
        holder.txt_name.setText(grievanceHandler.getG_name());
        holder.txt_email.setText(grievanceHandler.getG_email());
        holder.txt_phone.setText(grievanceHandler.getG_phone());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name,txt_email,txt_phone;
        CardView cd_grievance_handler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_email=itemView.findViewById(R.id.txt_email);
            txt_phone=itemView.findViewById(R.id.txt_phone);
            cd_grievance_handler=itemView.findViewById(R.id.cd_grievance_handler);

        }
    }
}
