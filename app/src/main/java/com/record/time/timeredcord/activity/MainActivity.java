package com.record.time.timeredcord.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.record.time.timeredcord.R;
import com.record.time.timeredcord.util.Calculator;
import com.record.time.timeredcord.util.Constant;
import com.record.time.timeredcord.util.MyCountDownTimer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyCountDownTimer myCountDownTimer;

    private String currentDate, startTime, setDuration;
    private int hr, min;
    private boolean mIsClicked = true;
    private EditText mHrEntry, mMinEntry;
    private TextView mHour, mMinute, mSec;
    private TextView mDate, mStart_Time, mDuration;
    private TextView mTotal_Record_Time;
    private Button mStartBtn, mPauseResume, mStopBtn, mSetTimeDuration;
    private LinearLayout mTimer_Display, mTime_Recording, mTotal_consumption, mTimeEntry, mButtonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * initView() method is used to initilize the view.
     */
    private void initView() {

        mHour = (TextView) findViewById(R.id.hour);
        mMinute = (TextView) findViewById(R.id.minute);
        mSec = (TextView) findViewById(R.id.second);

        mHrEntry = (EditText) findViewById(R.id.editHr);
        mMinEntry = (EditText) findViewById(R.id.editMi);
        mDate = (TextView) findViewById(R.id.date);
        mDuration = (TextView) findViewById(R.id.duration);
        mStart_Time = (TextView) findViewById(R.id.start_time);
        mTotal_Record_Time = (TextView) findViewById(R.id.total_record_time);

        mStartBtn = (Button) findViewById(R.id.start_btn);
        mPauseResume = (Button) findViewById(R.id.pause_btn);
        mStopBtn = (Button) findViewById(R.id.stop_btn);
        mSetTimeDuration = (Button) findViewById(R.id.setDurationBtn);

        mTimeEntry = (LinearLayout) findViewById(R.id.timeEntry);
        mTimer_Display = (LinearLayout) findViewById(R.id.timer_display);
        mTime_Recording = (LinearLayout) findViewById(R.id.recording_display);
        mTotal_consumption = (LinearLayout) findViewById(R.id.total_consumption);
        mButtonLayout = (LinearLayout) findViewById(R.id.button_layout);

        mSetTimeDuration.setOnClickListener(this);
        mStartBtn.setOnClickListener(this);
        mPauseResume.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.setDurationBtn:
                mSetTimeDuration.setVisibility(View.GONE);
                mTimeEntry.setVisibility(View.GONE);
                if (mHrEntry.getText().toString() != null)
                    hr = Integer.parseInt(mHrEntry.getText().toString());
                if (mMinEntry.getText().toString() != null)
                    min = Integer.parseInt(mMinEntry.getText().toString());
                mButtonLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.start_btn:
                customNotification();
                mTimeEntry.setVisibility(View.GONE);
                displayStartTime();
                startTimer(Calculator.convertHourAndMinuteIntoMilliSecond(hr, min));
                mTime_Recording.setVisibility(View.VISIBLE);
                break;

            case R.id.pause_btn:
                if (myCountDownTimer != null) {

                    if (mIsClicked) {
                        myCountDownTimer.pause();
                        mPauseResume.setText("Resume");
                        mIsClicked = false;
                    }
                    else {
                        myCountDownTimer.resume();
                        mPauseResume.setText("Pause");
                        mIsClicked = true;
                    }
                }
                break;

            case R.id.stop_btn:
                displayTotalConsumptionTime(hr, min);
                break;
        }
    }

    /**
     * startTimer() method is used to start time using
     * countDown Timer.
     */
    private void startTimer(long timeDuration) {

        myCountDownTimer = new MyCountDownTimer(timeDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //mRecord_Time.setText("Remaining Time: " + Calculator.displayTimeInHHMMSSFormate(millisUntilFinished));
                updateUI(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                //mRecord_Time.setText("done!");
            }
        };

        myCountDownTimer.start();
    }

    /**
     * updateUI() method is used to update the UI
     * @param millisUntilFinished
     */
    private void updateUI(long millisUntilFinished) {

        if(millisUntilFinished/(60 * 60 * 1000) < 10 )
            mHour.setText("0" + (millisUntilFinished/(60 * 60 * 1000)));
        else
            mHour.setText("" + (millisUntilFinished/(60 * 60 * 1000)));

        if ((millisUntilFinished / (60 * 1000) % 60) < 10 )
            mMinute.setText("0" + (millisUntilFinished / (60 * 1000) % 60));
        else
            mMinute.setText("" + (millisUntilFinished / (60 * 1000) % 60));

        if ((millisUntilFinished / 1000 % 60) < 10)
            mSec.setText("0" + (millisUntilFinished / 1000 % 60));
        else
            mSec.setText("" + (millisUntilFinished / 1000 % 60));

    }

    /**
     * displayStartTime() method is used to display date and time
     * on the screen after click on start button.
     */
    private void displayStartTime() {

        mTimer_Display.setVisibility(View.VISIBLE);
        mTime_Recording.setVisibility(View.INVISIBLE);
        mTotal_consumption.setVisibility(View.INVISIBLE);

        currentDate = Calculator.getDate();
        startTime = Calculator.getCurrentTime();
        setDuration = "" + hr + min;
        mDate.setText("Date: " + currentDate );
        mStart_Time.setText("Start Time: " + startTime);
        mDuration.setText("Duration: " + hr + " : " + min);
        mStartBtn.setClickable(false);
        mStopBtn.setClickable(true);
    }

    /**
     * displayTotalConsumptionTime() method is used to display
     * total consumption time between the interval.
     */
    private void displayTotalConsumptionTime(int hr, int min) {

        mStartBtn.setClickable(true);
        mStopBtn.setClickable(false);
        myCountDownTimer.cancel();

        long remainTime;
        if (myCountDownTimer.mPaused) {
            remainTime = myCountDownTimer.pause();
        }
        else {
            remainTime = myCountDownTimer.resume();
        }
        long totalConsumptionTime = Calculator.convertHourAndMinuteIntoMilliSecond(hr, min) - remainTime;
        mTotal_Record_Time.setText("Total Consumption Time: " + Calculator.displayTimeInHHMMSSFormate(totalConsumptionTime));

        startResultActivity(currentDate, startTime, setDuration, Calculator.displayTimeInHHMMSSFormate(totalConsumptionTime));
    }

    /**
     * customNotification() method is used to create custom notification
     */
    public void customNotification() {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);

        // Set Notification Title
        String strtitle = "customnotificationtitle";
        // Set Notification Text
        String strtext = "customnotificationtext";

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, MainActivity.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_alarm_black_48dp)
                // Set Ticker Message
                .setTicker("customnotificationticker")
                // Dismiss Notification
                .setAutoCancel(false)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        //remoteViews.setImageViewResource(R.id.noti_img,R.drawable.ic_launcher);
        //remoteViews.setImageViewResource(R.id.imagenotiright,R.drawable.androidhappy);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title, "customnotificationtitle");
        remoteViews.setTextViewText(R.id.text, "customnotificationtext");

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }


    private void startResultActivity(String date, String startTime, String duration, String consumptionTime) {

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra(Constant.date_key, date);
        intent.putExtra(Constant.start_time, startTime);
        intent.putExtra(Constant.duration, duration);
        //intent.putExtra(Constant.hour, consumptionTime);
        startActivity(intent);
    }

}
