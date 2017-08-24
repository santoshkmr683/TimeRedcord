package com.record.time.timeredcord.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by santosh on 15/5/17.
 */

public class Calculator {

    /**
     * getCurrentTime() method return current time as in string format
     * @return
     */
    public static String getCurrentTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * getDate() method return current date
     * @return
     */
    public static String getDate() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * totalConsumptionTime() method return total consumption time
     * between two interval time
     * @param startTime
     * @param stopTime
     * @return
     */
    public static String totalConsumptionTime(long startTime, long stopTime) {

        long diff = stopTime - startTime;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        return diffHours + " : " + diffMinutes + " : " + diffSeconds;
    }

    /**
     * displayTimeInHHMMSSFormate() method return time in the
     * format of HH : MM : SS
     * @param timeInMilliSec
     * @return
     */
    public static String displayTimeInHHMMSSFormate(long timeInMilliSec) {

        String hr, min, sec;
        long seconds = timeInMilliSec / 1000 % 60;
        long minutes = timeInMilliSec / (60 * 1000) % 60;
        long hours = timeInMilliSec / (60 * 60 * 1000);

        if (seconds < 10)
            sec = "0" + seconds;
        else
            sec = "" + seconds;

        if (minutes < 10)
            min = "0" + minutes;
        else
            min = "" + minutes;

        if (hours < 10)
            hr = "0" + hours;
        else
            hr = "" + hours;

        return hr + " : " + min + " : " + sec;
    }

    public static long convertHourAndMinuteIntoMilliSecond(int hr, int min) {

        long milliSec = (hr * 60 * 60 * 1000) + (min * 60 * 1000);
        return milliSec;
    }

}
