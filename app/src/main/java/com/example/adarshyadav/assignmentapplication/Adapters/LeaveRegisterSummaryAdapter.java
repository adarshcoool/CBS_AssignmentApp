package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.LeaveRegisterSummaryPojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LeaveRegisterSummaryAdapter extends BaseAdapter {
    TextView SerialNo, LeaveType, FromDate, ToDate, ToSession, FromSession, TotalLeave;
    private Context context;
    private ArrayList<LeaveRegisterSummaryPojo> mArrayList;
    long difference;
    long elapsedDays;
    long value;

    public LeaveRegisterSummaryAdapter(Context ctx, ArrayList<LeaveRegisterSummaryPojo> mArrayList) {
        super();
        this.context = ctx;
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_leave_status_summary_adapter, parent, false);

        SerialNo = rowView.findViewById(R.id.serial_no);
        LeaveType = rowView.findViewById(R.id.leave_type);
        FromDate = rowView.findViewById(R.id.fromDate);
        FromSession = rowView.findViewById(R.id.fromSession);
        ToDate = rowView.findViewById(R.id.toDate);
        ToSession = rowView.findViewById(R.id.toSession);
        TotalLeave = rowView.findViewById(R.id.total_leaves);




        SerialNo.setText(mArrayList.get(position).getApplicationNo());

        if ((mArrayList.get(position).getLeaveType()).equals("CL")) {
            LeaveType.setText("Casual Leave");
        } else if ((mArrayList.get(position).getLeaveType()).equals("LWP")) {
            LeaveType.setText("Leave Without Pay");
        } else if ((mArrayList.get(position).getLeaveType()).equals("EL")) {
            LeaveType.setText("Earned Leave");
        } else if ((mArrayList.get(position).getLeaveType()).equals("SL")) {
            LeaveType.setText("Short Leave");
        } else if ((mArrayList.get(position).getLeaveType()).equals("ABN")) {
            LeaveType.setText("Absent");
        }
        FromDate.setText(mArrayList.get(position).getFromDate());
        ToDate.setText(mArrayList.get(position).getToDate());
        FromSession.setText(mArrayList.get(position).getFromSession());
        ToSession.setText(mArrayList.get(position).getToSession());

        String fromDate = mArrayList.get(position).getFromDate();
        String toDate = mArrayList.get(position).getToDate();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date1 = simpleDateFormat.parse(fromDate);
            Date date2 = simpleDateFormat.parse(toDate);

            difference = date2.getTime() - date1.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = (difference / daysInMilli);

            value = elapsedDays + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if ((mArrayList.get(position).getLeaveType()).equals("SL")) {
            TotalLeave.setText(String.valueOf(0.5));
        } else {
            TotalLeave.setText(String.valueOf(value));
        }
        return rowView;
    }

}
