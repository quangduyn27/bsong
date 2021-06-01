<%@page import="model.bean.User"%>
<%@page import="model.bean.Cat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa danh mục</h2>
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
                            User user = (User) request.getAttribute("user");
                            %>
                                <form role="form" action="<%=request.getContextPath() %>/admin-user-edit" method="post" >
                                 <div class="form-group">
                                        <label for="id">ID</label>
                                        <input type="text" value="<%=user.getId() %>" readonly="readonly" name="uid" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Tên người dùng</label>
                                        <input type="text" id="name" value="<%=user.getUsername() %>" readonly="readonly" name="username" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Mật khẩu </label>
                                        <input type="text" id="name" value="" name="password" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Họ tên</label>
                                        <input type="text" id="name" value="<%=user.getFullname() %>" name="fullname" class="form-control" />
                                    </div>
                                    <%
                                    	if (userLogin.getIdRole() == 2 && user.getId()!=userLogin.getId()) {
                                    %>
                                    <div class="form-group">
                                    	<label for="idrole">Vai tro</label>
                                    	<select name="idrole" id="idrole" class="form-control">
                                    		<option value="2" <% if(user.getIdRole() == 2) { out.print("selected"); } %>>Admin</option>
                                    		<option value="1" <% if(user.getIdRole() == 1) { out.print("selected"); } %>>Mod</option>
                                    		<option value="0" <% if(user.getIdRole() == 0) { out.print("selected"); } %>>User</option>
                                    	</select>
                                    </div>
                                    <%
                                    	}
                                    %>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Sửa</button>
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