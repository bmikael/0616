// http://www.mybringback.com/tutorial-series/12924/android-tutorial-using-remote-databases-php-and-mysql-part-1/
package com.example.login.query;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.R;

public class MainActivity extends Activity implements AttemptQueryCallback {
	// constant value
	private static final String QUERY_URL = "http://192.168.2.111/webservice/query.php";

	private ProgressDialog pDialog;

	private ListView list;
	private CustomList adapter;
	private ArrayList<Item> data = new ArrayList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();

		new AttemptQuery(MainActivity.this).execute(QUERY_URL);
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.listView1);
	}

	@Override
	public void onQueryPreExecute() {
		// TODO Auto-generated method stub
		pDialog = new ProgressDialog(MainActivity.this);
		pDialog.setMessage("Loading....");
		pDialog.setIndeterminate(false);// 取消進度條
		pDialog.setCancelable(false);// 開啟取消
		pDialog.show();
	}

	@Override
	public void doQueryInBackground(Item item) {
		// TODO Auto-generated method stub
		data.add(item);
	}

	@Override
	public void onQueryPostExecute(String result) {
		// TODO Auto-generated method stub
		pDialog.dismiss();
		adapter = new CustomList(MainActivity.this, data);
		list.setAdapter(adapter);
		if (result != null) {
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
		}
	}
}
