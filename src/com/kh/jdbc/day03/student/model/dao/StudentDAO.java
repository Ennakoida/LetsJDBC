package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "student";
	private final String PASSWORD = "student";
	
	public List<Student> selectAll() {
		String query = "SELECT * FROM STUDENT_TBL"; // SELECT -> ResultSet, executeQuery()
		List<Student> sList = new ArrayList<Student>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			// 여러번 반복해야하니 while문
			while(rset.next()) { // rset 은 next() 가 꼭 필요하다.
				Student student = rsetToStudent(rset);
				sList.add(student); // table의 모든 row를 sList에 담겠다
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// close()는 finally에 넣어준다
				// finally에 쓰기 위해 rset, stmt, conn은 전부 전역변수로 만들어준다
				// try catch 해줘야함
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}
	
	public List<Student> selectAllByName(String studentName) {
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME LIKE '" + studentName + "'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME LIKE ?"; // pstmt
		List<Student> sList = new ArrayList<Student>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentName);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sList;
	}

	public Student selectOneById(String studentId) {
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?"; 
		Student student = new Student();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return student;
	}
	
	public Student selectLoginInfo(Student student) {
		/*
		 * Statement to PreparedStatement
		 * 1. 위치홀더(= ?) 셋팅
		 * 2. PreparedStatement 객체 생성 with query
		 * 3. 입력값 셋팅
		 * 4. 쿼리문 실행 및 결과 받기 (feat. method())
		 */
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + student.getStudentId() + "' AND STUDENT_PWD = '" + student.getStudentPwd() + "'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?"; // pstmt
		Student result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query); // stmt 대신 pstmt 사용함 -> SQL Injection 방지
			pstmt.setString(1,  student.getStudentId()); // 검증 // 시작은 1
			pstmt.setString(2, student.getStudentPwd()); // 마지막 수 (-> 2) 는 query의 ?(= 위치홀더) 개수와 동일해야한다.
			rset = pstmt.executeQuery(); // pstmt에서 이미 query를 받아왔기 때문에 executeQuery()에 query 필요X
			
			if(rset.next()) {
				result = rsetToStudent(rset);
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int insertStudent(Student student) {
//		String query = "INSERT INTO STUDENT_TBL VALUES (" 
//				+ "'"+ student.getStudentId() + "', "
//						+ "'" + student.getStudentPwd() + "', "
//								+ "'" + student.getStudentName() + "', "
//										+ "'" + student.getGender() + "', "
//												+ "" + student.getAge() + " , "
//														+ "'" + student.getEmail() + "', "
//																+ "'" + student.getPhone() + "', "
//																		+ "'" + student.getAddress() + "', "
//																				+ "'" + student.getHobby() + "', "
//																						+ "DEFAULT)";
		
		String query = "INSERT INTO STUDENT_TBL VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)"; 
		int result = -1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
//			pstmt.setString(4, student.getGender()+"");
			pstmt.setString(4, String.valueOf(student.getGender())); // char -> String으로 형변환해서 넣어주기
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate(); // 쿼리문 실행 빼먹지 않기

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateStudent(Student student) {
		// UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass11', EMAIL = 'khuser11@iei.or.kr', PHONE = '01011111111', ADDRESS = '서울시 강남구', HOBBY = '코딩,수영' WHERE STUDENT_ID = 'khuser01';
//		String query = "UPDATE STUDENT_TBL SET "
//				+ "STUDENT_PWD = '" + student.getStudentPwd() + "'"
//						+ ", EMAIL = '" + student.getEmail() + "'"
//								+ ", PHONE = '" + student.getPhone() + "'"
//										+ ", ADDRESS = '" + student.getAddress() + "'"
//												+ ", HOBBY = '" + student.getHobby() + "' "
//														+ "WHERE STUDENT_ID = '" + student.getStudentId() + "'";
		
		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?"; // pstmt
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteStudent(String studentId) {
//		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?"; // pstmt
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private Student rsetToStudent(ResultSet rset) throws SQLException { // 상위에서 예외처리
		Student student = new Student(); // 객체 생성 (rset에서 가져온 값을 객체에 넣어 저장)
		// rset.getString("STUDENT_ID") : 가져온 값
		student.setStudentId(rset.getString("STUDENT_ID")); // set - column name 맞춰주기 // columnNum을 알면 getString(1) 도 가능
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setGender(rset.getString("GENDER").charAt(0)); // char : String을 charAt()메소드를 이용하여 문자로 잘라서 사용
		student.setAge(rset.getInt("AGE")); // Student.java의 age가 int 라서 getInt
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));	
		
		return student;
	}
}
