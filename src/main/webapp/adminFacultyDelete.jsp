<%@page import="com.vgec.dao.FacultyInfoDAO"%>
<%
	String userRole = new String("SUPERSTAR");

	if (session.getAttribute("role") != null) {
		userRole = (String) session.getAttribute("role");
	}
	if (userRole.equals("Admin")) {
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="true" %>
<%@ page isELIgnored="false" %><!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%	
	out.println(request.getParameter("id"));
	int id = Integer.parseInt(request.getParameter("id"));
	FacultyInfoDAO.deleteFaculty(id);
	response.sendRedirect("adminViewFaculty.jsp");
%>
</body>
</html>
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>
