package com.kh.jdbc.day04.student.common;

import java.sql.*;

public class JDBCTemplate {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL         = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER 		 = "student";
	private final String PASSWORD	 = "student";
	
	// 디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW들
	// 개발할 때 공통되는 설계 문제를 해결하기 위하여 사옹되는 패턴임
	// ==> 효율적인 방식을 위함
	// 패턴의 종류 : 생성 / 구조 / 행위 .. 패턴
	// 1. 생성 패턴 : 싱글톤 패턴, 추상 팩토리, 팩토리 메서드 ...
	// 2. 구조 패턴 : 컴포지트, 데코레이트, ...
	// 3. 행위 패턴 : 옵저버, 스테이트, 전략, 템플릿 메서드, ....
	
	/*
	 * 싱글톤 패턴
	 * public class Singletone{
	 * 		private static Singleton instance;
	 * 
	 * 		private Singletone() {}
	 * 
	 * 		public static Singletone getInstance() {
	 * 			if(instance == null)
	 * 				instance = new Singletone();
	 * 		}
	 * }
	 */
	
	// 싱글톤 패턴
	private static JDBCTemplate instance;
	private static Connection conn;
	
	public JDBCTemplate() {
	}

	public static JDBCTemplate getInstance() {
		// 무조건 딱 한번만 생성되고 없을 때만 생성한다
		// 이미 만들어져 있는지 체크
		if(instance == null) {
			// 안 만들어져 있으면 그거 사용
//			JDBC 객체 생성
			instance = new JDBCTemplate();
		}
		// 만들어져 있면 그거 사용
//		JDBC 객체
		return instance;
		
			
	}
	
	public Connection createConnection() {
//		Connection conn = null;
		
		try {
			if(conn == null || conn.isClosed()) {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				// DBCP(DataBase Connection Pool)
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;	
	}
	
	public void close() {
		if(conn != null) {
			try {
				if(conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
