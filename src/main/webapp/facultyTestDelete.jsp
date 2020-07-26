<%@page import="com.vgec.dao.TestDAO"%>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%
	String userRole = new String("SUPERSTAR");

	if (session.getAttribute("role") != null) {
		userRole = (String) session.getAttribute("role");
	}
	if (userRole.equals("Faculty")) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% TestDAO.facultyDeleteTest(Integer.parseInt(request.getParameter("testId")));
	if(request.getParameter("page").equals("test")){
		response.sendRedirect("facultyViewTest.jsp");	
	}else{
		response.sendRedirect("facultyViewResult.jsp");
	}
%>
</body>
</html>
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>