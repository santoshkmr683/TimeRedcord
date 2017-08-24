package com.record.time.timeredcord.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.record.time.timeredcord.R;
import com.record.time.timeredcord.util.Constant;

public class ResultActivity extends AppCompatActivity {


    private TextView mStartDate, mStartTime, mTimeDuration;
    private TextView mHour, mMinute, mSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        setDataOnUI();
    }

    private void initView() {
        mStartDate = (TextView) findViewById(R.id.start_date);
        mStartTime = (TextView) findViewById(R.id.start_time);
        mTimeDuration = (TextView) findViewById(R.id.time_duration);

        mHour = (TextView) findViewById(R.id.hour);
        mMinute = (TextView) findViewById(R.id.minute);
        mSec = (TextView) findViewById(R.id.second);
    }

    private void setDataOnUI() {

        Intent intent = getIntent();
        if (intent != null) {
            String date = intent.getExtras().getString(Constant.date_key);
            String startTime = intent.getExtras().getString(Constant.start_time);
            String duration = intent.getExtras().getString(Constant.duration);

            mStartDate.setText(":  " + date);
            mStartTime.setText(":  " + startTime);
            mTimeDuration.setText(":  " + duration);
        }
    }
}
