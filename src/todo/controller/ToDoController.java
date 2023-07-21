package todo.controller;

import todo.model.dao.ToDoDAO;

public class ToDoController {
	
	private ToDoDAO toDoDao;
	
	public ToDoController() {
		toDoDao = new ToDoDAO();
	}

}
