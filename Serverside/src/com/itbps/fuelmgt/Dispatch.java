package com.itbps.fuelmgt;

import java.util.Date;

public class Dispatch
{
	
	private int pickupdispatchId;
	private int driverid;
	private String driver;
	private int disbatchid;
	private String dispatcher;
	private String terminalid;
	private String truckname;
	private Date scheduledate;
	private Date createddate;
	
	public int getPickupdispatchId()
	{
		return pickupdispatchId;
	}
	
	public void setPickupdispatchId(int pickupdispatchId)
	{
		this.pickupdispatchId = pickupdispatchId;
	}
	
	public int getDriverid()
	{
		return driverid;
	}
	
	public void setDriverid(int driverid)
	{
		this.driverid = driverid;
	}
	
	public String getDriver()
	{
		return driver;
	}
	
	public void setDriver(String driver)
	{
		this.driver = driver;
	}
	
	public int getDisbatchid()
	{
		return disbatchid;
	}
	
	public void setDisbatchid(int disbatchid)
	{
		this.disbatchid = disbatchid;
	}
	
	public String getDispatcher()
	{
		return dispatcher;
	}
	
	public void setDispatcher(String dispatcher)
	{
		this.dispatcher = dispatcher;
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
	
	public Date getCreateddate()
	{
		return createddate;
	}
	
	public void setCreateddate(Date createddate)
	{
		this.createddate = createddate;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
