<%@page import="model.bean.User"%>
<%@page import="model.bean.Contact"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý người dùng </h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Advanced Tables -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <div class="row">
                             <div class="col-sm-6">
                                    <a href="<%=request.getContextPath() %>/admin-user-add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="post" action="<%=request.getContextPath()%>/admin-user">
                                        <input type="submit" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" value="${search }" name="timkiem" class="form-control input-sm" placeholder="Nhập tên người dùng" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>
                                <%
                                if(request.getParameter("msg")!=null){
                                	int msg =  Integer.parseInt(request.getParameter("msg"));
                                	if(msg==1){
                                		out.print("<p style='color:green'>Thêm thành công.</p>");
                                	}else if(msg==2){
                                		out.print("<p style='color:green'>Sửa thành công.</p>");
                                	}else if(msg==3){
                                		out.print("<p style='color:green'>Xoá thành công.</p>");
                                	}else{
                                		out.print("<p style='color:red'>Xử lý không thành công.</p>");
                                	}
                                }
                                %>
                                <%
                                	if (request.getParameter("err") != null) {
                                %>
                                <div class="alert alert-danger" role="alert">
								  Ban khong co quyen
								</div>
                                <%
                                	}
                                %>
                                <%
                               
                                	 @SuppressWarnings("unchecked")
                                    ArrayList<User>  listuser = (ArrayList<User>)request.getAttribute("listuser");
                                    if(listuser.size()>0){
                                %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên người dùng</th>
                                        <th>Họ tên</th>
                                        <th>Vai trò</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                
                                <%
                               
                                 for(User user: listuser){
                                %>
                                    <tr>
                                        <td><%=user.getId() %></td>
                                         <%
                                        if(request.getParameter("search")!=null){
                                        	String search = (String) request.getParameter("search");
                                        %>
                                        <td class="center"><%=user.getUsername().replace(search, "<bold style='color:red'>"+search+"</bold>")  %></td>
                                        <%}else{ %>
                                       <td class="center"><%=user.getUsername() %></td>
                                        <%} %>
                                        
                                        <td class="center"><%=user.getFullname() %></td>
                                        <td class="center"><% if (user.getIdRole()==2){out.print("Admin");}else if (user.getIdRole()==2){out.print("Mod");} else{ out.print("User");}%></td>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin-user-edit/<%=user.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin-user-del?uid=<%=user.getId() %>" title="" onclick="return confirm('bạn có muốn xoá?')" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                    <%}%>
                                </tbody>
                            </table>
                            <%}else{ %>
                              <p>Không có tin.</p>
                             <%}%>
                               <%
                               int currentPage = (Integer) request.getAttribute("currentPage");
                               int numberOfPages = (Integer) request.getAttribute("numberOfPages");
                               if(listuser.size()>0){
                               %>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px"></div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                            <%
                                        String active, disabled= "";
                                        int pagePrevious = currentPage - 1;
                                           if (currentPage == 1) {
                                          pagePrevious = currentPage;
                                          disabled ="disabled";
                                           }
                                          %>
                                        
                                            <li class="paginate_button previous <%=disabled %>" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous">
                                            
                                            	<a href="<%=request.getContextPath()%>/admin-user?page=<%=pagePrevious%>">Trang trước</a>
                                            </li>
                                            <%
                                            for(int i =1; i<= numberOfPages; i++){
                                            	if(currentPage==i){
                                            	active = "active";
                                            	}else{
                                            	active = "";
                                            	}
                                            %>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin-user?page=<%=i%>"><%=i %></a></li>
											<%} %>
                                            	<%
                                            		int pageNext = currentPage + 1;
												 disabled= "";
                                            		if(currentPage == numberOfPages) {
                                            			pageNext = currentPage;
                                            			disabled = "disabled";
                                            		}
                                            	%>
                                            <li class="paginate_button next <%=disabled %>" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next">
                                            <a href="<%=request.getContextPath()%>/admin-user?page=<%=pageNext%>">Trang tiếp</a></li>
                                         <%} %>   
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>
