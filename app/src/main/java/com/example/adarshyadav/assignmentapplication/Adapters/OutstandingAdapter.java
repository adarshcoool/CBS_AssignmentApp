package com.example.adarshyadav.assignmentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adarshyadav.assignmentapplication.Pojo.OutStandingPojo;
import com.example.adarshyadav.assignmentapplication.R;

import java.util.ArrayList;

public class OutstandingAdapter extends BaseAdapter {

    TextView CustomerCode, CustomerName, Amount;
    private Context context;
    private ArrayList<OutStandingPojo> result;


    public OutstandingAdapter(Context ctx, ArrayList<OutStandingPojo> result) {
        super();
        this.context = ctx;
        this.result = result;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int pos, View convertView, final ViewGroup arg2) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_outstanding_adapter, arg2, false);

        CustomerCode = rowView.findViewById(R.id.tvCustomer_code);
        CustomerName = rowView.findViewById(R.id.tvCustomer_name);
        Amount = rowView.findViewById(R.id.tvAmount);

        CustomerCode.setText(result.get(pos).getCustomer_Code());
        CustomerName.setText(result.get(pos).getCustomer_Name());
        Amount.setText(result.get(pos).getOutStanding_Amount());

        return rowView;
    }
}

