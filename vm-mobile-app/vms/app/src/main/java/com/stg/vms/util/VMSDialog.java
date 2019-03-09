package com.stg.vms.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class VMSDialog {
    public static void showErrorDialog(final Activity context, String title, String message, final boolean backOnOk) {
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(context);
        errorDialogBuilder.setTitle(title);
        errorDialogBuilder.setMessage(message);
        errorDialogBuilder.setCancelable(true);
        errorDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                if (backOnOk)
                    context.onBackPressed();
            }
        });
        errorDialogBuilder.create().show();
    }
}
