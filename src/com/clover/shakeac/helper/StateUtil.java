package com.clover.shakeac.helper;

import com.clover.shakeac.Constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class StateUtil {
	
	private ConnectivityManager mConMan = null;

	public StateUtil(Context context) {
		mConMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public boolean getIsNetworkAvailable() {
		boolean stateResult = true;
		NetworkInfo ni = mConMan.getActiveNetworkInfo();
		if (ni == null || !ni.isAvailable()) {
			stateResult = false;
		}
		return stateResult;
	}

	public WakeLock getWakeLock(Context context) {
		return ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, Constant.TAG);
	}

}
