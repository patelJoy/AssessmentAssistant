package com.vgec.controller;

import java.io.File;
import java.io.FileWriter;
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
import org.apache.poi.ss.usermodel.CellType;
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
		
        String dirPath = "C:\\Users\\patel\\Downloads\\pj2";

        String testDirName = request.getParameter("testId");
       
        File testDirectory = new File(dirPath + File.separator + "test_"+ testDirName);
        testDirectory.mkdirs();

        File idealDirectory = new File(testDirectory + File.separator + "Ideal");
        idealDirectory.mkdir();
		
		ArrayList<String> answ = new ArrayList<String>();
		Part filePart = request.getPart("fileUpload");
        int batchSize = 10;
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
                    	if(nextCell.getCellType() == CellType.STRING) {
                    		String ques = nextCell.getStringCellValue();
                            statement.setString(2, ques);
                    	}else {
                    		String ques = String.valueOf(nextCell.getNumericCellValue());
                            statement.setString(2, ques);
                    	}
                        break;
                    case 2:                    
                    	if(nextCell.getCellType() == CellType.STRING) {
                    		String ans = nextCell.getStringCellValue();
                    		answ.add(ans);
                            statement.setString(3, ans);
                    	}else {
                    		String ans = String.valueOf(nextCell.getNumericCellValue());
                    		answ.add(ans);
                            statement.setString(3, ans);
                    	}
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
                    	if(nextCell.getCellType() == CellType.STRING) {
                    		String a = nextCell.getStringCellValue();
                            statement.setString(6, a);
                    	}else {
                    		String a = String.valueOf(nextCell.getNumericCellValue());
                            statement.setString(6, a);
                    	}
                    	break;
                    case 6:
                    	if(nextCell.getCellType() == CellType.STRING) {
                    		String b = nextCell.getStringCellValue();
                            statement.setString(7, b);
                    	}else {
                    		String b = String.valueOf(nextCell.getNumericCellValue());
                            statement.setString(7, b);
                    	}
                    	break;
                    case 7:
                    	if(nextCell.getCellType() == CellType.STRING) {
                    		String c = nextCell.getStringCellValue();
                            statement.setString(8, c);
                    	}else {
                    		String c = String.valueOf(nextCell.getNumericCellValue());
                            statement.setString(8, c);
                    	}
                    	break;
                    case 8:
                    	if(nextCell.getCellType() == CellType.STRING) {
                    		String d = nextCell.getStringCellValue();
                            statement.setString(9, d);
                    	}else {
                    		String d = String.valueOf(nextCell.getNumericCellValue());
                            statement.setString(9, d);
                    	}
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
            String query1 = "select q_id,q_type from "+st;
            ResultSet rs1 = con.prepareStatement(query1).executeQuery();
            ArrayList<Integer> qIds = new ArrayList<Integer>();  
            int j = 0;
            while(rs1.next()) {
            	if(rs1.getInt(2) == 1) {
            		File newFile = new File(idealDirectory + File.separator + "q_" +rs1.getInt(1) + ".txt");
                    boolean isCreated = newFile.createNewFile();
                    if (isCreated) {
                    	System.out.println(rs1.getInt(1)+ " "+answ.get(j));
                        FileWriter myWriter = new FileWriter(newFile);
                        myWriter.write(answ.get(j));
                        myWriter.close();
                    } else {
                        System.out.printf("\n2. Unable to create new file");
                    }
            	}
            	qIds.add(rs1.getInt(1));
            	j++;
            }
            rs1.close();
            
            
            
            
            String query2 = "select student_username from student_info where student_batch=? and student_department=?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, batch);
            ps2.setString(2, dept);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<String> ernos = new ArrayList<String>();
            while(rs2.next()) {
            	ernos.add(rs2.getString(1));
            }
            rs2.close();
            
            st = "answers_" + request.getParameter("testId");
            String query3 = "CREATE TABLE "+ st +" (\r\n" + 
            		"  `a_erno` VARCHAR(16) NOT NULL DEFAULT 0,\r\n";
            int size = qIds.size();
            for(int i=0;i<size;i++) {
            	query3 += " `a_"+ qIds.get(i) + "` LONGTEXT NULL,\r\n";
            }
            query3 += "  `a_markslist` MEDIUMTEXT NULL,\r\n" + 
            		"  `attempted` INT NOT NULL DEFAULT 0,\r\n" +
            		"  PRIMARY KEY (`a_erno`));";
            
            con.prepareStatement(query3).executeUpdate();
            
            String query4 = "insert into " + st + "(a_erno) values (?);";          
            con.setAutoCommit(false);
            int ersize = ernos.size();
            PreparedStatement ps4 = con.prepareStatement(query4);
            
            for(int i=0;i<ersize;i++) {
            	ps4.setString(1, ernos.get(i));
            	
            	ps4.addBatch();
                
                if (i % (batchSize+10) == 0) {
                    ps4.executeBatch();
                }
            }
            
            ps4.executeBatch();
            con.commit();
            con.close();
            
            
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
