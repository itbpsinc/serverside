package com.itbps.fuelmgt;

import java.util.Date;

public class Employee
{
	private int id;
	private String nameid;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private Date dateofhire;
	private String ssn;
	private String password;
	private String role;
	private boolean active;
	
	
	
	
	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getRole()
	{
		return role;
	}
	
	public void setRole(String role)
	{
		this.role = role;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getNameid()
	{
		return nameid;
	}
	
	public void setNameid(String nameid)
	{
		this.nameid = nameid;
	}
	
	public String getAddress1()
	{
		return address1;
	}
	
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
	
	public String getAddress2()
	{
		return address2;
	}
	
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	
	public String getZipcode()
	{
		return zipcode;
	}
	
	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public Date getDateofhire()
	{
		return dateofhire;
	}
	
	public void setDateofhire(Date dateofhire)
	{
		this.dateofhire = dateofhire;
	}
	
	public String getSsn()
	{
		return ssn;
	}
	
	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}
	
}
