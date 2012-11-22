package com.clover.shakeac.helper;

import com.clover.shakeac.Constant;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

	private Context context;

    public SharedUtil(Context context) {
        this.context = context;
    }
    
    public void saveCustomDir(String dir) {
        SharedPreferences.Editor editor = getEditor().edit();
        editor.putString("video_custom", dir);
        editor.commit();
    }
    
    public String getCustomDir() {
        return getEditor().getString("video_custom", "");
    }
    
    public void saveUpdateTime(String updateTime) {
        SharedPreferences.Editor editor = getEditor().edit();
        editor.putString("news", updateTime);
        editor.commit();
    }

    public String getUpdateTime() {
        return getEditor().getString("news", "");
    }
    
    public void saveUsernameAndPwd(String username, String pwd) {
        SharedPreferences.Editor editor = getEditor().edit();
        editor.putString("username", username);
        editor.putString("pwd", pwd);
        editor.commit();
    }
    
    public String getUsername() {
        return getEditor().getString("username", "");
    }
    
    public String getPwd() {
        return getEditor().getString("pwd", "");
    }
    
    private SharedPreferences getEditor() {
        return context.getSharedPreferences(Constant.TAG, 0);
    }

}
