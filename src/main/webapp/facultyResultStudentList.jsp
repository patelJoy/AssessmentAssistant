<%@page import="com.vgec.bean.StudentInfo"%>
<%@page import="com.vgec.dao.StudentInfoDAO"%>
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

				<!-- List Of Tests -->
              <div class="card shadow mb-4">
                    <div class="card-header py-3">
                      <h6 class="m-0 font-weight-bold text-primary">View Results</h6>
                    </div>
                    <div class="card-body">
                      <div class="table-responsive">
                        <table class="table table-bordered" id="studentResultTable" width="100%" cellspacing="0">
                          <thead>
                            <tr>
                              <th>Enrollment No</th>
                              <th>Name</th>
                              <th>Marks</th>
                              <th>Edit Result</th>
                            </tr>
                          </thead>
                          <%
                          	ArrayList<StudentInfo> lsi = StudentInfoDAO.resultStudentList(Integer.parseInt(request.getParameter("id")));
                          if(lsi != null){
                          	session.setAttribute("lsi", lsi);
                          %>
                          <tbody>
                          <c:forEach items="${lsi}" var="item">
                              <tr>
                                <td>${item.erno}</td>
                                <td>${item.name}</td>
                                <td>${item.marks}</td>
                                <td>
                                  <form action="facultyEditResult.jsp" class="user" method="POST">
					                <div class="form-group">
					                	<input type="hidden" name="erno" value="${item.erno }"/>
					                	<input type="hidden" name="id" value="<%=request.getParameter("id")%>"/>
					                    <button type="submit" class="btn btn-primary btn-circle btn-sm" id="evaluate">
					                    <i class="fas fa-edit"></i>
					                    </button>
					                </div>
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

<jsp:include page="facultyFooter.jsp" />
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>