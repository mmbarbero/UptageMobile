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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {


    public void login(View view){

        final EditText usernameText = findViewById(R.id.usernameText);
        final EditText passwordText = findViewById(R.id.passwordText);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("username", username); rp.add("password",password);

        HttpUtils.post("/login/api/", rp, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra("json", response.toString());
                startActivity(intent);
                Log.v("verbose", response.toString());
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                int code = statusCode;
                if (code == 403){
                    Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                    resetCredentials(usernameText, passwordText);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Servers are down", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void resetCredentials(EditText text1, EditText text2){
        text1.setText("");
        text2.setText("");
    }

    public void callApi(View view){

        HttpUtils.get("/videos/api/", null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Log.v("verbose", "im here boi 1");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    Log.v("verbose", response.get(0).toString());
                }catch (JSONException e) {

                    Log.v("err", e.toString());
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}
