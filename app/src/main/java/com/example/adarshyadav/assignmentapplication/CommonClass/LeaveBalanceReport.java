package com.example.adarshyadav.assignmentapplication.CommonClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adarshyadav.assignmentapplication.Activities.LoginActivity;
import com.example.adarshyadav.assignmentapplication.Adapters.LeaveBalanceAdapter;
import com.example.adarshyadav.assignmentapplication.Pojo.LeaveBalancePojo;
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

public class LeaveBalanceReport extends AppCompatActivity {

    ListView LeaveBalance;
    LeaveBalanceAdapter mAdapter;
    ArrayList mArrayList;
    TextView Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_balance_report);

        Logout = findViewById(R.id.logout);
        LeaveBalance = findViewById(R.id.leave_balance);
        String URL = "http://hbmas.cogniscient.in/HRLoginService/LoginService.svc/GetLeaveStatusDetail?AssoCode=DT1002";

        mArrayList = new ArrayList<NewJoiningPojo>();
        new ListAsyncTask().execute(URL);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
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

            type = new ProgressDialog(LeaveBalanceReport.this);
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
                    JSONObject GetLeaveStatusDetailResult = jsono.getJSONObject("LeaveStatusDetailResult");

                    JSONObject Message = GetLeaveStatusDetailResult.getJSONObject("GetLeaveStatusMessage");
                    error = Message.optString("ErrorMsg");
                    Success = Message.optString("Success");

                    JSONArray Result = GetLeaveStatusDetailResult.getJSONArray("LSDetails");
                    for (int i = 0; i < Result.length(); i++) {
                        if (Result.getJSONObject(i).getString("Leave_type").equals("PR")) {
                        } else {
                            LeaveBalancePojo op = new LeaveBalancePojo();

                            op.setAvailedLeave(Result.getJSONObject(i).getString("Availed"));
                            op.setBalanceLeave(Result.getJSONObject(i).getString("Balance"));
                            op.setLeaveType(Result.getJSONObject(i).getString("Leave_type"));

                            mArrayList.add(op);
                        }
                    }

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
                mAdapter = new LeaveBalanceAdapter(LeaveBalanceReport.this, mArrayList);
                LeaveBalance.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
