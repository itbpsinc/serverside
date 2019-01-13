package com.itbps.jersey;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.glassfish.jersey.server.ResourceConfig;

@Path("/todos")
public class TodoResource extends ResourceConfig {
	
	public TodoResource()
	{
		packages("com.itbps.jersey");
	}
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path ("/getall")
	public TodoList getTodo() {
		Todo todo = TodoDao.instance.getModel().get("1");
		if (todo == null)
			throw new RuntimeException("Get: Todo with not found");
		return new TodoList();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Todo> todo) {
		Todo c = todo.getValue();
		return putAndGetResponse(c);
	}

	@DELETE
	public void deleteTodo() {
		Todo c = TodoDao.instance.getModel().remove(1);
		if (c == null)
			throw new RuntimeException("Delete: Todo with " + 1 + " not found");
	}

	private Response putAndGetResponse(Todo todo) {
		Response res = null;
		if (TodoDao.instance.getModel().containsKey(todo.getId())) {
			res = Response.noContent().build();
		} else {
			//res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		TodoDao.instance.getModel().put(todo.getId(), todo);
		return res;
	}
	
	@GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = TodoDao.instance.getModel().size();
        return String.valueOf(count);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newTodo(@FormParam("id") String id,
            @FormParam("summary") String summary,
            @FormParam("description") String description,
            @Context HttpServletResponse servletResponse) throws IOException {
        Todo todo = new Todo(id, summary);
        if (description != null) {
            todo.setDescription(description);
        }
        TodoDao.instance.getModel().put(id, todo);

        servletResponse.sendRedirect("../create_todo.html");
    }
    
    @Path("{todo}")
    public TodoResource getTodo(@PathParam("todo") String id) 
    {
        return null; //new TodoResource(uriInfo, request, id);
    }

}
