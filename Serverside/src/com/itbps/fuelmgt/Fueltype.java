package com.itbps.fuelmgt;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Fueltype {
	private String fuelname;
	private String fueldescription;

	public String getFuelname() {
		return fuelname;
	}

	public void setFuelname(String fuelname) {
		this.fuelname = fuelname;
	}

	public String getFueldescription() {
		return fueldescription;
	}

	public void setFueldescription(String fueldescription) {
		this.fueldescription = fueldescription;
	}

}
