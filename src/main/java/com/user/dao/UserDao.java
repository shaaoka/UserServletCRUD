package com.user.dao;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import com.user.model.User;



//建立 C R U D 方法
public class UserDao {

	private String url ="jdbc:sqlserver://localhost:1433;DatabaseName=Usersdb;encrypt=false";
	private String user ="shaaoka";
	private String password="123456";

	public UserDao() {
		
	}
	
	// 建立資料庫連線
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
	// 插入資料
	public void insertUser(User user) throws SQLException {
		String sqli = "INSERT INTO users(name,email,country) VALUES (?,?,?)";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqli)){
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setString(3,user.getCountry());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	//查詢 單筆id資料
	public User SelectUser(int id) {
		User user = null;
		String sqlid = "SELECT * FROM users WHERE ID = ?";
		// 1.建立連線 
		try ( Connection connection = getConnection();
				// 2.建立sql物件語句 
				PreparedStatement preparedStatement = connection.prepareStatement(sqlid);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// 3 執行查詢
			ResultSet rs = preparedStatement.executeQuery();

			// 4 處理ResultSet 回傳
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id,name, email, country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	//查詢 全部資料
	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		String sqlAll = "SELECT * FROM users";
		// 1.建立連線 
		try ( Connection connection = getConnection();
				// 2.建立sql物件語句 
				PreparedStatement preparedStatement = connection.prepareStatement(sqlAll);) {
			System.out.println(preparedStatement);
			// 3 執行查詢
			ResultSet rs = preparedStatement.executeQuery();

			// 4 處理ResultSet 回傳
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id, name, email, country));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	//刪除資料

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		String sqlD = "DELETE FROM users WHERE id = 1";
		try ( Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlD);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	//修改資料
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		String sqlu = "UPDATE users SET name = ?,email = ?,country = ? WHERE ID = ?";
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlu);) {
			statement.setString(1,user.getName());
			statement.setString(2,user.getEmail());
			statement.setString(3,user.getCountry());
			statement.setInt(4,user.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}


