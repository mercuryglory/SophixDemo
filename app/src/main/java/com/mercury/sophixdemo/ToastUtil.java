package com.mercury.sophixdemo;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast sToast;

    public static void showToast(final Context context, final String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();

    }
}