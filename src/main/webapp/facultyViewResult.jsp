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
    <h1 class="h3 mb-2 text-gray-800"><center>Test Result</center></h1>

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
                <th>View Result</th>
              </tr>
            </thead>
            <tbody>
            <% ArrayList<Test> pt = TestDAO.getPendingTests((Integer)session.getAttribute("facultyId"), true);
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
                <td>
                <c:choose>
		         <c:when test = "${item.evaluated == 0 }">
		         	<form action="Evaluate" class="user" method="POST">
	                <div class="form-group">
	                	<input type="hidden" name="tid" id="tid" value="${item.id }"/>
	                    <button type="submit" class="btn btn-success" id="evaluate">Evaluate
	                    </button>
	                    <img src="img/eval1.gif" id="loadimg" height="50px" width="50px" style="visibility:hidden;"/>
	                </div>
	                
					</form>
		         </c:when>
         
		         <c:otherwise>
		         	<form action="facultyResultStudentList.jsp" class="user" method="POST">
	                <div class="form-group">
	                	<input type="hidden" name="id" value="${item.id }"/>
	                    <button type="submit" class="btn btn-primary btn-circle btn-sm" id="viewresult">
	                    <i class="fa fa-location-arrow"></i>
	                    </button>
	                </div>
					</form>
		         </c:otherwise>
      			</c:choose>
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

<footer class="sticky-footer bg-white">
    <div class="container my-auto">
      <div class="copyright text-center my-auto">
        <span>Copyright &copy; Your Website 2019</span>
      </div>
    </div>
  </footer>
  <!-- End of Footer -->

</div>
<!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
<i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
<div class="modal-dialog" role="document">
  <div class="modal-content">
    <div class="modal-header">
      <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
      <button class="close" type="button" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
      </button>
    </div>
    <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
    <div class="modal-footer">
      <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
      <a class="btn btn-primary" href="logout.jsp">Logout</a>
    </div>
  </div>
</div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="js/demo/chart-area-demo.js"></script>
<script src="js/demo/chart-pie-demo.js"></script>

</body>

</html>

<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>