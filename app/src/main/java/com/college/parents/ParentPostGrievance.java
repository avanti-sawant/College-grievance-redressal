package com.college.parents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.college.grievancemanagement.Dashboard;
import com.college.grievancemanagement.R;
import com.college.student.StudentPostGrievance;
import com.college.util.AppController;
import com.college.util.Keys;
import com.college.util.Loggers;
import com.college.util.SharedPreference;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParentPostGrievance extends AppCompatActivity {
    Button btn_post_grievence, btn_upload;
    ImageView ivDocument;
    EditText edt_desc;
    Spinner spinner_subject;

    ArrayList<String> alSubject = new ArrayList<String>();
    ArrayList<String> alId = new ArrayList<String>();

    ArrayAdapter<String> adapterSubject;
    PermissionListener permissionlistener;

    String path = "";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_post_grievance);
        getSupportActionBar().setTitle("Post Grievance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppController.initialize(getApplicationContext());
        SharedPreference.initialize(getApplicationContext());

        btn_post_grievence = findViewById(R.id.btn_post_grievence);
        btn_upload = findViewById(R.id.btn_upload);
        edt_desc = findViewById(R.id.edt_desc);
        spinner_subject = findViewById(R.id.spinner_subject);
        ivDocument = findViewById(R.id.imageViewDocument);

        dialog = new ProgressDialog(ParentPostGrievance.this);
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        getSubjects();

        btn_post_grievence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gs_id = alId.get(spinner_subject.getSelectedItemPosition());
                String g_description = edt_desc.getText().toString().trim();
                if (g_description.equals(""))
                    Toast.makeText(ParentPostGrievance.this, "Enter Description", Toast.LENGTH_SHORT).show();
                else if (path.equals(""))
                    Toast.makeText(ParentPostGrievance.this, "Please select image", Toast.LENGTH_SHORT).show();
                else
                    submitStudentGrievence(g_description, gs_id);

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedPermission.with(ParentPostGrievance.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();
            }
        });

        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ImagePicker.Companion.with(ParentPostGrievance.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(500)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .cameraOnly()
                        .start(181);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(ParentPostGrievance.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void submitStudentGrievence(String g_description, String gs_id) {
        String image = UUID.randomUUID().toString();
        UploadNotificationConfig config = new UploadNotificationConfig();
        config.getCompleted().autoClear = true;
        config.setTitleForAllStatuses("Uploading"); //TAKE VIDEO NAME FROM VIDEO PATH
        config.setIconForAllStatuses(R.mipmap.ic_launcher);

        dialog.show();
        try {
            new MultipartUploadRequest(getApplicationContext(), image, Keys.Common.POST_GRIEVENCE)
                    .addFileToUpload(path, "g_photo")
                    .addParameter("g_u_id", SharedPreference.get("p_id"))
                    .addParameter("g_type", "2")
                    .addParameter("gs_id", gs_id)
                    .addParameter("g_name", SharedPreference.get("p_name"))
                    .addParameter("g_phone", SharedPreference.get("p_phone"))
                    .addParameter("g_description", g_description)
                    .setMaxRetries(1)
                    .setNotificationConfig(config)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            Log.i("tag", "on progress upload");
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            dialog.dismiss();
                            exception.printStackTrace();
                            Toast.makeText(context, "Please try again later, poor internet connection", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            dialog.dismiss();
                            Log.i("tag", "on completed upload");
                            Log.i("tag", serverResponse.getBodyAsString());
                            try {
                                JSONObject json = new JSONObject(serverResponse.getBodyAsString());
                                if (json.getString("success").equals("1"))
                                    finish();
                                Toast.makeText(ParentPostGrievance.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            dialog.dismiss();
                            Loggers.i("Operation Cancelled");
                        }
                    })
                    .startUpload();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 181) {
                ivDocument.setVisibility(View.VISIBLE);
                path = ImagePicker.Companion.getFilePath(data);
                ivDocument.setImageURI(Uri.parse(path));
            }
        }
    }

    private void getSubjects() {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.Common.GET_GRIEVENCE_SUBJECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loggers.i(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getString("success").equals("1")) {
                        JSONArray data = json.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject j = data.getJSONObject(i);
                            alId.add(j.getString("gs_id"));
                            alSubject.add(j.getString("gs_title"));
                        }
                        adapterSubject = new ArrayAdapter<String>(ParentPostGrievance.this, android.R.layout.simple_spinner_item, alSubject);
                        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_subject.setAdapter(adapterSubject);
                    }
                    Toast.makeText(ParentPostGrievance.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ParentPostGrievance.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("u_type", "1");
                return params;
            }
        };

        AppController.getInstance().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}