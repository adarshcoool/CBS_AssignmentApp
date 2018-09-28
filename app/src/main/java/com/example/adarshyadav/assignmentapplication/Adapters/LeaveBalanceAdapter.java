package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.LeaveBalancePojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.util.ArrayList;

public class LeaveBalanceAdapter extends BaseAdapter {

    TextView LeaveType, AvailedLeave, BalanceLeave;
    private Context context;
    private ArrayList<LeaveBalancePojo> result;

    public LeaveBalanceAdapter(Context ctx, ArrayList<LeaveBalancePojo> result) {
        super();
        this.context = ctx;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.size();
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
        View rowView = inflater.inflate(R.layout.activity_leave_balance_adapter, parent, false);

        LeaveType = rowView.findViewById(R.id.leave_type);
        AvailedLeave = rowView.findViewById(R.id.availed_leave);
        BalanceLeave = rowView.findViewById(R.id.balance_leave);

        if ((result.get(position).getLeaveType()).equals("CL")) {
            LeaveType.setText("Casual Leave");
        } else if ((result.get(position).getLeaveType()).equals("LWP")) {
            LeaveType.setText("Leave Without Pay");
        } else if ((result.get(position).getLeaveType()).equals("EL")) {
            LeaveType.setText("Earned Leave");
        } else if ((result.get(position).getLeaveType()).equals("SL")) {
            LeaveType.setText("Short Leave");
        } else if ((result.get(position).getLeaveType()).equals("ABS")) {
            LeaveType.setText("Absent");
        } else if ((result.get(position).getLeaveType()).equals("EWP")) {
            LeaveType.setText("Extra Work Paid");
        } else if ((result.get(position).getLeaveType()).equals("ABN")) {
            LeaveType.setText("Not Punch(Absent)");
        } else if ((result.get(position).getLeaveType()).equals("COFF")) {
            LeaveType.setText("Compensatory Leave");
        } else if ((result.get(position).getLeaveType()).equals("SPL")) {
            LeaveType.setText("Special Leave");
        }

        AvailedLeave.setText(result.get(position).getAvailedLeave());
        BalanceLeave.setText(result.get(position).getBalanceLeave());
        return rowView;
    }
}
