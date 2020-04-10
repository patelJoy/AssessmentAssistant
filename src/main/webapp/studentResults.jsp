<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
                 <table class="table table-bordered" id="studentResultTable" width="100%" cellspacing="0">
                   <thead>
                     <tr>
                       <th>Subject</th>
                       <th>Category</th>
                       <th>Date</th>
                       <th>Total Marks</th>
                       <th>Obtained Marks</th>
                       <th>Raise query</th>
                     </tr>
                   </thead>
                   <tbody>
                       <tr>
                         <td>Java</td>
                         <td>Midsem</td>
                         <td>23/03/2020</td>
                         <td>30</td>
                         <td>28</td>
                         <td>
                           <a href="#" class="btn btn-primary btn-circle btn-sm">
                             <i class="fa fa-exclamation"></i>
                           </a>
                         </td>
                       </tr>
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