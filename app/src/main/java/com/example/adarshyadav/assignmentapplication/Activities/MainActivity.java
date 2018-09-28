package com.example.adarshyadav.assignmentapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.adarshyadav.assignmentapplication.CommonClass.AboutUs;
import com.example.adarshyadav.assignmentapplication.CommonClass.BirthdayActivity;
import com.example.adarshyadav.assignmentapplication.CommonClass.ContactUs;
import com.example.adarshyadav.assignmentapplication.CommonClass.HolidayActivity;
import com.example.adarshyadav.assignmentapplication.CommonClass.LeaveActivity;
import com.example.adarshyadav.assignmentapplication.CommonClass.LeaveBalanceReport;
import com.example.adarshyadav.assignmentapplication.CommonClass.LeaveRegisterReport;
import com.example.adarshyadav.assignmentapplication.CommonClass.NewJoiningActivity;
import com.example.adarshyadav.assignmentapplication.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout Leave, Report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Leave = findViewById(R.id.get_transaction);
        Report = findViewById(R.id.post_transaction);


        Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaveRegisterReport.class);
                startActivity(i);
            }
        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaveActivity.class);
                startActivity(i);
            }
        });
/*

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
*/


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.transaction) {
            Intent i = new Intent(MainActivity.this, LeaveActivity.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        } else if (id == R.id.leave_register_report) {
            Intent i = new Intent(MainActivity.this, LeaveRegisterReport.class);
            startActivity(i);
        } else if (id == R.id.leave_balance_report) {
            Intent i = new Intent(MainActivity.this, LeaveBalanceReport.class);
            startActivity(i);
        } else if (id == R.id.holiday) {
            Intent i = new Intent(MainActivity.this, HolidayActivity.class);
            startActivity(i);
        } else if (id == R.id.birthday) {
            Intent i = new Intent(MainActivity.this, BirthdayActivity.class);
            startActivity(i);
        } else if (id == R.id.new_joinee) {
            Intent i = new Intent(MainActivity.this, NewJoiningActivity.class);
            startActivity(i);
        } else if (id == R.id.about_us) {
            Intent i = new Intent(MainActivity.this, AboutUs.class);
            startActivity(i);
        } else if (id == R.id.contact_us) {
            Intent i = new Intent(MainActivity.this, ContactUs.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
