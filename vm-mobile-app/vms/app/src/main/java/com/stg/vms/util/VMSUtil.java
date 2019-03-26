package com.stg.vms.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class VMSUtil {
    public static String extractFirstName(@NonNull String fullName) {
        return fullName.split(" ")[0];
    }
    public static String formatStringDate(String date, String existingFormat, String desiredFormat) {
        try {
            SimpleDateFormat exDf = new SimpleDateFormat(existingFormat, Locale.US);
            SimpleDateFormat dsDf = new SimpleDateFormat(desiredFormat, Locale.US);
            return dsDf.format(exDf.parse(date));
        } catch (Exception e) {
            Log.e("VMSUtil", "Error in formating date string", e);
        }
        return null;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
