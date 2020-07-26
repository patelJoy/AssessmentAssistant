package com.vgec.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vgec.bean.Question;
import com.vgec.bean.Test;
import com.vgec.dao.TestDAO;
import com.vgec.util.DB_Connection;

public class StoreAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StoreAnswers() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@SuppressWarnings("unlikely-arg-type")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		ArrayList<Question> qls = (ArrayList<Question>) sess.getAttribute("qls");
		if(qls == null) {
			qls = TestDAO.getQuestions(Integer.parseInt(String.valueOf(sess.getAttribute("testid"))));
		}
		int size = qls.size();
		
		ArrayList<String> ls = new ArrayList<String>();
		String ans = null;
		
		ArrayList<Integer> marks = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			marks.add(0);
		}
		
		int tid = Integer.parseInt(String.valueOf(sess.getAttribute("testid")));
		String query = "update answers_"+tid+" SET ";
		
		for(int i=0;i<size;i++) {
			if(i == (size-1)) {
				query += " a_" + qls.get(i).getId() + "=?, a_markslist=?, attempted=? where a_erno=?";
			}else {
				query += " a_" + qls.get(i).getId() + "=?, ";
			}
			
			if(request.getParameter("ques"+qls.get(i).getId()) != null && !request.getParameter("ques"+qls.get(i).getId()).equals("")) {
				if(qls.get(i).getType() == 1) {
					ans = request.getParameter("ques"+qls.get(i).getId());
					int a = qls.get(i).getId()-1;
					marks.set(a,0);
				}else {
					int val = Integer.parseInt(request.getParameter("ques"+qls.get(i).getId()));
					if(val == 1) {
						ans = qls.get(i).getA();
					}else if(val == 2) {
						ans = qls.get(i).getB();
					}else if(val == 3) {
						ans = qls.get(i).getC();
					}else if(val == 4){
						ans = qls.get(i).getD();
					} else {
						ans = null;
					}
					if(ans != null && ans.equals(qls.get(i).getAnswer())) {
						int a = qls.get(i).getId()-1;
						int b = qls.get(i).getMarks();
						marks.set(a,b);
					}else {
						int a = qls.get(i).getId()-1;
						marks.set(a,0);
					}
				}
			}else {
				ans = null;
				int a = qls.get(i).getId()-1;
				marks.set(a,0);
			}
			ls.add(ans);
		}
		
		
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
			String mark = "" + marks.get(0); 
			for(int i=1;i<=size;i++) {
				if(ls.get(i-1) != null) {
					ps.setString(i, ls.get(i-1));
				}else {
					ps.setNull(i, Types.NULL);
				}
				if(i != size) {
					mark += "," + marks.get(i);
				}
			}
			ps.setString(size+1, mark);
			ps.setInt(size+2, 1);
			ps.setString(size+3, String.valueOf(sess.getAttribute("studentUsername")));
			ps.executeUpdate();
			
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
		
		response.sendRedirect("studentPendingTests.jsp");
	}
}
