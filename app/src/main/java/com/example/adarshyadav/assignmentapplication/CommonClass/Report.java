package com.example.adarshyadav.assignmentapplication.CommonClass;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adarshyadav.assignmentapplication.Activities.LoginActivity;
import com.example.adarshyadav.assignmentapplication.Activities.MainActivity;
import com.example.adarshyadav.assignmentapplication.Adapters.CustomerOutstandingAdapter;
import com.example.adarshyadav.assignmentapplication.Pojo.CustomerOutStandingPojo;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Report extends AppCompatActivity {

    ListView listView;
    CustomerOutstandingAdapter mAdapter;
    ImageButton btnSearchButton;
    EditText etSearch;
    ArrayList mArrayList;
    int mYear, mMonth, mDay;
    SimpleDateFormat sdf1, sdf2;
    TextView logout;
    String LIST_URL;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        listView = findViewById(R.id.list_view);
        logout = findViewById(R.id.logout);
        btnSearchButton = findViewById(R.id.btn_search_button);
        etSearch = findViewById(R.id.et_searchDate);
        backButton = findViewById(R.id.back_button);

        btnSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList = new ArrayList<CustomerOutStandingPojo>();


                sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date date = sdf1.parse(etSearch.getText().toString().trim());
                    String a = sdf2.format(date);
                    LIST_URL = "http://103.75.33.98/ReportService/ReportServices.svc/GetCustomer?Company_NO=CBS&Location_no=NOIDA&AS_ON_DATE="
                            + a + "&FROM_CUSTOMER=&TO_CUSTOMER=";
                    new ListAsyncTask().execute(LIST_URL);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Report.this, LoginActivity.class);
                startActivity(i);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Report.this, MainActivity.class);
                startActivity(i);
            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentDate = Calendar.getInstance();
                mYear = mCurrentDate.get(Calendar.YEAR);
                mMonth = mCurrentDate.get(Calendar.MONTH);
                mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(Report.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedday, int selectedmonth, int selectedyear) {
                        // TODO Auto-generated method stub
                        Calendar c = Calendar.getInstance();
                        c.set(selectedday, selectedmonth, selectedyear);
                        sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                        etSearch.setText(pad(selectedyear) + "-" + pad(selectedmonth + 1) +
                                "-" + pad(selectedday));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.setTitle("Select start date");
                mDatePicker.show();
            }
        });

    }

    public String pad(int input) {

        String str = " ";
        if (input > 10 || input == 10) {
            str = Integer.toString(input);
        } else {
            str = "0" + Integer.toString(input);
        }
        return str;
    }

    class ListAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog type;
        String error;
        String Success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            type = new ProgressDialog(Report.this);
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
                    JSONObject GetCustomerDataResult = jsono.getJSONObject("GetCustomerDataResult");
                    JSONObject Message = GetCustomerDataResult.getJSONObject("Message");
                    error = Message.optString("ErrorMsg");
                    Success = Message.optString("Success");

                    JSONArray Result = GetCustomerDataResult.getJSONArray("Result");

                    for (int i = 0; i < Result.length(); i++) {

                        CustomerOutStandingPojo op = new CustomerOutStandingPojo();

                        op.setCustomer_Code(Result.getJSONObject(i).getString("Customer_Code"));
                        op.setCustomer_Name(Result.getJSONObject(i).getString("Customer_Name"));
                        op.setOutStanding_Amount(Result.getJSONObject(i).getString("OutStanding_Amount"));

                        mArrayList.add(op);
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

                mAdapter = new CustomerOutstandingAdapter(Report.this, mArrayList);
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
