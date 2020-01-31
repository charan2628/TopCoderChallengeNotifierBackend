package com.app.model;

import java.util.Arrays;

public class Login {

	private String username;
	private char[] password;
	
	public Login() {
		super();
	}

	public Login(String username, char[] password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + Arrays.toString(password) + "]";
	}
	
}
