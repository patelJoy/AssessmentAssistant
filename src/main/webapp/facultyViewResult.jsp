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
                <th>From Time</th>
                <th>To Time</th>
                <th>Duration</th>
                <th>View Result</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>2020</td>
                <td>Python Programming</td>
                <td>23/03/2020</td>
                <td>09:00 PM </td>
                <td>11:00 PM</td>
                <td>2 Hours</td>
                <td><a href="result.jsp">Result</a></td>
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

<jsp:include page="facultyFooter.jsp" />