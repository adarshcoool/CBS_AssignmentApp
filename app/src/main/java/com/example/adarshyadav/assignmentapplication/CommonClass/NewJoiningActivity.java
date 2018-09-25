package com.example.adarshyadav.assignmentapplication.CommonClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adarshyadav.assignmentapplication.Activities.LoginActivity;
import com.example.adarshyadav.assignmentapplication.Activities.MainActivity;
import com.example.adarshyadav.assignmentapplication.Adapters.NewJoiningAdapter;
import com.example.adarshyadav.assignmentapplication.Pojo.NewJoiningPojo;
import com.example.adarshyadav.assignmentapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NewJoiningActivity extends AppCompatActivity {

    ListView joiningListView;
    NewJoiningAdapter mAdapter;
    ArrayList mArrayList;
    TextView Logout;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_joining);
        Logout = findViewById(R.id.logout);

        joiningListView = findViewById(R.id.new_joining);
        backButton = findViewById(R.id.back_button);
        String URL = "http://hbmas.cogniscient.in/HRLoginService/LoginService.svc/GetNewJoiningDetail?LoginName=";

        mArrayList = new ArrayList<NewJoiningPojo>();
        new ListAsyncTask().execute(URL);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewJoiningActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    class ListAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog type;
        String error;
        String Success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            type = new ProgressDialog(NewJoiningActivity.this);
            type.setMessage("Please wait");
            type.show();
            type.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONObject GetJoiningDetailResult = jsono.getJSONObject("NewJoiningDetailResult");
                    JSONArray Result = GetJoiningDetailResult.getJSONArray("NJDetails");
                    for (int i = 0; i < Result.length(); i++) {

                        NewJoiningPojo op = new NewJoiningPojo();

                        op.setDepartment(Result.getJSONObject(i).getString("DEPARTMENT"));
                        op.setDate(Result.getJSONObject(i).getString("Date"));
                        op.setEmail(Result.getJSONObject(i).getString("EMAIL"));
                        op.setMobileNo(Result.getJSONObject(i).getString("MOBILE_NO"));
                        op.setName(Result.getJSONObject(i).getString("NAME"));

                        mArrayList.add(op);
                    }

                    JSONObject Message = GetJoiningDetailResult.getJSONObject("NewJoiningsMsgMessage");
                    error = Message.optString("ErrorMsg");
                    Success = Message.optString("Success");

                    return true;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            type.cancel();
            if (result == true && Success.equals("true")) {
                Collections.reverse(mArrayList);
                mAdapter = new NewJoiningAdapter(NewJoiningActivity.this, mArrayList);
                joiningListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
