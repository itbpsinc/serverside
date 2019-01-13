package com.itbps.fuelmgt;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Itemterminalpickup
{
	
	private int itemterminalpickupid;
	private int terminaldispatchdetailid;
	private int compartmentid;
	private double qtypickup;
	private String billoflading;
	private Date pickupdate;
	private Date itempickup_createddate;
	private Date lastupdated;
	private String lastupdateduser;
	private boolean verifierid;
	private Date verificationdate;
	private String fuelname;
	private double scheduledquantity;
	private Date dispatch_createddate;
	private int driverid;
	private int disbatchid;
	private String terminalid;
	private String truckname;
	private Date scheduledate;
	private String compartment;
	private String driver;
	private String dispatcher;
	
	public int getItemterminalpickupid()
	{
		return itemterminalpickupid;
	}
	
	public void setItemterminalpickupid(int itemterminalpickupid)
	{
		this.itemterminalpickupid = itemterminalpickupid;
	}
	
	public int getTerminaldispatchdetailid()
	{
		return terminaldispatchdetailid;
	}
	
	public void setTerminaldispatchdetailid(int terminaldispatchdetailid)
	{
		this.terminaldispatchdetailid = terminaldispatchdetailid;
	}
	
	public int getCompartmentid()
	{
		return compartmentid;
	}
	
	public void setCompartmentid(int compartmentid)
	{
		this.compartmentid = compartmentid;
	}
	
	public double getQtypickup()
	{
		return qtypickup;
	}
	
	public void setQtypickup(double qtypickup)
	{
		this.qtypickup = qtypickup;
	}
	
	public String getBilloflading()
	{
		return billoflading;
	}
	
	public void setBilloflading(String billoflading)
	{
		this.billoflading = billoflading;
	}
	
	public Date getPickupdate()
	{
		return pickupdate;
	}
	
	public void setPickupdate(Date pickupdate)
	{
		this.pickupdate = pickupdate;
	}
	
	public Date getItempickup_createddate()
	{
		return itempickup_createddate;
	}
	
	public void setItempickup_createddate(Date itempickup_createddate)
	{
		this.itempickup_createddate = itempickup_createddate;
	}
	
	public Date getLastupdated()
	{
		return lastupdated;
	}
	
	public void setLastupdated(Date lastupdated)
	{
		this.lastupdated = lastupdated;
	}
	
	public String getLastupdateduser()
	{
		return lastupdateduser;
	}
	
	public void setLastupdateduser(String lastupdateduser)
	{
		this.lastupdateduser = lastupdateduser;
	}
	
	public boolean isVerifierid()
	{
		return verifierid;
	}
	
	public void setVerifierid(boolean verifierid)
	{
		this.verifierid = verifierid;
	}
	
	public Date getVerificationdate()
	{
		return verificationdate;
	}
	
	public void setVerificationdate(Date verificationdate)
	{
		this.verificationdate = verificationdate;
	}
	
	public String getFuelname()
	{
		return fuelname;
	}
	
	public void setFuelname(String fuelname)
	{
		this.fuelname = fuelname;
	}
	
	public double getScheduledquantity()
	{
		return scheduledquantity;
	}
	
	public void setScheduledquantity(double scheduledquantity)
	{
		this.scheduledquantity = scheduledquantity;
	}
	
	public Date getDispatch_createddate()
	{
		return dispatch_createddate;
	}
	
	public void setDispatch_createddate(Date dispatch_createddate)
	{
		this.dispatch_createddate = dispatch_createddate;
	}
	
	public int getDriverid()
	{
		return driverid;
	}
	
	public void setDriverid(int driverid)
	{
		this.driverid = driverid;
	}
	
	public int getDisbatchid()
	{
		return disbatchid;
	}
	
	public void setDisbatchid(int disbatchid)
	{
		this.disbatchid = disbatchid;
	}
	
	public String getTerminalid()
	{
		return terminalid;
	}
	
	public void setTerminalid(String terminalid)
	{
		this.terminalid = terminalid;
	}
	
	public String getTruckname()
	{
		return truckname;
	}
	
	public void setTruckname(String truckname)
	{
		this.truckname = truckname;
	}
	
	public Date getScheduledate()
	{
		return scheduledate;
	}
	
	public void setScheduledate(Date scheduledate)
	{
		this.scheduledate = scheduledate;
	}
	
	public String getCompartment()
	{
		return compartment;
	}
	
	public void setCompartment(String compartment)
	{
		this.compartment = compartment;
	}
	
	public String getDriver()
	{
		return driver;
	}
	
	public void setDriver(String driver)
	{
		this.driver = driver;
	}
	
	public String getDispatcher()
	{
		return dispatcher;
	}
	
	public void setDispatcher(String dispatcher)
	{
		this.dispatcher = dispatcher;
	}
	
}
