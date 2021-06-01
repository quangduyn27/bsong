<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm người dùng</h2>
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
                            <%
                            if(request.getParameter("msg")!=null){
                            	int msg = Integer.parseInt(request.getParameter("msg"));
                            	if(msg==1){%>
                            	<div class="alert alert-danger" role="alert">
								  Tên đã tồn tại.
								</div>
                            		out.print("<p style='color:red'></p>");
                            	<%}else{%>
                            		<div class="alert alert-danger" role="alert">
                            		Vui lòng nhập.
  								</div>
                           		 <%}} %>
                                <form role="form" action="<%=request.getContextPath() %>/admin-user-add" method="post" >
                                    <div class="form-group">
                                        <label for="name">Tên người dùng</label>
                                        <input type="text" id="name" value="" name="username" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Mật khẩu </label>
                                        <input type="text" id="name" value="" name="password" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Họ tên</label>
                                        <input type="text" id="name" value="" name="fullname" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                    	<label for="idrole">Vai tro</label>
                                    	<select name="idrole" id="idrole" class="form-control">
                                    		<option value="2">Admin</option>
                                    		<option value="1">Mod</option>
                                    		<option value="0">User</option>
                                    	</select>
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
<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>