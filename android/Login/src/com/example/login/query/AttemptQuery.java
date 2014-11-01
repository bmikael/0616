package com.example.login.query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.login.JSONParser;

public class AttemptQuery extends AsyncTask<String, String, String> {
	AttemptQueryCallback callback;
	// constant value
	private static final String ACTIVITY_TAG = "MAIN_TAG";

	// JSON element ids from repsonse of php script:
	private static final String JSON_TAG_COUNT = "count";
	private static final String JSON_TAG_USERNAME = "username";
	private static final String JSON_TAG_PASSWORD = "password";

	//
	private JSONParser jsonParser;

	public AttemptQuery(AttemptQueryCallback callback) {
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
		callback.onQueryPreExecute();
	}

	@Override
	protected String doInBackground(String... args) {
		Log.d(ACTIVITY_TAG, "Loading starting");
		JSONObject json = jsonParser.makeHttpRequest(args[0], "POST", null);
		if (json == null) {
			return "connection error";
		}
		Log.d(ACTIVITY_TAG, "Loading attempt : " + json.toString());

		try {

			JSONArray numberList = json.getJSONArray("user");
			for (int i = 0; i < numberList.length(); i++) {
				Item item = new Item(numberList.getJSONObject(i).getString(
						JSON_TAG_COUNT), numberList.getJSONObject(i).getString(
						JSON_TAG_USERNAME), numberList.getJSONObject(i)
						.getString(JSON_TAG_PASSWORD));

				callback.doQueryInBackground(item);
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
		callback.onQueryPostExecute(result);
	}

}
