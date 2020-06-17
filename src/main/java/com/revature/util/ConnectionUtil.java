package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static Connection conn = null;
	
	private ConnectionUtil() {
		super();
	}
	
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			try {
				conn = DriverManager.getConnection(
						"jdbc:oracle:thin:@revature.cz9umnffc33n.us-east-2.rds.amazonaws.com:1521:ORCL",
						"banking",
						"password");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Did not find Oracle JDBC Driver class");
		}
		return conn;
	}
}
