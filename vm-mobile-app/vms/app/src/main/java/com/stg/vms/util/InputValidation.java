package com.stg.vms.util;

import android.text.TextUtils;
import android.util.Patterns;

public class InputValidation {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
