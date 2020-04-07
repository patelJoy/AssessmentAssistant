<%
	String userRole = new String("SUPERSTAR");

	if (session.getAttribute("role") != null) {
		userRole = (String) session.getAttribute("role");
	}
	if (userRole.equals("Faculty")) {
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="facultySidebar.jsp" />
<jsp:include page="facultyTopbar.jsp" />
<center>
<div class="container mt-5" style="height:700px;">
  <div class="mb-4"><h2> Add Question</h2></div>
  <table>
    <form action="QuestionsImport" method="POST" enctype="MULTIPART/FORM-DATA">
    <input type="hidden" name="testId" value="<%= request.getAttribute("testId")%>">
    <input type="hidden" name="batch" value="<%= request.getAttribute("batch")%>">
      <div class="form-group mt-3">
        <tr>
          <td><label>Questions Sheet : </label></td>
          <td><input type="file" name="fileUpload"></td>
        </tr>
      </div>
      <tr><td><td></td></tr><tr><td><td></td></tr><tr><td><td></td></tr><tr><td><td></td></tr>
      <div class="form-group">
        <tr>
          <td></td>
          <td>
          <input type="submit" value="Add Questions" class="btn btn-primary btn-icon-split">
          </td>
        </tr>
         
      </div>

      </form>
  </table>
</div>
</center>
<jsp:include page="facultyFooter.jsp" />
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>