package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.HolidayPojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.util.ArrayList;

public class HolidayAdapter extends BaseAdapter {
    TextView HolidayOccasion, HolidayDate, HolidayDay;


    private Context context;
    private ArrayList<HolidayPojo> result;


    public HolidayAdapter(Context ctx, ArrayList<HolidayPojo> result) {
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
        View rowView = inflater.inflate(R.layout.activity_holiday_adapter, parent, false);

        HolidayOccasion = rowView.findViewById(R.id.holiday_occasion);
        HolidayDate = rowView.findViewById(R.id.holiday_date);
        HolidayDay = rowView.findViewById(R.id.holiday_day);

        HolidayOccasion.setText(result.get(position).getHolidayOccasion());
        HolidayDate.setText(result.get(position).getHolidayDate());
        HolidayDay.setText(result.get(position).getHolidayDay());

        return rowView;
    }
}
