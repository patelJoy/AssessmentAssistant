package com.vgec.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connection {
	
	public static Connection getConnection() {
		
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assessment_assisstant", "root", "root");
		}catch(Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
