package com.example.login.loginmodule;

public interface AttemptLoginCallback {
	void onLoginPreExecute();

	String doLoginGetUserName();

	String doLoginGetPassword();

	void doLoginInBackground();

	void onLoginPostExecute(String result);
}
