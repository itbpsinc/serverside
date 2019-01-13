package com.itbps.fuelmgt.services;





import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;

import com.itbps.fuelmgt.ItemterminalpickupList;
import com.itbps.fuelmgt.sql.SQLServices;
import com.itbps.jersey.Todo;
import com.itbps.jersey.TodoDao;
import com.itbps.jersey.TodoList;
import com.itbps.user.security.AuthenticationFilter;

@Path("/itbps")
public class ITBPSApplicationServices extends  ResourceConfig 
{
	
	public ITBPSApplicationServices() {
        packages( "om.itbps.fuelmgt.services" );
        
		register( AuthenticationFilter.class );
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path ("/getallpickup")
	@RolesAllowed({"admin"})
	public ItemterminalpickupList getAllItempickups() 
	{
		ItemterminalpickupList pickuplist  = new SQLServices().getAllItempickups();
		return pickuplist;
	}
	
	
}