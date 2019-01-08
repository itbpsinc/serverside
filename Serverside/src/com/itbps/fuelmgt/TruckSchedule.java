package com.itbps.fuelmgt;

import java.util.Date;

public class TruckSchedule
{
	private int pickupdispatchid;
	private int compartmentid;
	private double scheduledquantity;
	private String fuelname;
	private Date createddate;
	
	public int getPickupdispatchid()
	{
		return pickupdispatchid;
	}
	
	public void setPickupdispatchid(int pickupdispatchid)
	{
		this.pickupdispatchid = pickupdispatchid;
	}
	
	public int getCompartmentid()
	{
		return compartmentid;
	}
	
	public void setCompartmentid(int compartmentid)
	{
		this.compartmentid = compartmentid;
	}
	
	public double getScheduledquantity()
	{
		return scheduledquantity;
	}
	
	public void setScheduledquantity(double scheduledquantity)
	{
		this.scheduledquantity = scheduledquantity;
	}
	
	public String getFuelname()
	{
		return fuelname;
	}
	
	public void setFuelname(String fuelname)
	{
		this.fuelname = fuelname;
	}
	
	public Date getCreateddate()
	{
		return createddate;
	}
	
	public void setCreateddate(Date createddate)
	{
		this.createddate = createddate;
	}
	
}
