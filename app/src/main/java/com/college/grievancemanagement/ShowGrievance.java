package com.college.grievancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.adapter.GrievanceHandlerAdapter;
import com.college.pojo.GrievanceHandler;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowGrievance extends AppCompatActivity {

    RecyclerView recycler_show_grievance;
    ArrayList<GrievanceHandler> grievanceHandlers;
    GrievanceHandlerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grievance);
        getSupportActionBar().setTitle("See Grievance Handler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_show_grievance=findViewById(R.id.recycler_show_grievance);
        grievanceHandlers=new ArrayList<>();
        /*grievanceHandlers.add(new GrievanceHandler("1","John Mathew","john@gmail.com","9874561230"));
        grievanceHandlers.add(new GrievanceHandler("2","Rahul Mathew","rahul@gmail.com","8974561230"));
        grievanceHandlers.add(new GrievanceHandler("3","John Mathew","john@gmail.com","7894561230"));
        grievanceHandlers.add(new GrievanceHandler("4","Shyam Mathew","shyam@gmail.com","9874561230"));
        grievanceHandlers.add(new GrievanceHandler("5","John Mathew","john@gmail.com","9630258741"));*/
        LinearLayoutManager layoutManager=new LinearLayoutManager(ShowGrievance.this);
        recycler_show_grievance.setLayoutManager(layoutManager);
        recycler_show_grievance.setHasFixedSize(true);


        getAllHandler();

    }

    private void getAllHandler() {
        StringRequest request = new StringRequest(Keys.Admin.GET_ALL_HANDLER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++){
                            JSONObject j = data.getJSONObject(i);
                            GrievanceHandler gh = new GrievanceHandler(j.getString("gh_id"), j.getString("gh_name"), j.getString("gh_email"), j.getString("gh_phone"));
                            grievanceHandlers.add(gh);
                        }
                        adapter=new GrievanceHandlerAdapter(ShowGrievance.this,grievanceHandlers);
                        recycler_show_grievance.setAdapter(adapter);
                    }
                    Toast.makeText(ShowGrievance.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ShowGrievance.this, "Chekc internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(ShowGrievance.this,AdminMenu.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}