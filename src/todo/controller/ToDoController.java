package todo.controller;

import java.util.List;

import todo.model.dao.ToDoDAO;
import todo.model.vo.ToDo;

public class ToDoController {
	
	private ToDoDAO toDoDao;
	
	public ToDoController() {
		toDoDao = new ToDoDAO();
	}

	public int insertToDo(ToDo toDo) {
		int result = toDoDao.insertToDo(toDo);
		return result;
	}

	public List<ToDo> selectToDo() {
		List<ToDo> tdList = toDoDao.selectToDo();
		return tdList;
	}

}
