/*
 * Reference : http://www.androidhive.info/2011/10/android-login-and-registration-screen-design/
 * 
 */

package com.example.login.loginmodule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.query.MainActivity;
import com.example.login.registermodule.RegisterActivity;

public class LoginActivity extends Activity implements AttemptLoginCallback {
	// constant value
	private static final String LOGIN_URL = "http://192.168.2.111/webservice/login.php";

	// JSON element ids from repsonse of php script:

	//
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private TextView registerScreen;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// hide action bar
		getActionBar().hide();

		initView();
		initLoginEvent();
		initToRegisterEvent();
	}

	private void initToRegisterEvent() {
		// TODO Auto-generated method stub
		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});

	}

	private void initLoginEvent() {
		// TODO Auto-generated method stub
		// Listening to login button clicked
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Saving current user info
				new AttemptLogin(LoginActivity.this).execute(LOGIN_URL);
			}
		});
	}

	private void initView() {
		usernameEditText = (EditText) findViewById(R.id.loginUsername);
		passwordEditText = (EditText) findViewById(R.id.loginPassword);
		registerScreen = (TextView) findViewById(R.id.link_to_register);
		loginButton = (Button) findViewById(R.id.loginButton);
	}

	@Override
	public void onLoginPreExecute() {
		// TODO Auto-generated method stub
		pDialog = new ProgressDialog(LoginActivity.this);
		pDialog.setMessage(getResources().getString(
				R.string.login_activity_loading_progress_dialog_context));
		pDialog.setIndeterminate(false);// 取消進度條
		pDialog.setCancelable(true);// 開啟取消
		pDialog.show();
	}

	@Override
	public String doLoginGetUserName() {
		// TODO Auto-generated method stub
		return usernameEditText.getText().toString();
	}

	@Override
	public String doLoginGetPassword() {
		// TODO Auto-generated method stub
		return passwordEditText.getText().toString();
	}

	@Override
	public void doLoginInBackground() {
		// TODO Auto-generated method stub
		Intent i = new Intent(getApplicationContext(), MainActivity.class);

		// finished current activity, and start another new activity
		finish();
		startActivity(i);
	}

	@Override
	public void onLoginPostExecute(String result) {
		// TODO Auto-generated method stub
		pDialog.dismiss();
		if (result != null) {
			Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}
	}
}
