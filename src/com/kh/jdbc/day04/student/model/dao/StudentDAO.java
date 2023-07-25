package com.kh.jdbc.day04.student.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.jdbc.day04.student.common.JDBCTemplate;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentDAO {
//	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
//	private final String USER = "student";
//	private final String PASSWORD = "student";
	
	/* 
	 * 1. Checked Exception과 Unchecked Exception
	 * 2. 예외의 종류 Throwable - Exception(checked exception 한정)
	 * 3. 예외처리 처리 방법 : throws, try ~ catch 
	 */
	private Properties prop;
	
	public StudentDAO() {
		prop = new Properties();
		Reader reader;
		try {
			reader = new FileReader("resources/query.properties"); // try catch
			prop.load(reader); // load -> inputStream 넣어주기
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 1. Statement
	 * - createStatement() 메소드를 통해서 객체 생성
	 * - execute*()를 실행할 때 쿼리문이 필요함
	 * - 쿼리문을 별도로 컴파일 하지 않아서 단순 실행일 경우 빠름
	 * - ex) 전체정보조회
	 * 
	 * 2. PreparedStatement
	 * - Statement를 상속받아서 만들어진 인터페이스
	 * - prepareStatement() 메소들를 통해서 객체 생성하는데 이때 쿼리문 필요
	 * - 쿼리문을 미리 컴파일하여 캐싱한 후 재사용하는 구조
	 * - 쿼리문을 컴파일 할때 위치홀더(?)를 이용하여 값이 들어가는 부분을 표시한 후 쿼리문 실행전에
	 * 값을 셋팅해주어야함.
	 * - 컴파일 하는 과정이 있어 느릴 수 있지만 쿼리문을 반복해서 실행할 때는 속도가 빠름
	 * - 전달값이 있는 쿼리문에 대해서 SqlInjection을 방어할 수 있는 보안기능이 추가됨
	 * - ex) 아이디로 정보조회, 이름으로 정보조회
	 * 
	 */
	
//	public List<Student> selectAll() {
	public List<Student> selectAll(Connection conn) {
//		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		List<Student> sList = new ArrayList<Student>();
//		String query = "SELECT * FROM STUDENT_TBL";
		String query = prop.getProperty("selectAll");
		
		try {
//			Class.forName(DRIVER_NAME);
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = new JDBCTemplate().createConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			}
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				conn.close();
				stmt.close();
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}

	
//	public Student selectOneById(String studentId) {
	public Student selectOneById(Connection conn, String studentId) {
//		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Student student = new Student();
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		String query = prop.getProperty("selectOneById");
		
		try {
//			Class.forName(DRIVER_NAME);
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = new JDBCTemplate().createConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return student;
	}


//	public List<Student> selectAllByName(String studentName) {
	public List<Student> selectAllByName(Connection conn, String studentName) {
//		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Student student = new Student();
		List<Student> sList = new ArrayList<Student>();
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";
		String query = prop.getProperty("selectAllByName");
		
		try {
//			Class.forName(DRIVER_NAME);
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = new JDBCTemplate().createConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentName);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
		} 
//			catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}


//	public int insertStudent(Student student) {
	public int insertStudent(Connection conn, Student student) {
//		String query = "INSERT INTO STUDENT_TBL VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		String query = prop.getProperty("insertStudent");
		int result = -1;
		
//		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
//			Class.forName(DRIVER_NAME);
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = new JDBCTemplate().createConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, String.valueOf(student.getGender())); // char -> String으로 형변환해서 넣어주기
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate(); // 쿼리문 실행 빼먹지 않기
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
//				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


//	public int updateStudent(Student student) {
	public int updateStudent(Connection conn, Student student) {
//		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?";
		String query = prop.getProperty("updateStudent");
		int result = -1;
		
//		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
//			Class.forName(DRIVER_NAME);
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = new JDBCTemplate().createConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate(); // 쿼리문 실행 빼먹지 않기
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
//				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


//	public int deleteStudent(String studentId) {
	public int deleteStudent(Connection conn, String studentId) {
//		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		Student student = new Student();
//		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		String query = prop.getProperty("deleteStudent");
		
		try {
//			Class.forName(DRIVER_NAME);
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn = new JDBCTemplate().createConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
//				conn.close();
				pstmt.close();
			} catch (SQLException e) {
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
