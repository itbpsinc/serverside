package com.itbps.fuelmgt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverList
{
	private Driver[] drivers;
	
	@XmlElement(name="data")
	public Driver[] getDrivers()
	{
		return drivers;
	}
	
	public void setDrivers(Driver[] drivers)
	{
		this.drivers = drivers;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
