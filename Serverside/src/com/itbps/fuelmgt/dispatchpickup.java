package com.itbps.fuelmgt;

import java.util.Date;

public class dispatchpickup {
	private int dispatchpickupid;
	private String dispatchid;
	private String driverid;
	private String terminal;
	private String truckname;
	private Date scheduledate;
	private Date createddate;
	private Date lastupdated;
	private String lastupdateduser;

	public int getDispatchpickupid() {
		return dispatchpickupid;
	}

	public void setDispatchpickupid(int dispatchpickupid) {
		this.dispatchpickupid = dispatchpickupid;
	}

	public String getDispatchid() {
		return dispatchid;
	}

	public void setDispatchid(String dispatchid) {
		this.dispatchid = dispatchid;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTruckname() {
		return truckname;
	}

	public void setTruckname(String truckname) {
		this.truckname = truckname;
	}

	public Date getScheduledate() {
		return scheduledate;
	}

	public void setScheduledate(Date scheduledate) {
		this.scheduledate = scheduledate;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getLastupdateduser() {
		return lastupdateduser;
	}

	public void setLastupdateduser(String lastupdateduser) {
		this.lastupdateduser = lastupdateduser;
	}

}
