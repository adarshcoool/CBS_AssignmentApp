package com.example.adarshyadav.assignmentapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.adarshyadav.assignmentapplication.CommonClass.GetTransaction;
import com.example.adarshyadav.assignmentapplication.CommonClass.LeaveActivity;
import com.example.adarshyadav.assignmentapplication.R;

public class Dashboard extends AppCompatActivity {

    LinearLayout getTransaction, postTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        getTransaction = findViewById(R.id.get_transaction);
        postTransaction = findViewById(R.id.post_transaction);

        getTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, GetTransaction.class);
                startActivity(i);
            }
        });

        postTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, LeaveActivity.class);
                startActivity(i);
            }
        });

    }
}
