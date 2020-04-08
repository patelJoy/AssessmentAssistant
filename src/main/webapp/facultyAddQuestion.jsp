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
 <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Add Questions</h1>
          </div>
          <div class="row">
            <div class="col-lg-12">

              <!-- Basic Card Example -->
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">Upload the doucment for adding the Questions</h6>
                </div>
                <div class="card-body">
                  The document should be in the form of an excel sheet (.xls or .xlsx extension)
                </div>
                <div>
                      <div class="row">
                        <div class="col-lg-5 mx-auto">
                          <div class="card shadow mb-4">
                            <div>
                          <div class="p-5 bg-white shadow rounded-lg"><img src="C:\Users\Bhavya\Desktop\images.png" alt="" width="200" class="d-block mx-auto mb-4 rounded-pill">
                  			<form class="user" method="POST" enctype="MULTIPART/FORM-DATA" action="QuestionsImport">
							<input type="hidden" name="testId" value="<%= request.getAttribute("testId")%>">
							<input type="hidden" name="batch" value="<%= request.getAttribute("batch")%>">
                  			<br>
                            <div class="custom-file overflow-hidden rounded-pill mb-5">
                              <input id="customFile" name="fileUpload" type="file" class="custom-file-input rounded-pill">
                              <label for="customFile" class="custom-file-label rounded-pill">Choose file</label>
                            </div>
                            <!-- End -->
                            <div>
                              <Button type="submit" class="btn btn-primary btn-icon-split btn-sm">
                                <span class="icon text-white-50">
                                  <i class="fas fa-plus"></i>
                                </span>
                                <span class="text">Add Questions</span>
                              </Button>
                            </div>
                            </form>
                           </div>             
                          </div>
                        </div>
                      </div>
                     </div>                  
                </div>
              </div>
            </div>
          </div>
        </div>
<jsp:include page="facultyFooter.jsp" />
<script type="application/javascript">
    $('input[type="file"]').change(function(e){
        var fileName = e.target.files[0].name;
        $(e.target).parent('div').find('label').html(fileName)
    });
</script>
<%
  } else{
		out.println("<script>alert('SESSION INVALID!!! PLEASE LOGIN AGAIN!!!!!');</script>");
		response.sendRedirect("login.jsp");
	}
%>