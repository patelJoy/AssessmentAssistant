package com.vgec.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.vgec.bean.EvaluateBean;
import com.vgec.dao.TestDAO;

public class Check {

	public static void main(String[] args) {			 
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
	    int size = arrOfStr.length;
	    ArrayList<EvaluateBean> eb = new ArrayList<EvaluateBean>();
	    EvaluateBean b = null;
	   
	    for(int i=0;i<size;i++) {
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
		    	eb.add(b);
	    	}
	    }
	    
	    Collections.sort(eb, new Comparator<EvaluateBean>() {
			  public int compare(EvaluateBean u1, EvaluateBean u2) {
			    return (int) (Long.parseLong(u1.getErno()) - Long.parseLong(u2.getErno()));
			  }
			});
	    
	    TestDAO.updateMarks(eb, 57);
	    
	}
}
