package com.stg.vms.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.stg.vms.data.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class InputValidation {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertDateTime(String date, String format) {
        if (TextUtils.isEmpty(date))
            date = "-";
        if (TextUtils.isEmpty(format))
            format = AppConstants.DEFAULT_DATE_TIME_FORMAT;
        SimpleDateFormat current = new SimpleDateFormat(format, Locale.US);
        SimpleDateFormat expected = new SimpleDateFormat(AppConstants.VISIBLE_DATE_TIME_FORMAT, Locale.US);
        try {
            date = expected.format(current.parse(date));
        } catch (Exception e) {
            Log.e("DateFormat", "Error in date format: ", e);
        }
        return date;
    }

    public static String convertDateTime(String date) {
        return convertDateTime(date, AppConstants.DEFAULT_DATE_TIME_FORMAT);
    }
}
