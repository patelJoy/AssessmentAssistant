<%@page import="com.vgec.util.Md5"%>
<%@page import="com.vgec.dao.FacultyInfoDAO"%>
<%@page import="com.vgec.bean.FacultyInfo"%>
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
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Faculty - Register</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

  <div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">New Faculty</h1>
              </div>
              <form class="user" method="POST">
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="text" class="form-control form-control-user" id="facultyFirstName" name="facultyFirstName" placeholder="First Name">
                  </div>
                  <div class="col-sm-6">
                    <input type="text" class="form-control form-control-user" id="facultyLastName" name="facultyLastName" placeholder="Last Name">
                  </div>
                </div>
                <div class="form-group">
                  <input type="email" class="form-control form-control-user" id="facultyInputEmail" name="facultyEmail" placeholder="Email Address">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control form-control-user" id="facultyInputPassword" name="facultyPassword" placeholder="Password">
                </div>
                <div class="form-group">
                      <input type="submit" class="btn btn-primary btn-user btn-block" id="submitLink" value="Add Faculty" name="btnAddFaculty">
                </div>
               </form>
               <% 
               		if (request.getParameter("btnAddFaculty") != null) {
               			FacultyInfo fi = new FacultyInfo();
               			fi.setName(request.getParameter("facultyFirstName")+" "+request.getParameter("facultyLastName"));
               			fi.setUsername(request.getParameter("facultyEmail"));
               			fi.setPassword(Md5.getMd5(request.getParameter("facultyPassword")));
               			fi.setDepartment(String.valueOf(session.getAttribute("adminDepartment")));
               			FacultyInfoDAO.addFaculty(fi);
               			response.sendRedirect("adminViewFaculty.jsp");
               		}
               %>
              <hr>
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
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>