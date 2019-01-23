package com.itbps.fuelmgt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeList
{
	private Employee[] employees;
	
	@XmlElement(name="data")
	public Employee[] getEmployees()
	{
		return employees;
	}


	public void setEmployees(Employee[] employees)
	{
		this.employees = employees;
	}
	
}
