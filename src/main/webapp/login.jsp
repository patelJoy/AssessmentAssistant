<%@page import="com.vgec.util.Md5"%>
<%@page import="com.vgec.dao.LoginDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String userRole = new String("SUPERSTAR");
	if (session.getAttribute("role") != null) {
		userRole = (String) session.getAttribute("role");
	}
		if (userRole.equals("admin")) {
			response.sendRedirect("adminHome.jsp");
		} else if (userRole.equals("faculty")) {
			response.sendRedirect("facultyCreateTest.jsp");
		} else if (userRole.equals("student")) {
			response.sendRedirect("studentPendingTests.jsp");
		} else {
%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Admin - Login</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">
<script type="text/javascript">
function rotate(){
  var tmpAnimation = 0;
    var element = $("img");
    tmpAnimation = tmpAnimation + 210;
    setTimeout(function() {
    	$({degrees: tmpAnimation - 90}).animate({degrees: tmpAnimation}, {
            duration: 3000,
            step: function(now) {
                element.css({
                    transform: 'rotate(' + now + 'deg)'
                });
            }
        });
    	}, 5000);
}
</script>

</head>

<body class="bg-gradient-primary">
  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">
      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block"> 
              	<img id='obscene-stuff' src="img/AsseesmentAssistant.png" height="500px" onload="rotate()">
              </div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Assessment Assistant</h1>
                  </div>
                  <hr>
                  <br>
                  <form class="user" method="POST">
                  	<select class="custom-select custom-select-lg mb-3" name="roleSelect" id="roleSelect" required>
					  <option selected>You Are</option>
					  <option value="Admin">Admin</option>
					  <option value="Faculty">Faculty</option>
					  <option value="Student">Student</option>
					</select>
                    <div class="form-group">
                      <input type="text" class="form-control form-control-user" id="username" placeholder="Enter Username" name="email" required>
                    </div>
                    <div class="form-group">
                      <input type="password" class="form-control form-control-user" id="password" name="password" placeholder="password" required>
                    </div>
                    <br>
                    <div class="form-group">
                      <input type="submit" class="btn btn-primary btn-user btn-block" id="submitLink" value="login" name="btnLogin">
                    </div>
                    <hr>
                  </form>
                  <div class="text-center">
                    <a class="small" href="forgot-password.html">Forgot Password?</a>
                  </div>
                  <%
						String role = request.getParameter("roleSelect");
						if (request.getParameter("btnLogin") != null && role.equals("Admin")) {
							ResultSet rs = LoginDAO.checkLogin(role, request.getParameter("email"), request.getParameter("password"));
							if (rs != null) {
								String uName = null;
								String name = null;
								String dept = null;
								
								if (rs.next()) {
									uName = rs.getString("admin_username");
									dept = rs.getString("admin_department");
									name = rs.getString("admin_name");
								}
								session.setAttribute("adminName", name);
								session.setAttribute("adminUsername", uName);
								session.setAttribute("adminDepartment", dept);
								session.setAttribute("role", role);
								response.sendRedirect("adminHome.jsp");
							} else {
								out.println("<script>alert('Wrong username or password')</script>");
							}
						} else if (request.getParameter("btnLogin") != null && role.equals("Faculty")) {
							ResultSet rs = LoginDAO.checkLogin(role, request.getParameter("email"), request.getParameter("password"));
							if (rs != null) {
								String uName = null;
								String name = null;
								String dept = null;
								int id = 0;
								
								if (rs.next()) {
									name = rs.getString("faculty_name");
									dept = rs.getString("faculty_department");
									id = rs.getInt("faculty_id");
									uName = rs.getString("faculty_username");
								}
								System.out.println(id);
								session.setAttribute("facultyName", name);
								session.setAttribute("facultyUsername", uName);
								session.setAttribute("facultyId",id);
								session.setAttribute("role", role);
								session.setAttribute("facultyDepartment", dept);
								response.sendRedirect("facultyCreateTest.jsp");
							} else {
								out.println("<script>alert('Wrong username or password')</script>");
							}
						} else if (request.getParameter("btnLogin") != null && role.equals("Student")) {
							ResultSet rs = LoginDAO.checkLogin(role, request.getParameter("email"), request.getParameter("password"));
							if (rs != null) {
								String uName = null;
								String name = null;
								String dept = null;
								int batch = 0;
								String email = null;
								
								if (rs.next()) {
									name = rs.getString("student_name");
									dept = rs.getString("student_department");
									uName = rs.getString("student_username");
									batch = rs.getInt("student_batch");
									email = rs.getString("student_email");
								}
								session.setAttribute("studentName", name);
								session.setAttribute("studentUsername", uName);
								session.setAttribute("role", role);
								session.setAttribute("studentDepartment", dept);
								session.setAttribute("studentBatch", batch);
								session.setAttribute("studentEmail", email);
								response.sendRedirect("studentPendingTests.jsp");
							} else {
								out.println("<script>alert('Wrong username or password')</script>");
							}
						}
	}
					%>
                  <hr>
                </div>
              </div>
            </div>
          </div>
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

</body>

</html>
