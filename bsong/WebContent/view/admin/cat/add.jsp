<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                            ${error}
                            <%
                            if(request.getParameter("msg")!=null){
                            	int msg =  Integer.parseInt(request.getParameter("msg"));
                            	if(msg==4){
                            		out.print("<p style='color:red'> Đã có lỗi xảy ra.</p>");
                            	}
                            }
                            %>
                            
                                <form role="form" action="" method="post" id="form">
                                    <div class="form-group">
                                        <label for="name">Tên danh mục</label>
                                        <input type="text" id="name" value="" name="name" class="form-control" />
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Thêm</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Form Elements -->
            </div>
        </div>
        <!-- /. ROW  -->
    </div>
    <!-- /. PAGE INNER  -->
</div>
<style>
.error {
	color: red;
}
</style>
<script type="text/javascript">
		$(document).ready(function() {
			$('#form').validate({
				rules : {
					"name" : {
						required : true,
					}
				},
				messages : {
					"name" : {
						required : "Vui lòng nhập tên bài hát",
					}
				}
			});
		});
	</script>
<script>
    document.getElementById("cat").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>