package com.itbps.fuelmgt;

import java.util.Date;

public class Driver {
	private String driverid;
	private String licenseno;
	private String licensestate;
	private Date licenseexpirationdate;

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getLicensestate() {
		return licensestate;
	}

	public void setLicensestate(String licensestate) {
		this.licensestate = licensestate;
	}

	public Date getLicenseexpirationdate() {
		return licenseexpirationdate;
	}

	public void setLicenseexpirationdate(Date licenseexpirationdate) {
		this.licenseexpirationdate = licenseexpirationdate;
	}

}
