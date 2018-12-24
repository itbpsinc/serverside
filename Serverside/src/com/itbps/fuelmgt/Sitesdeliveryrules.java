package com.itbps.fuelmgt;

import java.util.Date;

public class Sitesdeliveryrules {
	private int sitesdeliveryrulesid;
	private int siteid;
	private Date startdate;
	private Date enddate;
	private int minqty;
	private double freightchrg;

	public int getSitesdeliveryrulesid() {
		return sitesdeliveryrulesid;
	}

	public void setSitesdeliveryrulesid(int sitesdeliveryrulesid) {
		this.sitesdeliveryrulesid = sitesdeliveryrulesid;
	}

	public int getSiteid() {
		return siteid;
	}

	public void setSiteid(int siteid) {
		this.siteid = siteid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public int getMinqty() {
		return minqty;
	}

	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}

	public double getFreightchrg() {
		return freightchrg;
	}

	public void setFreightchrg(double freightchrg) {
		this.freightchrg = freightchrg;
	}

}
