package com.college.grievancehandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.adapter.SeeGrievanceDetailsAdapter;
import com.college.grievancemanagement.AdminMenu;
import com.college.grievancemanagement.Dashboard;
import com.college.grievancemanagement.R;
import com.college.grievancemanagement.ShowGrievance;
import com.college.pojo.SeeGrievenceDetail;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeeGrievenceDetails extends AppCompatActivity {

    RecyclerView recycler_see_grievence_details;
    ArrayList<SeeGrievenceDetail> seeGrievenceDetails = new ArrayList<>();
    SeeGrievanceDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_grievence_details);
        getSupportActionBar().setTitle("See Grievance Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        recycler_see_grievence_details = findViewById(R.id.recycler_see_grievence_details);

        /*seeGrievenceDetails.add(new SeeGrievenceDetail("John Mathew", "lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending", "08-10-2021 10:24"));
        seeGrievenceDetails.add(new SeeGrievenceDetail("John Mathew", "lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending", "08-10-2021 10:24"));
        seeGrievenceDetails.add(new SeeGrievenceDetail("John Mathew", "lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending", "08-10-2021 10:24"));
        seeGrievenceDetails.add(new SeeGrievenceDetail("John Mathew", "lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending", "08-10-2021 10:24"));
        seeGrievenceDetails.add(new SeeGrievenceDetail("John Mathew", "lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending", "08-10-2021 10:24"));
        seeGrievenceDetails.add(new SeeGrievenceDetail("John Mathew", "lorem ipsum",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                        "consequat.", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Pending", "08-10-2021 10:24"));*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(SeeGrievenceDetails.this);
        recycler_see_grievence_details.setLayoutManager(layoutManager);
        recycler_see_grievence_details.setHasFixedSize(true);

        getAllGrievence();

    }

    private void getAllGrievence() {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Handler.GET_ALL_GRIEVENCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.getString("success").equals("1")){
                        JSONArray data = json.getJSONArray("data");
                        for(int i=0; i<data.length(); i++){
                            JSONObject j = data.getJSONObject(i);
                            SeeGrievenceDetail s = new SeeGrievenceDetail(j.getString("g_id"), j.getString("g_type"), j.getString("g_u_id"), j.getString("gs_id"),
                                    j.getString("g_name"), j.getString("g_phone"), j.getString("g_description"), j.getString("g_photo"), j.getString("g_reply_id"),
                                    j.getString("g_reply"), j.getString("g_time"));
                            seeGrievenceDetails.add(s);
                        }
                        adapter = new SeeGrievanceDetailsAdapter(SeeGrievenceDetails.this, seeGrievenceDetails, SeeGrievenceDetails.this);
                        recycler_see_grievence_details.setAdapter(adapter);
                    }
                    Toast.makeText(SeeGrievenceDetails.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SeeGrievenceDetails.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.filter_option:
                return true;
            case R.id.subitem_student:
                Toast.makeText(this, "Student selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem_faculty:
                Toast.makeText(this, "Faculty selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem_parent:
                Toast.makeText(this, "Parent selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

}