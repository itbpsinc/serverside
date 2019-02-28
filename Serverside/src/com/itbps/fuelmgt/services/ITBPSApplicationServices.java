package com.itbps.fuelmgt.services;





import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.core.Response;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.JsonParser;
import com.itbps.exception.UserExistingException;
import com.itbps.exception.UserNotFoundException;
import com.itbps.fuelmgt.Authval;
import com.itbps.fuelmgt.Employee;
import com.itbps.fuelmgt.EmployeeList;
import com.itbps.fuelmgt.ItemterminalpickupList;
import com.itbps.fuelmgt.sql.SQLServices;
import com.itbps.jersey.Todo;
import com.itbps.jersey.TodoDao;
import com.itbps.jersey.TodoList;
import com.itbps.rest.ResponseBuilder;
import com.itbps.user.model.Credentials;
import com.itbps.user.security.AuthenticationFilter;
import com.itbps.user.security.PasswordSecurity;
import com.itbps.user.security.UserSecurity;
import com.itbps.utils.IUtils;

@DeclareRoles({"Admin", "user", "guest","driver","dispatcher","accountant"})
@Path("/itbps")
public class ITBPSApplicationServices extends  ResourceConfig 
{
	
	@Context private HttpServletRequest webRequest;
	
	public ITBPSApplicationServices() {
        packages( "com.itbps.fuelmgt.services" );
        
		register( AuthenticationFilter.class );
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path ("/allpickups")
	@PermitAll
	public ItemterminalpickupList getAllItempickups() 
	{
		ItemterminalpickupList pickuplist  = new SQLServices().getAllItempickups();
		return pickuplist;
	}
	
	@POST
	@Path("/create")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(UserSecurity usersecurity)
	{
		
		try
		{
			SQLServices service = new SQLServices();
			UserSecurity security = service.getUserForSecurity(usersecurity.getUserId());
			if(security == null)
			{
				usersecurity.setRole("user");
				 String plainPassword = usersecurity.getPassword();
				 usersecurity.setPassword(PasswordSecurity.generateHash(plainPassword));
				 System.out.println("Password size: " + usersecurity.getPassword().length());
				 usersecurity = service.createEmployee(usersecurity);
				 return authenticate( new Credentials(usersecurity.getUserId(), plainPassword ) );
				 
				 
			}
			else
				throw new UserExistingException(security.getUserId());
		}
		catch ( UserExistingException e ) {
			return ResponseBuilder.createResponse( Response.Status.CONFLICT, e.getMessage() );
		}
		catch ( Exception e ) {
			return ResponseBuilder.createResponse( Response.Status.INTERNAL_SERVER_ERROR );
		}
		
   }
	
	@POST
	@Path("/authenticate")
	@PermitAll
	@Produces("application/json")
	@Consumes("application/json")
	
	public Response authenticate( Credentials credentials ) 
	{
		
		try {
			
			UserSecurity userSecurity = SQLServices.getUserForSecurity(credentials.getUserid());
			if (userSecurity != null)
				userSecurity.setToken((String)webRequest.getSession(true).getAttribute("token"));
			
			if( PasswordSecurity.validatePassword( credentials.getPassword(), userSecurity.getPassword() ) == false ) {
				return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
			}

			// generate a token for the user
			Authval val = new Authval();
			val.setExpiration(600000);
			val.setId("JWT-09");
		//	val.setId(String.valueOf(userSecurity.getId()));
			val.setIssue("ITBPS INC");
			val.setLoginid(userSecurity.getUserId());
			val.setName(userSecurity.getUserId());
			val.setRole(userSecurity.getRole());
			
			
			String token = IUtils.createJWT(val);
			
			// write the token to the database
			UserSecurity sec = new UserSecurity( null, token );
			//sec.setId( id );
		//	userDao.setUserAuthentication( sec );
			webRequest.getSession(true).setAttribute("token", token);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put( AuthenticationFilter.TOKEN_PROPERTY.trim(), token );
			
			// Return the token on the response
			return ResponseBuilder.createResponse( Response.Status.OK, map );
		}
		catch( UserNotFoundException e ) {
			return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
		}
		catch( Exception e ) {
			return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
		}
		
	}
	
	@GET
	@Path("/employeeList")
	@PermitAll
	
	@Produces("application/json")
	public Response employeeList()
	{
		//@RolesAllowed({ "Admin" })
		try
		{
		   EmployeeList  empList = new SQLServices().getEmployees();
		   return ResponseBuilder.createResponse(Response.Status.OK, empList);
		   
		}
		catch( Exception e ) {
			return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
		}
		
	}
	
	@POST
	@Path("/adddEmployee")
	@RolesAllowed({ "Admin" })
	
	
	@Produces("application/json")
	public Response createEmployee(Employee employee)
	{
		//@RolesAllowed({ "Admin" })
		Employee  emp = null;
		try
		{
		   if (employee.getId() <= 0)
		      emp = new SQLServices().addEmployee(employee);
		   else
			  emp = new SQLServices().updateEmployee(employee);
		   
		   emp.setPassword(null);
		   return ResponseBuilder.createResponse(Response.Status.OK, emp);
		   
		}
		catch( Exception e ) 
		{
			return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
		}
		
	}
	
	
	
	
}