package com.example.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	// constant value
	private static final String ACTIVITY_TAG = "REGISTER_TAG";
	private static final String REGISTER_URL = "http://192.168.56.1/webservice/register.php";

	// JSON element ids from repsonse of php script:
	private static final String JSON_TAG_SUCCESS = "success";
	private static final String JSON_TAG_MESSAGE = "message";

	private ProgressDialog pDialog;
	private TextView loginScreen;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText repeatPasswordEditText;
	private Button registerButton;
	private JSONParser jsonParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// hide action bar
		getActionBar().hide();

		jsonParser = new JSONParser();
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
				new AttemptRegister().execute();
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

	class AttemptRegister extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("Register....");
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
			String repassword = repeatPasswordEditText.getText().toString();

			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				params.add(new BasicNameValuePair("repassword", repassword));

				Log.d(ACTIVITY_TAG, "Register starting");
				JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL,
						"POST", params);
				if(json == null)
				{
					return "connection error";
				}				
				Log.d(ACTIVITY_TAG, "Register attempt : " + json.toString());

				success = json.getInt(JSON_TAG_SUCCESS);
				if (success == 1) {
					Log.d(ACTIVITY_TAG,
							"Create Account Successful : " + json.toString());
					finish();
					return json.getString(JSON_TAG_MESSAGE);
				} else {
					Log.d(ACTIVITY_TAG,
							"Create Account Failure : "
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
				Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

}