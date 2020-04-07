package com.vgec.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.vgec.bean.FacultyInfo;
import com.vgec.bean.Test;
import com.vgec.util.DB_Connection;

public class TestDAO {
	
	public static ArrayList<Test> getFacultyTest(int id) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Test> ts = new ArrayList<Test>();
		
		String query = "select * from test where test_fid=?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			Test t = null;
			while(rs.next()) {
				t = new Test();
				t.setSubject(rs.getString("test_subject"));
				t.setCategory(rs.getString("test_category"));
				t.setTotalmarks(rs.getInt("test_totalmarks"));;
				t.setDate(Date.valueOf(rs.getString("test_date")));
				//t.setFromtime(new Time(new SimpleDateFormat("HH:MM:SS").parse(rs.getString("test_fromtime")).getTime()));
				//t.setFromtime(new Time(new SimpleDateFormat("HH:MM:SS").parse(rs.getString("test_totime")).getTime()));
				t.setDuration(rs.getInt("test_duration"));
				ts.add(t);
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
		return ts;
	}
	
	public static int createTest(Test t) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rs = 0;
		int rs1 = 0;
		
		
		String query = "insert into test(test_subject, test_duration, test_batch, test_date, test_fromTime, test_toTime, test_fid, test_category, test_totalmarks, test_department) values (?,?,?,?,?,?,?,?,?,?)";
		int testId = -1;
		try {
			String cat = null;
			if(t.getCategory().equals("1")) {
				cat = "Test";
			}else {
				cat = "Mid Sem";
			}
			ps = con.prepareStatement(query);
			ps.setString(1, t.getSubject());
			ps.setInt(2, t.getDuration());
			ps.setInt(3, t.getBatch());
			ps.setDate(4, t.getDate());
			ps.setTime(5, t.getFromtime());
			ps.setTime(6, t.getTotime());
			ps.setInt(7, t.getFid());
			ps.setString(8, cat);
			ps.setInt(9, t.getTotalmarks());
			ps.setString(10, t.getDepartment());
			rs = ps.executeUpdate();
			
			String query3 = "select max(test_id) from test";
			PreparedStatement ps3 = con.prepareStatement(query3);
			ResultSet rs3 = ps3.executeQuery();
			
			if(rs3.next()) {
				testId = rs3.getInt(1);
			}
			
			String st = "questions_" + String.valueOf(testId);
			String query1 = "CREATE TABLE "+ st +" (\r\n" + 
					"  `q_id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `q_type` INT NOT NULL,\r\n" + 
					"  `q_marks` INT NOT NULL,\r\n" + 
					"  `q_question` LONGTEXT NOT NULL,\r\n" +
					"  `q_co` VARCHAR(45) NOT NULL,\r\n" + 
					"  `q_answer` LONGTEXT NOT NULL,\r\n" + 
					"  `q_a` VARCHAR(45) NULL,\r\n" + 
					"  `q_b` VARCHAR(45) NULL,\r\n" + 
					"  `q_c` VARCHAR(45) NULL,\r\n" + 
					"  `q_d` VARCHAR(45) NULL,\r\n" + 
					"  PRIMARY KEY (`q_id`),\r\n" + 
					"  UNIQUE INDEX `q_id_UNIQUE` (`q_id` ASC) VISIBLE);";
			
			ps1 = con.prepareStatement(query1);
			rs1 = ps1.executeUpdate();
			
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
		return testId;
	}
}
