package com.example.adarshyadav.assignmentapplication.CommonClass;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adarshyadav.assignmentapplication.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LeaveActivity extends AppCompatActivity {

    int mYear, mMonth, mDay;
    SimpleDateFormat sdf;
    Spinner LeaveType, SessionFromDate, SessionToDate;
    Button ApplyButton;
    EditText fromDate, toDate, etReason;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    SaveDataAsyncTask mSaveDataAsyncTask;

    String[] leaveType = {"Casual Leave", "Leave Without Pay", "Absent", "Earned Leave", "Short Leave"};
    String lType, sType;
    String errorMsg, Success;
    String[] session = {"Whole Day", "First Session", "Second Session"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        LeaveType = findViewById(R.id.leave_spinner);
        SessionFromDate = findViewById(R.id.fromDate_spinner);
        SessionToDate = findViewById(R.id.toDate_spinner);
        ApplyButton = findViewById(R.id.apply_button);
        toDate = findViewById(R.id.et_toDate);
        toDate.setFocusable(false);
        fromDate = findViewById(R.id.et_fromDate);
        fromDate.setFocusable(false);
        etReason = findViewById(R.id.et_Reason);

        //Creating the ArrayAdapter instance having the spinner list
        ArrayAdapter leaveAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leaveType);
        leaveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the SpinnerResponseText
        LeaveType.setAdapter(leaveAdapter);

        ArrayAdapter fromDateAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, session);
        fromDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SessionFromDate.setAdapter(fromDateAdapter);

        ArrayAdapter toDateAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, session);
        toDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SessionToDate.setAdapter(toDateAdapter);

        ApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LeaveType.getSelectedItem().toString().equals("Casual Leave")) {
                    lType = "CL";
                }

                if (LeaveType.getSelectedItem().toString().equals("Leave Without Pay")) {
                    lType = "LWP";
                }

                if (LeaveType.getSelectedItem().toString().equals("Absent")) {
                    lType = "ABN";
                }

                if (LeaveType.getSelectedItem().toString().equals("Earned Leave")) {
                    lType = "EL";
                }

                if (LeaveType.getSelectedItem().toString().equals("Short Leave")) {
                    lType = "SL";
                }

                if (SessionFromDate.getSelectedItem().toString().equals("Whole Day")) {
                    sType = "W";
                }

                if (SessionFromDate.getSelectedItem().toString().equals("First Session")) {
                    sType = "F";
                }

                if (SessionFromDate.getSelectedItem().toString().equals("Second Session")) {
                    sType = "A";
                }

                if (SessionToDate.getSelectedItem().toString().equals("Whole Day")) {
                    sType = "W";
                }

                if (SessionToDate.getSelectedItem().toString().equals("First Session")) {
                    sType = "F";
                }

                if (SessionToDate.getSelectedItem().toString().equals("Second Session")) {
                    sType = "A";
                }

                mSaveDataAsyncTask = new SaveDataAsyncTask();
                mSaveDataAsyncTask.execute();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentDate = Calendar.getInstance();
                mYear = mCurrentDate.get(Calendar.YEAR);
                mMonth = mCurrentDate.get(Calendar.MONTH);
                mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        Calendar c = Calendar.getInstance();
                        c.set(selectedday, selectedmonth, selectedyear);
                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                        fromDate.setText(pad(selectedday) + "-" + pad(selectedmonth + 1) +
                                "-" + pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.setTitle("Select start date");
                mDatePicker.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentDate = Calendar.getInstance();
                mYear = mCurrentDate.get(Calendar.YEAR);
                mMonth = mCurrentDate.get(Calendar.MONTH);
                mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        Calendar c = Calendar.getInstance();
                        c.set(selectedday, selectedmonth, selectedyear);
                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                        toDate.setText(pad(selectedday) + "-" + pad(selectedmonth + 1) +
                                "-" + pad(selectedyear));
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

    public void postLeaveActivity() {
        String URL_DETAIL;
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("COMPANY_NO", "COGNI");
                jsonObject.put("LOCATION_NO", "NOIDA");
                jsonObject.put("emp_code", "DT1033");
                jsonObject.put("App_code", "");
                jsonObject.put("Leave_type", lType);
                jsonObject.put("Notified_date", date);
                jsonObject.put("From_date", fromDate.getText().toString().trim());
                jsonObject.put("To_date", toDate.getText().toString().trim());
                jsonObject.put("Calender_Code", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
                jsonObject.put("From_session", sType);
                jsonObject.put("To_session", sType);
                jsonObject.put("employee_reason", etReason.getText().toString().trim());
                jsonObject.put("employer_reason", "");
                jsonObject.put("Status", "0");//m-0, c-1 , a-2
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String jsonStr = jsonObject.toString();
            System.out.println("jsonString: " + jsonStr);

            URL_DETAIL = "http://hbmas.cogniscient.in/" + "HRLoginService/LoginService.svc/SetLeaveDataADD";
            getResponse(jsonStr, URL_DETAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getResponse(String data, String url) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            StringEntity params = new StringEntity(data, "UTF-8");

            post.setHeader("Content-Type", "application/JSON");
            post.setEntity(params);
            HttpResponse response;
            response = httpClient.execute(post);

            StringBuffer stringBuffer = new StringBuffer("");
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }

            String responseText = stringBuffer.toString();
            System.out.println("responseText :" + responseText);
            JSONObject jsono = new JSONObject(responseText);
            JSONObject Messsage = jsono.getJSONObject("LeaveResult");
            errorMsg = Messsage.optString("ErrorMsg");
            Success = Messsage.optString("Success");
            Log.e("hii", errorMsg + "-" + Success);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    private class SaveDataAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog mDialog;
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            postLeaveActivity();
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
//            mDialog.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
            if (errorMsg.contains("Leave Application Add Succefully")) {
                Intent i = new Intent(LeaveActivity.this, Transaction.class);
                startActivity(i);
            }
        }
    }


}
