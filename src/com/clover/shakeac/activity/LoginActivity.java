package com.clover.shakeac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.clover.shakeac.R;

public class LoginActivity extends Activity {
	private EditText mET_username;
	private EditText mET_password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ui();
	}

	private void ui() {
		mET_username = (EditText) findViewById(R.id.login_username_edit_text);
		mET_password = (EditText) findViewById(R.id.login_password_edit_text);
	}

	public void login(View v) {
		
	}

}
