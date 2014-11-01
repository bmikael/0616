package com.example.login.loginmodule;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.login.JSONParser;

public class AttemptLogin extends AsyncTask<String, String, String> {
	AttemptLoginCallback callback;
	// constant value
	private static final String ACTIVITY_TAG = "LOGIN_TAG";

	// JSON element ids from repsonse of php script:
	private static final String JSON_TAG_SUCCESS = "success";
	private static final String JSON_TAG_MESSAGE = "message";

	//
	private JSONParser jsonParser;

	public AttemptLogin(AttemptLoginCallback callback) {
		this.callback = callback;

		jsonParser = new JSONParser();
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		callback.onLoginPreExecute();
	}

	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub
		int success;
		String username = callback.doLoginGetUserName();
		String password = callback.doLoginGetPassword();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			Log.d(ACTIVITY_TAG, "Login starting");

			JSONObject json = jsonParser.makeHttpRequest(args[0], "POST",
					params);
			if (json == null) {
				return "connection error";
			}
			Log.d(ACTIVITY_TAG, "Login attempt : " + json.toString());
			success = json.getInt(JSON_TAG_SUCCESS);
			if (success == 1) {
				Log.d(ACTIVITY_TAG, "Login Successful : " + json.toString());
				callback.doLoginInBackground();
				return json.getString(JSON_TAG_MESSAGE);
			} else {
				Log.d(ACTIVITY_TAG,
						"Login Failure : " + json.getString(JSON_TAG_MESSAGE));
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
		callback.onLoginPostExecute(result);
	}

}
