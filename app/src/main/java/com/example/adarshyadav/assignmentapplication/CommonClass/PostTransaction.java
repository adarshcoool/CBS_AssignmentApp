package com.example.adarshyadav.assignmentapplication.CommonClass;

import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adarshyadav.assignmentapplication.Adapters.PostTransactionAdapter;
import com.example.adarshyadav.assignmentapplication.Pojo.PostTransactionPojo;
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

public class PostTransaction extends AppCompatActivity {

    ListView postTransaction;
    PostTransactionAdapter mAdapter;
    ArrayList mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_transaction);

        postTransaction = findViewById(R.id.post_list_view);
        String URL = "http://hbmas.cogniscient.in/HRLoginService/LoginService.svc/GetLeaveAppDetail?AppCode=&EmpCode=DT1033";

        mArrayList = new ArrayList<PostTransactionPojo>();
        new ListAsyncTask().execute(URL);
    }

    class ListAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog type;
        String error;
        String Success;
        String appNo;
        String leaveType;
        String fromDate;
        String toDate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            type = new ProgressDialog(PostTransaction.this);
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
                    JSONObject GetLeaveAppDetailResult = jsono.getJSONObject("LeaveAppDetailResult");
                    JSONArray Result = GetLeaveAppDetailResult.getJSONArray("LADetails");
                    for (int i = 0; i < Result.length(); i++) {

                        PostTransactionPojo op = new PostTransactionPojo();

                        op.setApplicationNo(Result.getJSONObject(i).getString("Appl_No"));
                        op.setLeaveType(Result.getJSONObject(i).getString("Leave_type"));
                        op.setFromDate(Result.getJSONObject(i).getString("FROM_DATE"));
                        op.setToDate(Result.getJSONObject(i).getString("TO_DATE"));

                        mArrayList.add(op);
                    }

                    JSONObject Message = GetLeaveAppDetailResult.getJSONObject("LeaveAppMessage");
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
                mAdapter = new PostTransactionAdapter(PostTransaction.this, mArrayList);
                postTransaction.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
