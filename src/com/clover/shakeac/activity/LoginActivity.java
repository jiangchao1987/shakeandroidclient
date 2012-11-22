package com.clover.shakeac.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.clover.shakeac.Constant;
import com.clover.shakeac.R;
import com.clover.shakeac.helper.DialogUtil;
import com.clover.shakeac.helper.ParseUtil;
import com.clover.shakeac.helper.RestClient;
import com.clover.shakeac.helper.SharedUtil;
import com.clover.shakeac.helper.StateUtil;
import com.clover.shakeac.helper.ToastUtil;
import com.clover.shakeac.model.Result;
import com.clover.shakeac.model.User;

public class LoginActivity extends Activity {
	
	private RestClient client;
    private StateUtil stateUtil;
    private SharedUtil sharedUtil;
    
	private EditText loginUsername;
	private EditText loginPassword;
    private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		ui();
	}
	
	private void init() {
		stateUtil = new StateUtil(LoginActivity.this);
		sharedUtil = new SharedUtil(LoginActivity.this);
	}

	private void ui() {
		loginUsername = (EditText) findViewById(R.id.login_username_edit_text);
		loginPassword = (EditText) findViewById(R.id.login_password_edit_text);
		loginUsername.setText(sharedUtil.getUsername());
        loginPassword.setText(sharedUtil.getPwd());
	}

	// login event
	public void login(View v) {
        progressDialog = DialogUtil.showProgressDialog(LoginActivity.this, Constant.MSG_LOGIN);
        new LoginTask().execute(loginUsername.getText().toString(), loginPassword.getText().toString());
    }
	
	class LoginTask extends AsyncTask<String, Void, Integer> {
		
		Result<User> result;

		@Override
		protected Integer doInBackground(String... params) {
            if (!stateUtil.getIsNetworkAvailable()) {
                return Constant.NETWORK_NOT_AVAILABLE;
            }

            client = RestClient.newInstance(LoginActivity.this);
            String json = client.login(params[0], params[1]);
            if (json == null || json.trim().equals("")) {
                return Constant.UNKNOW;
            }

            result = ParseUtil.parseUser(json);
            if (result == null) {
                return Constant.UNKNOW;
            }

            return Constant.CONTINUE;
        }
		
		@Override
        protected void onPostExecute(Integer statusCode) {
            super.onPostExecute(statusCode);
            switch (statusCode) {
                case Constant.CONTINUE :
                    DialogUtil.dismissProgressDialog(progressDialog);

                    ToastUtil.showShortToast(LoginActivity.this, result.getMessage());
                    if (result.getStatus() == Constant.SUCCESS) {
                        login(result.getModel());
                    }
                    break;
                case Constant.NETWORK_NOT_AVAILABLE :
                    DialogUtil.dismissProgressDialog(progressDialog);

                    ToastUtil.showShortToast(LoginActivity.this,
                            Constant.MSG_NETWORK_NOT_AVAILABLE);
                    break;
                case Constant.UNKNOW :
                    DialogUtil.dismissProgressDialog(progressDialog);

                    ToastUtil.showShortToast(LoginActivity.this, Constant.MSG_UNKNOW);
                    break;
                default :
                    break;
            }
        }

        private void login(User user) {
            Constant.USER = user;
            Log.i(Constant.TAG, user.getUdid());

            sharedUtil.saveUsernameAndPwd(loginUsername.getText().toString(), loginPassword.getText().toString());

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
            LoginActivity.this.finish();
        }
	}

}
