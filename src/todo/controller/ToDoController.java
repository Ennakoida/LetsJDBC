package todo.controller;

import java.util.List;

import todo.model.dao.ToDoDAO;
import todo.model.vo.ToDo;

public class ToDoController {
	
	private ToDoDAO toDoDao;
	
	public ToDoController() {
		toDoDao = new ToDoDAO();
	}

	public List<ToDo> insertToDo(ToDo toDo) {
		List<ToDo> tdList = toDoDao.insertToDo(toDo);
		return tdList;
	}

}
