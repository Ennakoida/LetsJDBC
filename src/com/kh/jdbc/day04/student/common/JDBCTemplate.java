package com.kh.jdbc.day04.student.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
	
	private Properties prop;
	
//	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	private final String URL         = "jdbc:oracle:thin:@localhost:1521:XE";
//	private final String USER 		 = "student";
//	private final String PASSWORD	 = "student";
	
	// 디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW들
	// 개발할 때 공통되는 설계 문제를 해결하기 위하여 사용되는 패턴임
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
	
	
	// DBCP(DataBase Connection Pool)
	public Connection createConnection() {
//		Connection conn = null;
		try {
			prop = new Properties();
			Reader reader = new FileReader("resources/dev.properties"); // FileNotFoundException 예외 처리
			prop.load(reader); // IOException 예외처리
			String driverName = prop.getProperty("driverName");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			if(conn == null || conn.isClosed()) {
//				Class.forName(DRIVER_NAME);
				Class.forName(driverName);
//				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false); // 오토 커밋 풀어주세요
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;	
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.commit(); // try catch
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.rollback(); // try catch
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
