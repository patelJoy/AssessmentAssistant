package com.vgec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.vgec.bean.FacultyInfo;
import com.vgec.util.DB_Connection;

public class FacultyInfoDAO {
	public static ArrayList<FacultyInfo> getFaculties(String dept){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<FacultyInfo> lfi = new ArrayList<FacultyInfo>();
		
		String query = "select * from faculty_info where faculty_department=?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, dept);
			rs = ps.executeQuery();
			
			FacultyInfo fi = null;
			while(rs.next()) {
				fi = new FacultyInfo();
				fi.setId(rs.getInt("faculty_id"));
				fi.setName(rs.getString("faculty_name"));
				fi.setUsername(rs.getString("faculty_username"));
				fi.setDepartment(rs.getString("faculty_department"));
				lfi.add(fi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return lfi;
	}
	
	public static void deleteFaculty(int id) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		String query1 = "delete from faculty_info where faculty_id=?";
		try {
			ps1 = con.prepareStatement(query1);
			ps1.setInt(1, id);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps1.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	public static void addFaculty(FacultyInfo fi) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		int rs = 0;
		
		String query = "insert into faculty_info(faculty_name, faculty_username, faculty_password, faculty_department) values (?,?,?,?)";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, fi.getName());
			ps.setString(2, fi.getUsername());
			ps.setString(3, fi.getPassword());
			ps.setString(4, fi.getDepartment());
			rs = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}
