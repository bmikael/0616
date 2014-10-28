// http://www.mybringback.com/tutorial-series/12924/android-tutorial-using-remote-databases-php-and-mysql-part-1/
package com.example.login;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String ACTIVITY_TAG = "MAIN_TAG";
	private static final String QUERY_URL = "http://192.168.56.1/webservice/query.php";

	// JSON element ids from repsonse of php script:
	private static final String JSON_TAG_COUNT = "count";
	private static final String JSON_TAG_USERNAME = "username";
	private static final String JSON_TAG_PASSWORD = "password";

	private ProgressDialog pDialog;
	private JSONParser jsonParser;

	private ListView list;
	private CustomList adapter;
	private ArrayList<Item> data = new ArrayList<Item>();
	private Activity mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
		initView();

		jsonParser = new JSONParser();

		new AttemptQuery().execute();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.listView1);
	}

	class AttemptQuery extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading....");
			pDialog.setIndeterminate(false);// 取消進度條
			pDialog.setCancelable(false);// 開啟取消
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			Log.d(ACTIVITY_TAG, "Loading starting");
			JSONObject json = jsonParser.makeHttpRequest(QUERY_URL, "POST",
					null);
			if (json == null) {
				return "connection error";
			}
			Log.d(ACTIVITY_TAG, "Loading attempt : " + json.toString());

			try {

				JSONArray numberList = json.getJSONArray("user");
				for (int i = 0; i < numberList.length(); i++) {
					Item item = new Item(numberList.getJSONObject(i).getString(JSON_TAG_COUNT),
							numberList.getJSONObject(i).getString(JSON_TAG_USERNAME),
							numberList.getJSONObject(i).getString(JSON_TAG_PASSWORD));
					
					data.add(item);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			adapter = new CustomList((Activity) mContext, data);
			list.setAdapter(adapter);
			
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
				Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG)
						.show();
			}

		}

	}
}
