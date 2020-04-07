package com.vgec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.vgec.bean.FacultyInfo;
import com.vgec.bean.StudentInfo;
import com.vgec.util.DB_Connection;

public class StudentInfoDAO {
	
	public static ArrayList<StudentInfo> adminViewStudent(String dept, int batch){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<StudentInfo> lsi = new ArrayList<StudentInfo>();
		
		String query = "select * from student_info where student_department=? and student_batch=?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, dept);
			ps.setInt(2,batch);
			rs = ps.executeQuery();
			
			StudentInfo si = null;
			while(rs.next()) {
				si = new StudentInfo();
				si.setName(rs.getString("student_name"));
				si.setUsername(rs.getString("student_username"));
				si.setEmail(rs.getString("student_email"));
				lsi.add(si);
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
		
		return lsi;
	}
	
	public static ArrayList<Integer> getBatches(String dept) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Integer> bat = new ArrayList<Integer>();
		
		String query = "select DISTINCT(student_batch) from student_info where student_department=?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, dept);
			rs = ps.executeQuery();
			
			while(rs.next()) {		
				bat.add(rs.getInt("student_batch"));
			}
			Collections.sort(bat);
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
		
		return bat;
	}
	
	public static void addStudent(StudentInfo fi) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		int rs = 0;
		
		String query = "insert into student_info(student_name, student_username, student_password, student_department, student_batch, student_email) values (?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, fi.getName());
			ps.setString(2, fi.getUsername());
			ps.setString(3, fi.getPassword());
			ps.setString(4, fi.getDepartment());
			ps.setInt(5, fi.getBatch());
			ps.setString(6, fi.getEmail());
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
