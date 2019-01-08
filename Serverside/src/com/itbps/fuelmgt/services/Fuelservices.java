package com.itbps.fuelmgt.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

import com.auth0.jwt.interfaces.Claim;
import com.itbps.fuelmgt.Authval;
import com.itbps.fuelmgt.sql.SQLServices;
import com.itbps.utils.IUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * Servlet implementation class Fuelservices
 */
@WebServlet("/fuelservices")
public class Fuelservices extends HttpServlet
{
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Fuelservices()
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
		//doPost(request, response);
		// response.setContentType("text/plain");
		// response.sendError(HttpServletResponse.SC_FORBIDDEN, "No GET access.");
		
		String id  = request.getParameter("id");
		String json = new SQLServices().getAllItempickups();
		
		response.setContentType("application/json");
		response.getWriter().print(json);
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Get the Authorization header from the request
       try
       {
		String authorizationHeader = request.getHeader("Authorization");

		if (!IUtils.isTokenBasedAuthentication(authorizationHeader))
			throw new ServletException("No JWT token found in request headers");

		String token = authorizationHeader.substring(IUtils.AUTHENTICATION_SCHEME.length());

		if ( IUtils.isValidToken(token))
		{
			
			//Perform request
		}
		else
		   throw new Exception("Invalid token..   Security Vilation");
		

       }
       catch(Exception _exx)
       {
		throw new ServletException("No JWT Invalid token found in request headers");	
       }
	}

	
	
	

}
