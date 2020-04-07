<jsp:include page="facultySidebar.jsp" />
<jsp:include page="facultyTopbar.jsp" />

<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800"><center>Assessment Report</center></h1>

    <!-- DataTales Example -->
    <div class="card shadow mb-4">
      <div class="card-body">
        <div class="table-responsive">
            <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                <div class="row">
                    <div class="col-sm-12 col-md-6">
                        <div id="dataTable-filter" class="dataTables_filter">
                            <label>Batch : </label>
                            <select id="" style="width: 200px;">
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-6">

                    </div>
                </div>
            </div>
          <table class="table table-bordered mt-4" id="dataTable" width="100%" cellspacing="0">
            <thead>
              <tr>
                <th>Enrollment No.</th>
                <th>Name</th>
                <th>View Report</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>160170116001</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116002</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116003</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116004</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116005</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116006</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116007</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
              </tr>
              <tr>
                <td>160170116008</td>
                <td>abc xyz</td>
                <td><a href="report.jsp">Report</a></td>
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