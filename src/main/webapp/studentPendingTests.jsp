<%@page import="com.vgec.dao.TestDAO"%>
<%@page import="com.vgec.bean.Test"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vgec.dao.StudentInfoDAO"%>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String userRole = new String("SUPERSTAR");

	if (session.getAttribute("role") != null) {
		userRole = (String) session.getAttribute("role");
	}
	if (userRole.equals("Student")) {
%>
<jsp:include page="studentSidebar.jsp" />
<jsp:include page="studentHeader.jsp" />
       
<!--container fluid-->
<div class="container-fluid">

  <!-- Page Heading -->
 

       <!-- List Of Tests -->
      <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Tests to come</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="studentPendingTestsTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Subject</th>
                      <th>Category</th>
                      <th>Date</th>
                      <th>Marks</th>
                      <th>Apply for test</th>
                    </tr>
                  </thead>
                  <tbody>
                  <%ArrayList<Test> pt = TestDAO.getPendingTests((Integer)session.getAttribute("studentBatch"),String.valueOf(session.getAttribute("studentDepartment")));
                   	ArrayList<Test> p = new ArrayList<Test>();
                    int size = pt.size();
                   	for(int i=0;i<size;i++){
                   		if(TestDAO.getAttempt(String.valueOf(session.getAttribute("studentUsername")), pt.get(i).getId())){
                   			p.add(pt.get(i));
                   		}
                   	}
            		if(p != null){
            		session.setAttribute("p", p); 
            		%>
            		<c:forEach items="${p}" var="item">
                      <tr>
                        <td>${item.subject }</td>
                        <td>${item.category }</td>
                        <td>${item.date }</td>
                        <td>${item.totalmarks }</td>
                        <td>
                        <form class="user" method="POST" action="studentTestInstructions.jsp">
                        <input type="hidden" name="testid" value="${item.id }"/>
                        <input type="hidden" name="duration" value="${item.duration }"/>
                        <input type="hidden" name="fromtime" value="${item.fromtime }">
                        <input type="hidden" name="totime" value="${item.totime }">
                        <input type="hidden" name="totalmarks" value="${item.totalmarks }">
                        <input type="hidden" name="subject" value="${item.subject }">
                        <Button type="submit" class="btn btn-success btn-sm" id="submitLink" name="Give Test">                        	
                            <span class="text">Give Test</span>
                          </Button>
                        </form>
                        </td>
                      </tr>
                     </c:forEach>
                      <%} %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
                 
</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->
<jsp:include page="studentFooter.jsp" />
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>