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
import com.example.adarshyadav.assignmentapplication.Adapters.HolidayAdapter;
import com.example.adarshyadav.assignmentapplication.Pojo.HolidayPojo;
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

public class HolidayActivity extends AppCompatActivity {

    ListView holidayListview;
    HolidayAdapter mAdapter;
    ArrayList mArrayList;
    TextView Logout;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        holidayListview = findViewById(R.id.holiday_list_view);
        backButton = findViewById(R.id.back_button);
        Logout = findViewById(R.id.logout);
        String URL = "http://hbmas.cogniscient.in/HRLoginService/LoginService.svc/GetHolidayDatail?YEAR=2018&CALENDER_CODE=2018";

        mArrayList = new ArrayList<HolidayPojo>();
        new ListAsyncTask().execute(URL);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HolidayActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

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

            type = new ProgressDialog(HolidayActivity.this);
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
                    JSONObject GetHolidayDetailResult = jsono.getJSONObject("HolidayDatailResult");
                    JSONArray Result = GetHolidayDetailResult.getJSONArray("HDDetails");
                    for (int i = 0; i < Result.length(); i++) {

                        HolidayPojo op = new HolidayPojo();

                        op.setCalenderCode(Result.getJSONObject(i).getString("Calender_Code"));
                        op.setCalenderName(Result.getJSONObject(i).getString("Calender_Name"));
                        op.setHolidayDate(Result.getJSONObject(i).getString("Holiday_Date"));
                        op.setHolidayDay(Result.getJSONObject(i).getString("Holiday_Day"));
                        op.setHolidayOccasion(Result.getJSONObject(i).getString("Holiday_Occasion"));
                        op.setYear(Result.getJSONObject(i).getString("YEAR"));

                        mArrayList.add(op);
                    }

                    JSONObject Message = GetHolidayDetailResult.getJSONObject("HolidayMessage");
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
                mAdapter = new HolidayAdapter(HolidayActivity.this, mArrayList);
                holidayListview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
