<%@page import="java.sql.Date"%>
<%@page import="com.vgec.dao.TestDAO"%>
<%@page import="java.sql.Time"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vgec.bean.Test"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vgec.dao.StudentInfoDAO"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%
	String userRole = new String("SUPERSTAR");

	if (session.getAttribute("role") != null) {
		userRole = (String) session.getAttribute("role");
	}
	if (userRole.equals("Faculty")) {
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>
<jsp:include page="facultySidebar.jsp" />
<jsp:include page="facultyTopbar.jsp" />
<center>
<div class="container mt-5" style="height:700px;">
  <div class="mb-4"><h2> Create Test</h2></div>
  <table>
    <form class="user" method="POST">
      <div class="form-group mt-3">
        <tr>
          <td><label>Batch </label></td>
          <% ArrayList<Integer> bat = StudentInfoDAO.getBatches(String.valueOf(session.getAttribute("facultyDepartment"))); 
          session.setAttribute("bat", bat);
          %>
          <td><select name="batch" class="form-control" style="width: 200px;">
          <c:forEach items="${bat}" var="item">
          	<option value="${item }">${item }</option>
          </c:forEach>
          </select></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td><label>Subject</label></td>
          <td><input type="text" name="subject" placeholder="Python Programming" class="form-control" style="width: 200px;"></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
          <tr>
            <td><label>Category</label></td>
            <td><select name="category" class="form-control" style="width: 200px;">
              <option value="1">Test</option>
              <option value="2">Mid Sem</option>
              </select></td>
          </tr>
        </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td><label>Date</label></td>
          <td><input type="date" name="date" class="form-control" style="width: 200px;"></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td><label>From Time</label></td>
          <td><input type="time" name="fromTime" class="form-control" style="width: 200px;"></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td><label>To Time</label></td>
          <td><input type="time" name="toTime" class="form-control" style="width: 200px;"></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td><label>Duration</label></td>
          <td><select name="duration" class="form-control" style="width: 200px;">
          	  <option value="15">15 min</option>
              <option value="30">30 min</option>
              <option value="1">1 hour</option>
              <option value="2">2 hour</option>
              </select></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td><label>Marks</label></td>
          <td><input type="text" name="marks" placeholder="30" class="form-control" style="width: 200px;"></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr><tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
          <tr>
            <td></td>
            <td><input type="submit" class="btn btn-primary btn-user btn-block" id="submitLink" value="Create Test" name="btnCreateTest"></td>
          </tr>
        </div>

      </form>
  </table>
  
  <%
  if (request.getParameter("btnCreateTest") != null){
	Test t = new Test();
	t.setBatch(Integer.parseInt(request.getParameter("batch")));
	t.setSubject(request.getParameter("subject"));
	t.setCategory(request.getParameter("category"));
	t.setDate(Date.valueOf(request.getParameter("date")));
	t.setFromtime(new Time(new SimpleDateFormat("hh:mm").parse(request.getParameter("fromTime")).getTime()));
	t.setTotime(new Time(new SimpleDateFormat("hh:mm").parse(request.getParameter("toTime")).getTime()));
	t.setDuration(Integer.parseInt(request.getParameter("duration")));
	t.setDepartment(String.valueOf(session.getAttribute("facultyDepartment")));
	t.setFid((Integer)(session.getAttribute("facultyId")));
	t.setTotalmarks(Integer.parseInt(request.getParameter("marks")));
	int testId = TestDAO.createTest(t);
	request.setAttribute("testId", testId);
	request.setAttribute("batch", request.getParameter("batch"));
	request.getRequestDispatcher("facultyAddQuestion.jsp").forward(request, response);
  }
  %>
</div>
</center>
<jsp:include page="facultyFooter.jsp" />
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>