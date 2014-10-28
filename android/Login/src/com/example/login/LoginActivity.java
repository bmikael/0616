/*
 * Reference : http://www.androidhive.info/2011/10/android-login-and-registration-screen-design/
 * 
 */

package com.example.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// constant value
	private static final String ACTIVITY_TAG = "LOGIN_TAG";
	private static final String LOGIN_URL = "http://192.168.56.1/webservice/login.php";

	// JSON element ids from repsonse of php script:
	private static final String JSON_TAG_SUCCESS = "success";
	private static final String JSON_TAG_MESSAGE = "message";

	//
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private TextView registerScreen;
	private ProgressDialog pDialog;
	private JSONParser jsonParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// hide action bar
		getActionBar().hide();

		jsonParser = new JSONParser();
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
				new AttemptLogin().execute();
			}
		});
	}

	private void initView() {

		usernameEditText = (EditText) findViewById(R.id.loginUsername);
		passwordEditText = (EditText) findViewById(R.id.loginPassword);
		registerScreen = (TextView) findViewById(R.id.link_to_register);
		loginButton = (Button) findViewById(R.id.loginButton);
	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Login....");
			pDialog.setIndeterminate(false);// 取消進度條
			pDialog.setCancelable(true);// 開啟取消
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			int success;
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();

			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				Log.d(ACTIVITY_TAG, "Login starting");

				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);
				if(json == null)
				{
					return "connection error";
				}
				Log.d(ACTIVITY_TAG, "Login attempt : " + json.toString());
				success = json.getInt(JSON_TAG_SUCCESS);
				if (success == 1) {
					Log.d(ACTIVITY_TAG, "Login Successful : " + json.toString());
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);

					// finished current activity, and start another new activity
					finish();
					startActivity(i);
					return json.getString(JSON_TAG_MESSAGE);
				} else {
					Log.d(ACTIVITY_TAG,
							"Login Failure : "
									+ json.getString(JSON_TAG_MESSAGE));
					return json.getString(JSON_TAG_MESSAGE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			if (result != null) {
				Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG)
						.show();
			}

		}

	}
}
