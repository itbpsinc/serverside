package com.itbps.fuelmgt.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

import com.itbps.fuelmgt.Itemterminalpickup;
import com.itbps.fuelmgt.ItemterminalpickupList;
import com.itbps.fuelmgt.sql.SQLServices;

@Path("/services")
public class Fuelservices
{
	/*
	 * @GET
	 * 
	 * @Path("/{name}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getMessage(@PathParam("name") String name) { String outMsg = "Hello " + name
	 * + "!"; return Response.status(200).entity(outMsg).build(); }
	 * 
	 */
	@GET
	@Path("/ipickup/driver/{id}")
	@Produces(
	{ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ItemterminalpickupList getDriverItemPickup(@PathParam("id") int id)
	{
		ItemterminalpickupList json = null;
		try
		{
			json = new SQLServices().getItempickupByDriverId(id);
			
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		}
		
		return json;
	}
	
	@GET
	@Path("/ipickup/{id}")
	@Produces(
	{ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Itemterminalpickup getItemPickupById(@PathParam("id") int id)
	{
		Itemterminalpickup itemterminalpickup = null;
		try
		{
			itemterminalpickup = new SQLServices().getItempickupById(id);
			
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		}
		
		return itemterminalpickup;
	}
	
	@GET
	@Path("/ipickup/dispatchr/{id}")
	@Produces(
	{ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ItemterminalpickupList getDispatchItemPickup(@PathParam("id") int id)
	{
		ItemterminalpickupList json = null;
		try
		{
			json = new SQLServices().getItempickuphByDispatchId(id);
			
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		}
		
		return json;
	}
	
	@GET
	@Path("/ipickup")
	@Produces(
	{ MediaType.APPLICATION_XML, MediaType.APPLICATION_XML })
	public ItemterminalpickupList getItemPickup()
	{
		ItemterminalpickupList json = null;
		try
		{
			json = new SQLServices().getAllItempickups();
			
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		}
		
		return json;
	}
	
}
