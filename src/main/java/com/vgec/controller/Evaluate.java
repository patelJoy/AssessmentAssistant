package com.vgec.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgec.bean.EvaluateBean;
import com.vgec.dao.TestDAO;

public class Evaluate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Evaluate() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		if(request.getParameter("tid") != null) {
			System.out.println(request.getParameter("tid"));
		int tid = Integer.parseInt(request.getParameter("tid"));
		TestDAO.updateEvaluate(tid);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<img src='C://Users//patel//eclipse-workspace//AssessmentAssistant//src//main//webapp//img//eval1.gif' style='display:block;margin-left:auto;margin-right:auto;width:50%;' alt='image' />");
		
		ArrayList<Integer> qids = TestDAO.getQuestionIds(tid); 
		
		ArrayList<EvaluateBean> eb = TestDAO.getEvaluateHash(qids, tid);
		
		int size = eb.size();
		
	    String dirPath = "C:\\Users\\patel\\Downloads\\pj2";
	    File testDirectory = new File(dirPath + File.separator + "test_" + tid);
        File actualDirectory = new File(testDirectory + File.separator + "actual");
        actualDirectory.mkdir();
        
        int id = 0;
        boolean isCreated = false;
        FileWriter myWriter = null;
        for(int i=0; i< size; i++)
        {
            if(id != eb.get(i).getQid()) {
            	if(myWriter != null) {
            		myWriter.close();
            	}
            	String newFileName = new String("q_" + eb.get(i).getQid() + ".txt");
                File newFile = new File(actualDirectory + File.separator + newFileName);
                isCreated = newFile.createNewFile();
                if (isCreated) {
                    myWriter = new FileWriter(newFile);
                    myWriter.write(eb.get(i).getErno() + "," + eb.get(i).getAnswer() + "\n");
                } else {
                    System.out.printf("\n2. Unable to create new file");
                }
                id = eb.get(i).getQid();
            } else {
            	myWriter.write(eb.get(i).getErno() + "," + eb.get(i).getAnswer() + "\n");
            }
        }
        if(myWriter != null) {
    		myWriter.close();
    	}
        
        Runtime rt = Runtime.getRuntime();
		 try {
			Process p = rt.exec("cmd.exe /c start /wait python SemanticSimilarity.py"+" " + tid, null, new File("C:\\Users\\patel\\Downloads\\pj2"));
			p.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 String content = "";
		    try
		    {
		        content = new String ( Files.readAllBytes( Paths.get("C:\\Users\\patel\\Downloads\\pj2\\test_"+57+"\\output.txt")));
		    } 
		    catch (IOException e) 
		    {
		        e.printStackTrace();
		    }
		    System.out.println(content);
		    String[] arrOfStr = content.split("\\|\\|\\|");
		    int size1 = arrOfStr.length;
		    ArrayList<EvaluateBean> ebs = new ArrayList<EvaluateBean>();
		    EvaluateBean b = null;
		   
		    for(int i=0;i<size1;i++) {
		    	if(!arrOfStr[i].equals("")) {
			    	String[] s = arrOfStr[i].split(",");
			    	b = new EvaluateBean();
			    	b.setQid(Integer.parseInt(s[0]));
			    	b.setErno(s[1]);
			    	double a = Double.parseDouble(s[2].substring(0,5));
			    	if(a < 0) {
			    		b.setMarks(0);
			    	}else {
			    		b.setMarks(a);
			    	}
			    	ebs.add(b);
		    	}
		    }
		    
		    TestDAO.updateMarks(ebs, tid);		
		    response.sendRedirect("facultyViewResult.jsp");
		}//if
	}
}
