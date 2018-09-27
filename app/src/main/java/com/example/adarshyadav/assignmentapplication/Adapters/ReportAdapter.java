
package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.ReportPojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {

    TextView SerialNo, LeaveType, FromDate, ToDate, ToSession, FromSession;
    private Context context;
    private ArrayList<ReportPojo> mArrayList;

    public ReportAdapter(Context ctx, ArrayList<ReportPojo> mArrayList) {
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
        View rowView = inflater.inflate(R.layout.activity_report_adapter, parent, false);

        SerialNo = rowView.findViewById(R.id.serial_no);
        LeaveType = rowView.findViewById(R.id.leave_type);
        FromDate = rowView.findViewById(R.id.fromDate);
        FromSession = rowView.findViewById(R.id.fromSession);
        ToDate = rowView.findViewById(R.id.toDate);
        ToSession = rowView.findViewById(R.id.toSession);


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

        return rowView;
    }
}
