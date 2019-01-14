package com.itbps.user.security;

public class UserSecurity
{
	private String password = null;
	private String token = null;
	private String role = null;
	private String userId;
	private String firstName;
	private String lastName;
	private int id;
	
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
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public UserSecurity()
	{
	}
	
	public UserSecurity(String password, String token)
	{
		this.password = password;
		this.token = token;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public UserSecurity(String password, String token, String role)
	{
		this.password = password;
		this.token = token;
		this.role = role;
	}
	
	public UserSecurity(String userId, String password, String token, String role)
	{
		this.setUserId(userId);
		this.password = password;
		this.token = token;
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
	
	public String getToken()
	{
		return token;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public String getRole()
	{
		return role;
	}
	
	public void setRole(String role)
	{
		this.role = role;
	}
	
	@Override
	public String toString()
	{
		return "UserSecurity [id=" + this.getUserId() + ", role=" + role + ", password=" + password + ", token=" + token + "]";
	}
}
