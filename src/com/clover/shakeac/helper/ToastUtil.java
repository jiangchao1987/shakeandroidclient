package com.clover.shakeac.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static void showShortToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	
}
