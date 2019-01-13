package com.itbps.user.model;

public class Credentials {
	private String userid = null;
	private String password = null;

	public Credentials() {
	}

	public Credentials(String userid, String password) {
		this.userid = userid;
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
