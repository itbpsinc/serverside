package com.itbps.fuelmgt.services;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.itbps.fuelmgt.Authval;
import com.itbps.fuelmgt.Employee;
import com.itbps.fuelmgt.sql.SQLServices;
import com.itbps.utils.IUtils;

/**
 * Servlet implementation class Fuellogin
 */
@WebServlet("/fuellogin")
public class Fuellogin extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private final String VALID_USER = "Austin:password";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Fuellogin()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// response.setContentType("text/plain");
		// response.sendError(HttpServletResponse.SC_FORBIDDEN, "No GET access.");
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		try
		{
			
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
			
			String data = builder.toString();
			
			JSONObject object = null;
			
			object = new JSONObject(data);
			String name = object.getString("loginid");
			String pass = object.getString("password");
			
			response.setContentType("application/json");
			if (name == null)
			{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				String json = String.format("{\"error\": \"%s\"}", "Username is required");
				response.getWriter().print(json);
				return;
			}
			
			if (pass == null)
			{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				String json = String.format("{\"error\": \"%s\"}", "Password is required");
				response.getWriter().print(json);
				return;
			}
			
			Employee emp = SQLServices.getEmployee(name);
			
			String vuser = emp.getNameid().trim().toLowerCase() + ":" + emp.getPassword().trim();
			if ((name.trim().toLowerCase() + ":" + pass.trim()).equals(vuser))
			{
				Authval auth = new Authval();
				auth.setExpiration(10000);
				auth.setId("JWT-09");
				
				auth.setIssue("ITBPS Inc");
				
				auth.setLoginid(emp.getNameid().trim());
				auth.setName(emp.getFirstName() + " " + emp.getLastName());
				auth.setRole(emp.getRole());
				
				String token = IUtils.createJWT(auth);
				
				JSONObject jsons = new JSONObject();
				jsons.put("token", token);
				
				response.getWriter().print(jsons.toString());
			} else
			{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				String json = String.format("{\"error\": \"%s\"}", "Username or Password is invalid");
				response.getWriter().print(json);
			}
			
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		}
		
	}
	
}
