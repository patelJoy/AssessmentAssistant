package com.vgec.controller;

import java.io.*;
import java.sql.*;
import java.util.*;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.vgec.util.DB_Connection;
import com.vgec.util.Md5;

@WebServlet("/DataImport")
@MultipartConfig(maxFileSize = 16177215) 
public class DataImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public DataImport() {
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int batch = Integer.parseInt(request.getParameter("batch"));
		String pass = Md5.getMd5("helloworld");
		String dept = String.valueOf(request.getSession().getAttribute("adminDepartment"));
		
		Part filePart = request.getPart("fileUpload");
        int batchSize = 20;
 
        Connection connection = null;
 
        try {

            Workbook workbook = new XSSFWorkbook(filePart.getInputStream());
 
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
 
            connection = DB_Connection.getConnection();
            connection.setAutoCommit(false);
  
            String sql = "INSERT INTO student_info (student_username, student_name, student_email, student_batch, student_password, student_department) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);    
             
            int count = 0;
             
            rowIterator.next(); // skip the header row
             
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
 
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
 
                    int columnIndex = nextCell.getColumnIndex();
 
                    switch (columnIndex) {
                    case 0:
                        Double username = nextCell.getNumericCellValue();
                        statement.setDouble(1, username);
                        break;
                    case 1:
                        String name = nextCell.getStringCellValue();
                        statement.setString(2, name);
                        break;
                    case 2:                    
                    	String email = nextCell.getStringCellValue();
                        statement.setString(3, email);
                        break;
                    }
                    statement.setInt(4, batch);
                    statement.setString(5, pass);
                    statement.setString(6, dept);
 
                }
                 
                statement.addBatch();
                 
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }              
 
            }
 
            workbook.close();
             
            // execute the remaining queries
            statement.executeBatch();
  
            connection.commit();
            connection.close();
             
             
        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
        response.sendRedirect("adminViewStudentBatch.jsp");
	}

}
