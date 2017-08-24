package com.record.time.timeredcord.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by santosh on 18/5/17.
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

    Context context;
    ProgressDialog progressDialog;

    public MyAsyncTask(Context context) {

        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setMax(100);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                cancel(true);
            }
        });
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(500);
                publishProgress(i);
            }

            return "Successful";
        }
        catch (InterruptedException e) {
            e.getMessage();
            return "Failure";
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int myValue = values[0];
        progressDialog.setProgress(myValue);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_LONG);
    }
}
