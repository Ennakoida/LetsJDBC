package com.kh.jdbc.day02.student.model.dao;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day02.student.model.vo.Student;

public class StudentDAO { // DAO : DB를 가져와서 객체화 시킴
	// 중복해서 사용
	// private final  -> 상수로 만들어준다 (-> 명명규칙 : 대문자)
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // localhost : ip / 1521 : port / XE : DB Name
	private final String USER = "student";
	private final String PASSWORD = "student";
	private String query = "";
	
//	public static void main(String[] args) {
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제 (close())
		 */
		query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME); // ClassNotFoundException try catch
			
			// 2. DB 연결 등록 (DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // SQLException try catch
			
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query); // Select일때만!!! ResultSet을 사용한다. 아니면 사용X
														// Insert는 int 를 사용한다
			
			sList = new ArrayList<Student>();
			// 후처리
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
				
//				System.out.printf("아이디 : %s, 이름 : %s\n", rset.getString("STUDENT_ID"), rset.getString("STUDENT_NAME")); // .getString(칼럼명)
			}
			
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sList;
	}

	
	public Student selectOneById(String studentId) {
		query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		Student student = null;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query); // SELECT > ResultSet, executeQuery
			
//			while(rset.next()) { // 값이 딱 1개면 while이 아니라 if로 처리해도 된다
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
			
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}


	public List<Student> selectAllByName(String studentName) {
		query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME LIKE '" + studentName + "'";
		List<Student> sList = null;
		Student student = null;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			sList = new ArrayList<Student>();
			
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sList;
	}
	
	public int insertStudent(Student student) {
			/*
			 * 1. 드라이버 등록
			 * 2. DB 연결 생성
			 * 3. 쿼리문 실행 준비
			 * 4. 쿼리문 실행 및 5. 결과 받기
			 * 6. 자원 해제
			 */
			
			
			// INSERT INTO STUDENT_TBL VALUES ('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678', '서울시 중구 남대문로 120', '독서, 수영', DEFAULT);
			// String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getStudentId()+"', '"+student.getStudentPwd()+"', '"+student.getStudentName()+"', '"+student.getGender()+"', "+student.getAge()+", '"+student.getEmail()+"', '"+student.getPhone()+"' , '"+student.getAddress()+"', '"+student.getHobby()+"', SYSDATE)";
			query = "INSERT INTO STUDENT_TBL VALUES (" 
					+ "'"+ student.getStudentId() + "', "
							+ "'" + student.getStudentPwd() + "', "
									+ "'" + student.getStudentName() + "', "
											+ "'" + student.getGender() + "', "
													+ "" + student.getAge() + " , "
															+ "'" + student.getEmail() + "', "
																	+ "'" + student.getPhone() + "', "
																			+ "'" + student.getAddress() + "', "
																					+ "'" + student.getHobby() + "', "
																							+ "DEFAULT)"; 
			int result = -1;
			
			try {
				// 1. 드라이버 등록
				Class.forName(DRIVER_NAME); // ClassNotFoundException try catch
				
				// 2. DB 연결 생성
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // SQLException try catch
				
				// 3. 쿼리 실행 준비
				Statement stmt = conn.createStatement();
				
				// 4. 실행하고 5. 결과받기
	//			.executeQuery(query) : SELECT용
	//			.executeUpdate(query) : DML(INSERT, UPDATE, DELETE)용 // -> return : rowcount / 0
				result = stmt.executeUpdate(query);
				
				// 6. 자원 해제
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}


	public int updateStudent(Student student) {
		// UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass11', EMAIL = 'khuser11@iei.or.kr', PHONE = '01011111111', ADDRESS = '서울시 강남구', HOBBY = '코딩,수영' WHERE STUDENT_ID = 'khuser01';
		query = "UPDATE STUDENT_TBL SET "
				+ "STUDENT_PWD = '" + student.getStudentPwd() + "'"
						+ ", EMAIL = '" + student.getEmail() + "'"
								+ ", PHONE = '" + student.getPhone() + "'"
										+ ", ADDRESS = '" + student.getAddress() + "'"
												+ ", HOBBY = '" + student.getHobby() + "' "
														+ "WHERE STUDENT_ID = '" + student.getStudentId() + "'";
		int result = 0;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}


	public int deleteStudent(String studentId) {
		query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '" +  studentId + "'";
		int result = 0;
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}


	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student(); // 객체 생성 (rset에서 가져온 값을 객체에 넣어 저장)
		// rset.getString("STUDENT_ID") : 가져온 값
		student.setStudentId(rset.getString("STUDENT_ID")); // set - column name 맞춰주기
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setGender(rset.getString("GENDER").charAt(0)); // char : String을 charAt()메소드를 이용하여 문자로 잘라서 사용
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));	
		
		return student;
	}
}
