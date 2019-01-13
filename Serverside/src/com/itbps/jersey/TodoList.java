package com.itbps.jersey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="todo")
public class TodoList {
	
	List<Todo> todo = new ArrayList<Todo>();

	public  TodoList ()
	{   
		List<Todo> lst = new ArrayList<Todo>();
		Todo todo = new Todo("1", "Learn REST","Ths is the description");
		
		todo.setDob(new Date());
		lst.add(todo);
		
        todo = new Todo("2", "Learn REST II","Ths is the description");
		todo.setDob(new Date());
		
		lst.add(todo);
		
		todo = new Todo("3", "Learn REST III","Ths is the description");
		todo.setDob(new Date());
		
		lst.add(todo);
		
		this.todo = lst;
	}
	@XmlElement(name="data")
	public List<Todo> getList() {
		return todo;
	}
	public void setList(List<Todo> list) {
		this.todo = list;
	}

}
