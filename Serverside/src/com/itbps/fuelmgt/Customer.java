package com.itbps.fuelmgt;

public class Customer {
	private String customerid;
	private String name;
	private String billaddress1;
	private String billaddress2;
	private String billcity;
	private String billstate;
	private String billzipcode;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBilladdress1() {
		return billaddress1;
	}

	public void setBilladdress1(String billaddress1) {
		this.billaddress1 = billaddress1;
	}

	public String getBilladdress2() {
		return billaddress2;
	}

	public void setBilladdress2(String billaddress2) {
		this.billaddress2 = billaddress2;
	}

	public String getBillcity() {
		return billcity;
	}

	public void setBillcity(String billcity) {
		this.billcity = billcity;
	}

	public String getBillstate() {
		return billstate;
	}

	public void setBillstate(String billstate) {
		this.billstate = billstate;
	}

	public String getBillzipcode() {
		return billzipcode;
	}

	public void setBillzipcode(String billzipcode) {
		this.billzipcode = billzipcode;
	}

}
