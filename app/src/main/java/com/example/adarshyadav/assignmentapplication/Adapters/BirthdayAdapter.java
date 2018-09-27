package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.BirthdayPojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.util.ArrayList;

public class BirthdayAdapter extends BaseAdapter {
    TextView Name, Department, Date, Email, MobileNo;
    private Context context;
    private ArrayList<BirthdayPojo> result;


    public BirthdayAdapter(Context ctx, ArrayList<BirthdayPojo> result) {
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
        View rowView = inflater.inflate(R.layout.activity_birthday_adapter, parent, false);

        Name = rowView.findViewById(R.id.name);
        Department = rowView.findViewById(R.id.department);
        Date = rowView.findViewById(R.id.birthday_date);
        Email = rowView.findViewById(R.id.email);
        MobileNo = rowView.findViewById(R.id.mobile_number);

        Name.setText(result.get(position).getName());
        if ((result.get(position).getDepartment()).equals("C3")) {
            Department.setText("MiSAP");
        } else if ((result.get(position).getDepartment()).equals("C5")) {
            Department.setText("SAP");
        }
        Date.setText(result.get(position).getDate());
        Email.setText(result.get(position).getEmail());
        MobileNo.setText(result.get(position).getMobileNO());

        return rowView;
    }
}
