package com.itbps.fuelmgt;

import java.util.Date;

public class Dispatchdelivery {
	private int dispatchdeliveryid;
	private String dispacthid;
	private String driverid;
	private String truckname;
	private Date dispatchdate;
	private Date createddate;
	private String lastupdateduser;

	public int getDispatchdeliveryid() {
		return dispatchdeliveryid;
	}

	public void setDispatchdeliveryid(int dispatchdeliveryid) {
		this.dispatchdeliveryid = dispatchdeliveryid;
	}

	public String getDispacthid() {
		return dispacthid;
	}

	public void setDispacthid(String dispacthid) {
		this.dispacthid = dispacthid;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getTruckname() {
		return truckname;
	}

	public void setTruckname(String truckname) {
		this.truckname = truckname;
	}

	public Date getDispatchdate() {
		return dispatchdate;
	}

	public void setDispatchdate(Date dispatchdate) {
		this.dispatchdate = dispatchdate;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getLastupdateduser() {
		return lastupdateduser;
	}

	public void setLastupdateduser(String lastupdateduser) {
		this.lastupdateduser = lastupdateduser;
	}

}
