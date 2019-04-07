package com.itbps.fuelmgt;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FueltypeList
{
	private Fueltype[] fueltypes;
	
	public Fueltype[] getFueltypes()
	{
		return fueltypes;
	}
	
	public void setFueltypes(Fueltype[] fueltypes)
	{
		this.fueltypes = fueltypes;
	}
	
}
