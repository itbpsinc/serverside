package com.itbps.jersey;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="todo")
public class Todo {

	private String id;
	private String summary;
	private String description;
    private Date   dob;
    
	public Todo() {

	}

	
	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public Todo(String id, String summary) {
		this.id = id;
		this.summary = summary;
	}
	public Todo(String id, String summary, String desc) {
		this.id = id;
		this.summary = summary;
		this.description = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
