package todo.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import todo.model.vo.ToDo;

public class ToDoDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "student";
	private final String PASSWORD = "student";
	
	// 할 일 추가
	public int insertToDo(ToDo toDo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO TODO_TBL VALUES(SEQ_TODO_NO.NEXTVAL, ?, ?, ?, DEFAULT)";
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, toDo.getList());
			pstmt.setString(2, toDo.getDue());
			pstmt.setInt(3, toDo.getdDay());
			result = pstmt.executeUpdate();
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 할 일 전체 조회
	public List<ToDo> selectToDo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<ToDo> tdList = new ArrayList<ToDo>();
		String query = "SELECT * FROM TODO_TBL";
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ToDo toDo = rset2ToDo(rset);
				tdList.add(toDo);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tdList;
	}

	private ToDo rset2ToDo(ResultSet rset) throws SQLException {
		ToDo toDo = new ToDo();
		toDo.setIndex(rset.getInt("TODO_INDEX"));
		toDo.setList(rset.getString("TODO_LIST"));
		toDo.setDue(rset.getString("DUE"));
		toDo.setdDay(rset.getInt("DDAY"));
		toDo.setToday(rset.getDate("TODAY"));
		return toDo;
	}
}
