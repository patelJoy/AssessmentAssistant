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
	
	public static ArrayList<StudentInfo> resultStudentList(int tid) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<StudentInfo> lsi = new ArrayList<StudentInfo>();
		
		String query = "select a.a_erno, s.student_name, a.a_markslist from answers_"+tid+" as a INNER JOIN student_info as s ON a.a_erno=s.student_username where a.attempted=1";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			StudentInfo si = null;
			while(rs.next()) {
				si = new StudentInfo();
				si.setErno(rs.getString(1));
				si.setName(rs.getString(2));
				String[] parts = rs.getString(3).split(",");
				int sum = 0;
				int[] ints = new int[parts.length];
				for (int i = 0; i < parts.length; i++) {
				    sum += Integer.parseInt(parts[i]);
				}
				si.setMarks(sum);
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
	
	public static void deleteStudentBatch(int batch, String dept) {
		int batchSize = 20;
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select test_id from test where test_batch=? and test_department=?";
		ArrayList<Integer> testIds = new ArrayList<Integer>();
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, batch);
			ps.setString(2, dept);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				testIds.add(rs.getInt(1));
			}
			int size = testIds.size();
			if(size != 0) {
				String query1 = "drop table IF EXISTS ";
				for(int i=0;i<size;i++) {
					query1 += "questions_" + testIds.get(i);
					query1 += ", ";
					query1 += "answers_" + testIds.get(i);
					if(i != (size - 1)) {
						query1 += ", ";
					} else {
						query1 += ";";
					}
				}
				con.prepareStatement(query1).executeUpdate();
			
				String query2 = "delete from test where test_id=?";
				PreparedStatement ps1 = con.prepareStatement(query2);
				con.setAutoCommit(false);
				for(int i=0;i<size;i++) {
					ps1.setInt(1, testIds.get(i));
					ps1.addBatch();
					if(i % batchSize == 0) {
						ps1.executeBatch();
					}
				}
				ps1.executeBatch();
				con.commit();
			}
			con.setAutoCommit(true);
			String query3 = "delete from student_info where student_batch=?";
			PreparedStatement ps2 = con.prepareStatement(query3);
			ps2.setInt(1, batch);
			ps2.executeUpdate();
			
			
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
