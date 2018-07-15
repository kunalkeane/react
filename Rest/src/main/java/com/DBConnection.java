package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/react", "root", "password");
		} catch (Exception e) {
			throw new RuntimeException("Database connection failed");
		}
	}
	
	public static void main(String args[]) {
		try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from comment");
			while (rs.next())
				System.out.println(rs.getInt("id") + "  " + rs.getString("text") + "  " + rs.getString("hashtag"));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}  
		
}
