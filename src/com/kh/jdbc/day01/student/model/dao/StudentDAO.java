package com.kh.jdbc.day01.student.model.dao;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO { // DAO : DB를 가져와서 객체화 시킴
	
//	public static void main(String[] args) {
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제 (close())
		 */
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE"; // localhost : ip / 1521 : port / XE : DB Name
		String user = "student";
		String password = "student";
		String query = "SELECT * FROM STUDENT_TBL";
		
		List<Student> sList = null;
		Student student = null;
		
		try {
			// 1. 드라이버 등록
			Class.forName(driverName); // ClassNotFoundException try catch
			
			// 2. DB 연결 등록 (DriverManager)
			Connection conn = DriverManager.getConnection(url, user, password); // SQLException try catch
			
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query); // Select일때만!!! ResultSet을 사용한다. 아니면 사용X
														// Insert는 int 를 사용한다
			
			sList = new ArrayList<Student>();
			// 후처리
			while(rset.next()) {
				student = new Student(); // 객체 생성 (rset에서 가져온 값을 객체에 넣어 저장)
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
}
