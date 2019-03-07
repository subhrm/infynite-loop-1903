package com.stg.vms.model;

import android.util.Log;

import com.stg.vms.data.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VisitorsLastDay {
    private String date;
    private int count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDayOfMonth() {
        SimpleDateFormat actualFormat = new SimpleDateFormat(AppConstants.DEFAULT_DATE_FORMAT, Locale.US);
        SimpleDateFormat desiredFormat = new SimpleDateFormat("dd", Locale.US);
        try {
            return Double.valueOf(desiredFormat.format(actualFormat.parse(this.date)));
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error in date parsing.", e);
        }
        return Double.valueOf(0);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
