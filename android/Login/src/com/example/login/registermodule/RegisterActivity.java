package com.example.login.registermodule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;

public class RegisterActivity extends Activity implements
		AttemptRegisterCallback {
	// constant value
	private static final String REGISTER_URL = "http://192.168.2.111/webservice/register.php";

	private ProgressDialog pDialog;
	private TextView loginScreen;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText repeatPasswordEditText;
	private Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// hide action bar
		getActionBar().hide();

		initView();
		initToLoginEvent();
		initRegisterEvent();
	}

	private void initToLoginEvent() {
		// TODO Auto-generated method stub
		loginScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				finish();
			}
		});

	}

	private void initRegisterEvent() {
		// TODO Auto-generated method stub
		registerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new AttemptRegister(RegisterActivity.this)
						.execute(REGISTER_URL);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		loginScreen = (TextView) findViewById(R.id.link_to_login);
		usernameEditText = (EditText) findViewById(R.id.registerUsername);
		passwordEditText = (EditText) findViewById(R.id.registerPassword);
		repeatPasswordEditText = (EditText) findViewById(R.id.repeatPassword);
		registerButton = (Button) findViewById(R.id.registerButton);
	}

	/*
	 * class AttemptRegister extends AsyncTask<String, String, String> {
	 * 
	 * @Override protected void onPreExecute() { // TODO Auto-generated method
	 * stub super.onPreExecute(); pDialog = new
	 * ProgressDialog(RegisterActivity.this);
	 * pDialog.setMessage("Register...."); pDialog.setIndeterminate(false);//
	 * 取消進度條 pDialog.setCancelable(true);// 開啟取消 pDialog.show(); }
	 * 
	 * @Override protected String doInBackground(String... args) { // TODO
	 * Auto-generated method stub int success; String username =
	 * usernameEditText.getText().toString(); String password =
	 * passwordEditText.getText().toString(); String repassword =
	 * repeatPasswordEditText.getText().toString();
	 * 
	 * try { List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("username", username)); params.add(new
	 * BasicNameValuePair("password", password)); params.add(new
	 * BasicNameValuePair("repassword", repassword));
	 * 
	 * Log.d(ACTIVITY_TAG, "Register starting"); JSONObject json =
	 * jsonParser.makeHttpRequest(REGISTER_URL, "POST", params); if(json ==
	 * null) { return "connection error"; } Log.d(ACTIVITY_TAG,
	 * "Register attempt : " + json.toString());
	 * 
	 * success = json.getInt(JSON_TAG_SUCCESS); if (success == 1) {
	 * Log.d(ACTIVITY_TAG, "Create Account Successful : " + json.toString());
	 * finish(); return json.getString(JSON_TAG_MESSAGE); } else {
	 * Log.d(ACTIVITY_TAG, "Create Account Failure : " +
	 * json.getString(JSON_TAG_MESSAGE)); return
	 * json.getString(JSON_TAG_MESSAGE); } } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * return null; }
	 * 
	 * 
	 * @Override protected void onPostExecute(String result) { // TODO
	 * Auto-generated method stub super.onPostExecute(result);
	 * pDialog.dismiss(); if (result != null) {
	 * Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG) .show();
	 * } } }
	 */

	@Override
	public void onRegisterPreExecute() {
		// TODO Auto-generated method stub
		pDialog = new ProgressDialog(RegisterActivity.this);
		pDialog.setMessage(getResources().getString(
				R.string.Register_activity_loading_progress_dialog_context));

		pDialog.setIndeterminate(false);// 取消進度條
		pDialog.setCancelable(true);// 開啟取消
		pDialog.show();
	}

	@Override
	public String doRegisterGetUserName() {
		// TODO Auto-generated method stub
		return usernameEditText.getText().toString();
	}

	@Override
	public String doRegisterGetPassword() {
		// TODO Auto-generated method stub
		return passwordEditText.getText().toString();
	}

	@Override
	public String doRegisterGetRePassword() {
		// TODO Auto-generated method stub
		return repeatPasswordEditText.getText().toString();
	}

	@Override
	public void doRegisterInBackground() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onRegisterPostExecute(String result) {
		// TODO Auto-generated method stub
		pDialog.dismiss();
		if (result != null) {
			Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}
	}
}