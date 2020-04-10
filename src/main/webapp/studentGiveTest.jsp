<%@page import="java.util.Calendar"%>
<%@page import="com.vgec.dao.TestDAO"%>
<%@page import="com.vgec.bean.Question"%>
<%@page import="java.util.ArrayList"%>
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
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Pending Tests</title>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.10.2.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
	
	<script>
    var switchCount = 0;
    let hidden, visibilityChange;
    if (typeof document.hidden !== 'undefined') {
        hidden = 'hidden';
        visibilityChange = 'visibilitychange';
    }
    else if (typeof document.webkitHidden !== 'undefined') {
        hidden = 'webkitHidden';
        visibilityChange = 'webkitvisibilitychange';
    }
    else if (typeof document.msHidden !== 'undefined') {
        hidden = 'msHidden';
        visibilityChange = 'msvisibilitychange';
    }
    else {
        hidden = null;
        visibilityChange = null;
    }

    if (hidden !== null) {
        document.addEventListener(visibilityChange, function (ev) {
            console.log('visiblitychange', document[hidden]);
            if (document.hidden) {
                switchCount++;
            }
            else {
                if (switchCount == 1) {
                    alert('This is the warning for you ! Do not try to change the tab now ');
                }
                if (switchCount > 1) {
                    document.getElementById("btnFinishAttempt").click();
                }
            }
        });

    }
var unhidden = 1;
var unhidden1 = 2;
function hide(i){
	s = 'q' + i;
	var div = document.getElementById(s);
	if(div != null){
		// hide
		div.style.visibility = 'hidden';
		// OR
		div.style.display = 'none';
	}
	s1 = 'btn' + i;
	var btn = document.getElementById(s1);
	if(btn != null){
		// hide
		btn.className = 'btn btn-primary btn-circle';
		// OR
	}
}
function init(){
	s = 'q'+1;
	var div = document.getElementById(s);
	if(div != null){
		// hide
		div.style.visibility = 'visible';
		// OR
		div.style.display = 'block';
	}
	s1 = 'q'+2;
	var div1 = document.getElementById(s1);
	if(div1 != null){
		// hide
		div1.style.visibility = 'visible';
		// OR
		div1.style.display = 'block';
	}
}
function unhide(i){
	hide(unhidden)
	hide(unhidden1)
	s = 'q' + i;
	s1 = 'q' + (i+1);
	unhidden = i;
	unhidden1 = (i+1);
	var div = document.getElementById(s);
	if(div != null){
		div.style.visibility = 'visible';
		// OR
		div.style.display = 'block';
	}
	var div1 = document.getElementById(s1);
	if(div1 != null){
		div1.style.visibility = 'visible';
		// OR
		div1.style.display = 'block';
	}
	s3 = 'btn' + i;
	var btn = document.getElementById(s3);
	if(btn != null){
		// hide
		btn.className = 'btn btn-success btn-circle';
		// OR
	}
	s4 = 'btn' + (i+1);
	var btn1 = document.getElementById(s4);
	if(btn1 != null){
		// hide
		btn1.className = 'btn btn-success btn-circle';
		// OR
	}
}
</script>
	
</head>

<body id="page-top" onload="init()">
    

    <!-- Page Wrapper -->
    <div id="wrapper">

      <!-- Content Wrapper -->
      <div id="content-wrapper" class="d-flex flex-column">
  
        <!-- Main Content -->
        <div id="content">
        
          <!-- Topbar -->
          <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
  
            <!-- Sidebar Toggle (Topbar) -->
            <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
              <i class="fa fa-bars"></i>
            </button>
  
  
            <!-- Topbar Navbar -->
            <ul class="navbar-nav ml-auto">
  
              <!-- Nav Item - Search Dropdown (Visible Only XS) -->
              <li class="nav-item dropdown no-arrow d-sm-none">
                <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <i class="fas fa-search fa-fw"></i>
                </a>
                <!-- Dropdown - Messages -->
                <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                  <form class="form-inline mr-auto w-100 navbar-search">
                    <div class="input-group">
                      <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                      <div class="input-group-append">
                        <button class="btn btn-primary" type="button">
                          <i class="fas fa-search fa-sm"></i>
                        </button>
                      </div>
                    </div>
                  </form>
                </div>
              </li>
  
  
               
              <div class="topbar-divider d-none d-sm-block"></div>
  
              <!-- Nav Item - User Information -->
              <li class="nav-item dropdown no-arrow">
                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <span class="mr-2 d-none d-lg-inline text-gray-600 small">Student</span>
                  <img class="img-profile rounded-circle" src="https://source.unsplash.com/QAB-WJcbgJk/60x60">
                </a>
                <!-- Dropdown - User Information -->
              </li>
  
            </ul>
  
          </nav>
          <!-- End of Topbar -->
     

<!--container fluid-->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800"><%= session.getAttribute("subject")%></h1>
        <div class="d-none d-sm-inline-block shadow-sm" style="color:blue">
             Total Marks: <%= session.getAttribute("totalmarks")%></div>
    </div>
<form class="user" method="POST" id="myForm" action="studentPendingTests.jsp">
    <div class="row">
		
        <div class="col-lg-9">
		
		<% ArrayList<Question> qls = TestDAO.getQuestions(Integer.parseInt(String.valueOf(session.getAttribute("testid"))));
			session.setAttribute("qls",qls);
			int size = qls.size();
			pageContext.setAttribute("size", size);
		%>
		
		<c:forEach items="${qls}" var="item" varStatus="loop">
		<c:choose>
		    <c:when test="${item.type == 0}">
		        <!-- Question 1 -->
                  <div class="card shadow mb-4" id="q${loop.index+1}" style="visibility:hidden;display:none;">
                      <div class="card-header py-2 d-sm-flex justify-content-between">
                          <h6 class="m-0 font-weight-bold text-primary">Question ${loop.index +1 }</h6>
                          <div> ${item.marks } Marks, ${item.co }</div>
                      </div>
                      <div class="card-body">
                          <h6 class="font-weight-bold">${item.question }</h6>
                          <div>
                              <div class="form-check-inline">
                                  <input class="form-check-input" type="radio" name="ques${item.id}"
                                      id="option1ForQuestionId" value="1">
                                  <label class="form-check-label" for="option1ForQuestion">
                                      ${item.a }
                                  </label>
                              </div>
								<br>
                              <div class="form-check-inline">
                                  <input class="form-check-input" type="radio" name="ques${item.id}"
                                      id="option2ForQuestionId" value="2">
                                  <label class="form-check-label" for="option2ForQuestion">
                                      ${item.b }
                                  </label>
                              </div>
								<br>
                              <div class="form-check-inline">
                                  <input class="form-check-input" type="radio" name="ques${item.id}"
                                      id="option3ForQuestionId" value="3">
                                  <label class="form-check-label" for="option3ForQuestion">
                                      ${item.c }
                                  </label>
                              </div>
								<br>
                              <div class="form-check-inline">
                                  <input class="form-check-input" type="radio" name="ques${item.id}"
                                      id="option4ForQuestionId" value="4">
                                  <label class="form-check-label" for="option4ForQuestion">
                                      ${item.d }
                                  </label>
                              </div>
                          </div>
                      </div>
                  </div>
		    </c:when>    
		    <c:otherwise>
		    <!-- Question 2 -->
            <div class="card shadow mb-4" id="q${loop.index+1 }" style="visibility:hidden;display:none;">
                <div class="card-header py-2 d-sm-flex justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Question ${loop.index +1 }</h6>
                    <div> ${item.marks } Marks, ${item.co }</div>
                </div>
                <div class="card-body">
                    <h6 class="font-weight-bold">${item.question }</h6>
                    <div>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" id="questionId" name="ques${item.id}"></textarea>
                        </div>
                    </div>
                </div>
            </div>
		    </c:otherwise>
		</c:choose>
          
          
		</c:forEach>
        </div>                        

        <div class="col-lg-3">

            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Time Left</h6>
                </div>
                <div class="card-body">
                    <div id="countdown" class="px-5 py-2 bg-gray-900 text-white">
                        <div align="center" id='tiles'></div>
                    </div>
					
                    <script>
                    	window.localStorage.setItem('endTime', <%=session.getAttribute("endtime")%>);
                        var hours, minutes, seconds; // variables for time units
                        var countdown = document.getElementById("tiles"); // get tag element
                        getCountdown();
                        setInterval(function () { getCountdown(); }, 1000);

                        function getCountdown() {
                        	
                        	var current = new Date();
                        	var n = Number(window.localStorage.getItem('endTime'))
                        	var end = new Date(n+1);
                        	if(end.getTime() > current.getTime()){
                        		var seconds_left = end.getTime() - current.getTime();
               					seconds_left = seconds_left / 1000;
               					console.log(seconds_left);
                                hours = pad(parseInt(seconds_left / 3600));
                                seconds_left = seconds_left % 3600;
                                minutes = pad(parseInt(seconds_left / 60));
                                seconds = pad(parseInt(seconds_left % 60));
                                countdown.innerHTML = "<span>" + hours + "</span> : <span>" + minutes + "</span> : <span>" + seconds + "</span>";	
                        	}else{
								document.getElementById("btnFinishAttempt").click();                        		
                        	}
                        }

                        function pad(n) {
                            return (n < 10 ? '0' : '') + n;
                        }
                    </script>
                </div>
            </div>

            <!-- Jump to Page -->
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Jump To Question</h6>
                </div>
                <div class="card-body">
                <c:forEach begin="1" end="${size }" varStatus="loop">
                	<c:if test="${ loop.count % 5 == 1}">
                	<c:out value="<div class='mt-4 mb-2'>"  escapeXml="false"/>
                    </c:if>
                    	<c:if test="${ loop.count == 1 || loop.count == 2}">
                        <button class="btn btn-success btn-circle " id="btn${loop.count }" onclick="unhide(${loop.count })">
                            ${loop.count }
                        </button>
                        </c:if>
                        <c:if test="${ loop.count != 1 && loop.count != 2}">
                        <button class="btn btn-primary btn-circle " id="btn${loop.index }" onclick="unhide(${loop.count })">
                            ${loop.count }
                        </button>
                        </c:if>
                     <c:if test="${loop.count % 5 == 0 }">
                    <c:out value="</div>" escapeXml="false"/>
                    </c:if>
				</c:forEach>
                </div>
            
            </div>
			</div>	
            <Button type="submit" class="btn btn-info" id="btnFinishAttempt" name="btnFinishAttempt" form="myForm">Finish Attempt</Button>
            
            </div>
            </form>
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
		return;
	}
%>