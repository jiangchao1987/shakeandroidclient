package com.clover.shakeac.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil {

    public static ProgressDialog showProgressDialog(Context context, String text) {
        return ProgressDialog.show(context, "", text, true);
    }

    public static void dismissProgressDialog(ProgressDialog dialog) {
        try {
        } finally {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }
    
    public static AlertDialog.Builder showDialog(Context context, String text) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(text);
        return dialog;
    }
}
