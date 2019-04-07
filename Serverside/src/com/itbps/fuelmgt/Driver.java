package com.itbps.fuelmgt;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Driver {
	private int driverid;
	private String licenseno;
	private String licensestate;
	private String licenseexpirationdate;
	private String firstname;
	private String lastname;
	private boolean active;
	private String nameid;
	
	

	
	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getNameid()
	{
		return nameid;
	}

	public void setNameid(String nameid)
	{
		this.nameid = nameid;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public int getDriverid() {
		return driverid;
	}

	public void setDriverid(int driverid) {
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

	public String getLicenseexpirationdate() {
		return licenseexpirationdate;
	}

	public void setLicenseexpirationdate(String licenseexpirationdate) {
		this.licenseexpirationdate = licenseexpirationdate;
	}

}
