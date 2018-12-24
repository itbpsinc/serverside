package com.itbps.fuelmgt;

import java.util.Date;

public class Itempickup {
	private int itemspickupid;
	private int dispatchpickupid;
	private int compartmentid;
	private String fuelname;
	private double qtypickup;
	private double qtyscheduled;
	private String billoflading;
	private Date pickupdate;
	private Date createdate;
	private Date createdated;
	private String lastupdateduser;

	public int getItemspickupid() {
		return itemspickupid;
	}

	public void setItemspickupid(int itemspickupid) {
		this.itemspickupid = itemspickupid;
	}

	public int getDispatchpickupid() {
		return dispatchpickupid;
	}

	public void setDispatchpickupid(int dispatchpickupid) {
		this.dispatchpickupid = dispatchpickupid;
	}

	public int getCompartmentid() {
		return compartmentid;
	}

	public void setCompartmentid(int compartmentid) {
		this.compartmentid = compartmentid;
	}

	public String getFuelname() {
		return fuelname;
	}

	public void setFuelname(String fuelname) {
		this.fuelname = fuelname;
	}

	public double getQtypickup() {
		return qtypickup;
	}

	public void setQtypickup(double qtypickup) {
		this.qtypickup = qtypickup;
	}

	public double getQtyscheduled() {
		return qtyscheduled;
	}

	public void setQtyscheduled(double qtyscheduled) {
		this.qtyscheduled = qtyscheduled;
	}

	public String getBilloflading() {
		return billoflading;
	}

	public void setBilloflading(String billoflading) {
		this.billoflading = billoflading;
	}

	public Date getPickupdate() {
		return pickupdate;
	}

	public void setPickupdate(Date pickupdate) {
		this.pickupdate = pickupdate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedated() {
		return createdated;
	}

	public void setCreatedated(Date createdated) {
		this.createdated = createdated;
	}

	public String getLastupdateduser() {
		return lastupdateduser;
	}

	public void setLastupdateduser(String lastupdateduser) {
		this.lastupdateduser = lastupdateduser;
	}

}
