package todo.model.vo;

import java.sql.Date;

public class ToDo {
	private int index;
	private String list;
	private String due;// 마감일
	private int dDay; // d-day
	private Date today; 
	
	public ToDo() {
	}

	public ToDo(int index, String list) {
		super();
		this.index = index;
		this.list = list;
	}
	
	public ToDo(String list, String due, int dDay) {
		super();
		this.list = list;
		this.due = due;
		this.dDay = dDay;
	}
	
	public ToDo(int index, String list, String due, int dDay) {
		super();
		this.index = index;
		this.list = list;
		this.due = due;
		this.dDay = dDay;
	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getList() {
		return list;
	}
	
	public void setList(String list) {
		this.list = list;
	}
	
	public String getDue() {
		return due;
	}
	
	public void setDue(String due) {
		this.due = due;
	}
	
	public int getdDay() {
		return dDay;
	}
	
	public void setdDay(int dDay) {
		this.dDay = dDay;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
	
	
	
}
