package com.itbps.fuelmgt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="itempickup")
public class ItemterminalpickupList
{
	private Itemterminalpickup[] pickupList;
	
	public Itemterminalpickup[] getPickupList()
	{
		return pickupList;
	}
	
	@XmlElement(name="data")
	public void setPickupList(Itemterminalpickup[] pickupList)
	{  
		this.pickupList = pickupList;
	}
	
}
