package com.itbps.fuelmgt;

public class Itemdelivery {
	private int itemdeliveryid;
	private int siteid;
	private String truckname;
	private String billoflading;
	private int compactmentid;
	private double qtyscheduled;
	private double qtydelivered;

	public int getItemdeliveryid() {
		return itemdeliveryid;
	}

	public void setItemdeliveryid(int itemdeliveryid) {
		this.itemdeliveryid = itemdeliveryid;
	}

	public int getSiteid() {
		return siteid;
	}

	public void setSiteid(int siteid) {
		this.siteid = siteid;
	}

	public String getTruckname() {
		return truckname;
	}

	public void setTruckname(String truckname) {
		this.truckname = truckname;
	}

	public String getBilloflading() {
		return billoflading;
	}

	public void setBilloflading(String billoflading) {
		this.billoflading = billoflading;
	}

	public int getCompactmentid() {
		return compactmentid;
	}

	public void setCompactmentid(int compactmentid) {
		this.compactmentid = compactmentid;
	}

	public double getQtyscheduled() {
		return qtyscheduled;
	}

	public void setQtyscheduled(double qtyscheduled) {
		this.qtyscheduled = qtyscheduled;
	}

	public double getQtydelivered() {
		return qtydelivered;
	}

	public void setQtydelivered(double qtydelivered) {
		this.qtydelivered = qtydelivered;
	}

}
