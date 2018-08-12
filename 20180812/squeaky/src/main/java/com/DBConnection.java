package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://pundsgh620t31c:3306/react", "squeaky", "password");
		} catch (Exception e) {
			throw new RuntimeException("Database connection failed");
		}
	}
	
	public static void main(String args[]) {
		/*try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from comment");
			while (rs.next())
				System.out.println(rs.getInt("id") + "  " + rs.getString("text") + "  " + rs.getString("hashtag"));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}*/ new DBConnection().foo();
	}  
	
	void foo(){
		List<B> list = new ArrayList<>();
		list.add(new B("a"));
		list.add(new B("b"));
		
		for(B b : list){
			b = new B("c");
			System.out.println(b.getStr());
		}
		
		for(B b : list){
			System.out.println(b.getStr());
		}
	}
	
	class B{
		String str;

		public B(String str) {
			super();
			this.str = str;
		}

		public String getStr() {
			return str;
		}
		
	}
		
}
