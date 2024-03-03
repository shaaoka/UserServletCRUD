package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//建立 C R U D 方法
public class UserDao {
	
	private String url ="jdbc:sqlserver://localhost:1433;DatabaseName=Usersdb;encrypt=false";
	private String user ="shaaoka";
	private String password="123456";
		
	// 建立連線
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}
