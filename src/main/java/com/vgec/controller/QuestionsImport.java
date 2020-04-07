package com.vgec.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vgec.util.DB_Connection;

@MultipartConfig(maxFileSize = 16177215) 
public class QuestionsImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public QuestionsImport() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Part filePart = request.getPart("fileUpload");
        int batchSize = 20;
        int noOfQuestions = 0;
        String dept = String.valueOf(request.getSession().getAttribute("facultyDepartment"));
        int batch = Integer.parseInt(request.getParameter("batch"));
 
        Connection connection = null;
        Connection con = null;
        try {

            Workbook workbook = new XSSFWorkbook(filePart.getInputStream());
 
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
 
            connection = DB_Connection.getConnection();
            connection.setAutoCommit(false);
            String st = "questions_" + request.getParameter("testId");
  
            String sql = "INSERT INTO "+ st +" (q_type, q_question, q_answer, q_marks, q_co, q_a, q_b, q_c, q_d) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);    
             
            int count = 0;
             
            rowIterator.next(); // skip the header row
             
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                
                int q_type = 0;
                noOfQuestions++;
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
 
                    int columnIndex = nextCell.getColumnIndex();
                    
                    switch (columnIndex) {
                    case 0:
                    	q_type = (int)nextCell.getNumericCellValue();
                        statement.setInt(1, q_type);
                        break;
                    case 1:
                        String ques = nextCell.getStringCellValue();
                        statement.setString(2, ques);
                        break;
                    case 2:                    
                    	String ans = nextCell.getStringCellValue();
                        statement.setString(3, ans);
                        break;
                    case 3:
                    	int marks = (int)nextCell.getNumericCellValue();
                    	statement.setInt(4, marks);
                    	break;
                    case 4:
                    	String co = nextCell.getStringCellValue();
                    	statement.setString(5, co);
                    	break;
                    case 5:
                    	String a = nextCell.getStringCellValue();
                    	statement.setString(6, a);
                    	break;
                    case 6:
                   		String b = nextCell.getStringCellValue();
                   		statement.setString(7, b);
                    	break;
                    case 7:
                    	String c = nextCell.getStringCellValue();
                    	statement.setString(8, c);
                    	break;
                    case 8:
                   		String d = nextCell.getStringCellValue();
                   		statement.setString(9, d);
                    	break;
                    }
                }
                if(q_type == 1) {
                	statement.setNull(6, Types.NULL);
                	statement.setNull(7, Types.NULL);
                	statement.setNull(8, Types.NULL);
                	statement.setNull(9, Types.NULL);
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
             
            con = DB_Connection.getConnection();
            String query1 = "select q_id from "+st;
            ResultSet rs1 = con.prepareStatement(query1).executeQuery();
            ArrayList<Integer> qIds = new ArrayList<Integer>();  
            while(rs1.next()) {
            	qIds.add(rs1.getInt(1));
            }
            
            String query2 = "select ";
             
        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
        response.sendRedirect("facultyViewTest.jsp");

	}

}
