<%@page import="com.vgec.bean.Test"%>
<%@page import="java.util.ArrayList"%>
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
<jsp:include page="facultySidebar.jsp" />
<jsp:include page="facultyTopbar.jsp" />

<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800"><center>Pending Tests</center></h1>

    <!-- DataTales Example -->
    <div class="card shadow mb-4">
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-bordered mt-4" id="dataTable" width="100%" cellspacing="0">
            <thead>
              <tr>
                <th>Batch</th>
                <th>Subject</th>
                <th>Date</th>
                <th>From Time - To Time</th>
                <th>Marks</th>
                <th>Duration</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
            <% ArrayList<Test> pt = TestDAO.getPendingTests((Integer)session.getAttribute("facultyId"), false);
            	if(pt != null){
            	session.setAttribute("pt", pt);
            %>
            <c:forEach items="${pt}" var="item">
              <tr>
                <td>${item.batch }</td>
                <td>${item.subject }</td>
                <td>${item.date }</td>
                <td>${item.fromtime } - ${item.totime }</td>
                <td>${item.totalmarks }</td>
                <td>${item.duration }</td>
                <td><a href="facultyTestDelete.jsp?testId=<c:out value="${ item.id }"/>&page=test" class="btn btn-danger btn-circle btn-sm">
                    <i class="fas fa-trash"></i>
                  </a></td>
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

<jsp:include page="facultyFooter.jsp" />
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>