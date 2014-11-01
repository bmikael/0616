package com.example.login.registermodule;

public interface AttemptRegisterCallback {
	void onRegisterPreExecute();

	String doRegisterGetUserName();

	String doRegisterGetPassword();

	String doRegisterGetRePassword();

	void doRegisterInBackground();

	void onRegisterPostExecute(String result);
}
