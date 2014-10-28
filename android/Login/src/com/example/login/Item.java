package com.example.login;

public class Item {
	private String id;
	private String username;
	private String password;

	public Item(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

}
