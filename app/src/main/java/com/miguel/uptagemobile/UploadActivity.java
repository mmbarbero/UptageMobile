package com.miguel.uptagemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        EditText titleInput = findViewById(R.id.titleId);
        EditText descriptionInput = findViewById(R.id.decriptionId);
        EditText tagsInput = findViewById(R.id.tagsId);

    }



    public void submit(View view){



        RequestParams rp = new RequestParams();
        rp.add("videoTitle", "testtitle");
        rp.add("videoDescription","testDescription");
        rp.add("videoTags", "testtags");
        rp.add("videoCategory", "category");
        rp.add("username", "testauthor");
        rp.add("videoDate", "2018-04-27T15:39:08.631Z");
        rp.add("videoYCoord", "28");
        rp.add("videoXCoord", "40");
        rp.add("filename", "filename");


        HttpUtils.post("/file-upload/", rp, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.v("verbose", response.toString());
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                int code = statusCode;
                if (code == 403){
                    Toast.makeText(UploadActivity.this, "Sum Tim Wong", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(UploadActivity.this,statusCode, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
