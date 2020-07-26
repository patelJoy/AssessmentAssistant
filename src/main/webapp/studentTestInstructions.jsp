<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Time"%>
<%@page import="java.util.Calendar"%>
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
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h4 class="h3 mb-0 text-gray-800">Read the given instructions carefully</h4>

    </div>

    <div class="row">

        <div class="col-lg-12">

            <!-- Instructions -->
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Instructions</h6>
                </div>
                <div class="card-body">
                    <h6 class="font-weight-bold"></h6>
                    <div>
                        <i class="fa fa-angle-right">All the questions are compulsory.</i>
                    </div>
                    <div>
                        <i class="fa fa-angle-right">Read all the questions properly before
                            answering.</i>
                    </div>
                    <div>
                        <i class="fa fa-angle-right">No negative marking is there.</i>
                    </div>
                    <div>
                        <i class="fa fa-angle-right">Once you have started the exam, you will not be
                            able to go back or restart the exam.</i>
                    </div>
                    <div>
                        <i class="fa fa-angle-right">Click the button below to start the exam. You will
                            be having 60 minutes to complete the test.</i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row justify-content-center">
    <form class="user" method="POST">
    	<input type="hidden" name="testid" value="<%= request.getParameter("testid")%>"/>
        <input type="hidden" name="duration" value="<%= request.getParameter("duration") %>"/>
        <input type="hidden" name="fromtime" value="<%= request.getParameter("fromtime")%>">
        <input type="hidden" name="totime" value="<%= request.getParameter("totime")%>">
        <input type="hidden" name="totalmarks" value="<%= request.getParameter("totalmarks")%>">
        <input type="hidden" name="subject" value="<%= request.getParameter("subject")%>">
        <Button type="submit" class="btn btn-success btn-sm" id="submitLink" name="startTest">                        	
            <span class="text">Start Test</span>
        </Button>
	</form>
	<%
	 if(request.getParameter("startTest") != null){
		 session.setAttribute("testid", request.getParameter("testid"));
		 session.setAttribute("duration", request.getParameter("duration"));
		 session.setAttribute("fromtime", request.getParameter("fromtime"));
		 session.setAttribute("totime", request.getParameter("totime"));
		 session.setAttribute("totalmarks", request.getParameter("totalmarks"));
		 session.setAttribute("subject", request.getParameter("subject"));
		 java.util.Date currentDate = new java.util.Date();
		 int dur =Integer.parseInt(request.getParameter("duration"));
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(currentDate);
		 if(dur == 1){
			 cal.add(Calendar.HOUR, 1);
		 }else if(dur == 2){
			 cal.add(Calendar.HOUR, 2);
		 }else if(dur == 15){
			 cal.add(Calendar.MINUTE, 15);
		 }else if(dur == 30){
			 cal.add(Calendar.MINUTE, 30);
		 }
		 java.util.Date endtime = cal.getTime();
		 
		session.setAttribute("endtime", endtime.getTime());	 
		
		 
		 response.sendRedirect("studentGiveTest.jsp");
	 }
	%>
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