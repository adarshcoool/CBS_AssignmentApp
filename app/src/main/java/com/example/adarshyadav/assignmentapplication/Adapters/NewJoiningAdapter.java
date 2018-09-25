package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.NewJoiningPojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.util.ArrayList;

public class NewJoiningAdapter extends BaseAdapter {
    TextView Name, Department, Date, Email, MobileNo;
    private Context context;
    private ArrayList<NewJoiningPojo> result;

    public NewJoiningAdapter(Context ctx, ArrayList<NewJoiningPojo> result) {
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
        View rowView = inflater.inflate(R.layout.activity_new_joining_adapter, parent, false);

        Name = rowView.findViewById(R.id.joinee_name);
        Department = rowView.findViewById(R.id.joinee_department);
        Date = rowView.findViewById(R.id.joining_date);
        Email = rowView.findViewById(R.id.joinee_email);
        MobileNo = rowView.findViewById(R.id.joinee_mobile_number);

        Name.setText(result.get(position).getName());
        Department.setText(result.get(position).getDepartment());
        Date.setText(result.get(position).getDate());
        Email.setText(result.get(position).getEmail());
        MobileNo.setText(result.get(position).getMobileNo());

        return rowView;
    }
}
