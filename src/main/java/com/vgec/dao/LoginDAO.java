package com.vgec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vgec.util.DB_Connection;
import com.vgec.util.Md5;

public class LoginDAO {
	public static ResultSet checkLogin(String role, String username,String password) {
		
		String pass = Md5.getMd5(password);
		ResultSet rs = null;
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		
		String query = null;
		if(role.equals("Admin")) {
			query = "select * from admin_info where admin_username=? and admin_password=?";
		} else if(role.equals("Faculty")) {
			query = "select * from faculty_info where faculty_username=? and faculty_password=?";
		} else {
			query = "select * from student_info where student_username=? and student_password=?";
		}
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, pass);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
}
