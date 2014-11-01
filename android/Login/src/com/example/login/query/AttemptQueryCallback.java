package com.example.login.query;

public interface AttemptQueryCallback {
	void onQueryPreExecute();

	void doQueryInBackground(Item item);

	void onQueryPostExecute(String result);
}
