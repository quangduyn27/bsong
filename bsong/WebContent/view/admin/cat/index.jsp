<%@page import="model.bean.Cat"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý danh mục</h2>
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
                                    <a href="<%=request.getContextPath() %>/admin-cat-add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                               
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" name="search" action="<%=request.getContextPath() %>/admin-cat">
                                        <input type="submit" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" value="${search }" name="search" class="form-control input-sm" placeholder="Nhập tên bài hát" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>
							 ${error }
                                <%
                                if(request.getParameter("msg")!=null){
                                	int msg =  Integer.parseInt(request.getParameter("msg"));
                                	if(msg==1){
                                		out.print("<p style='color:green'>Thêm thành công.</p>");
                                	}else if(msg==2){
                                		out.print("<p style='color:green'>Sửa thành công.</p>");
                                	}else if(msg==3){
                                		out.print("<p style='color:green'>Xoá thành công.</p>");
                                	}else if(msg==6){
                                		out.print("<p style='color:green'>Thay đổi thứ tự thành công.</p>");
                                	}
                                	else{
                                		out.print("<p style='color:red'>Xử lý không thành công.</p>");
                                	}
                                }
                                %>
                                 <%
                                	if (request.getParameter("msgErr") != null) {
                                %>
                                <div class="alert alert-danger" role="alert">
								  Loi url, so trang khong hop le
								</div>
                                <%
                                	}
                                %>
                                <%
                                ArrayList<Cat> listcat = new ArrayList<Cat>();
                                if(request.getAttribute("listcat")!=null){
                                	  listcat = (ArrayList<Cat>)request.getAttribute("listcat");
                                if(listcat.size()>0){
                               %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên người dùng</th>
                                        <th>Trạng thái</th>
                                        <th>Sắp xếp</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%
                                for(Cat cat: listcat){
                                %>
                                    <tr>
                                        <td ><%=cat.getId() %></td>
                                        <%
                                        if(request.getParameter("search")!=null){
                                        	String search = (String) request.getParameter("search");
                                        %>
                                        <td class="center"><%=cat.getName().replace(search, "<bold style='color:red'>"+search+"</bold>")  %></td>
                                        
                                        <%}else{ %>
                                        
                                        <td class="center"><%=cat.getName() %></td>
                                        
                                        <%} %>
                                        
                                        <td id="active<%=cat.getId() %> class="center">
                                        <%
                                        if(cat.getStatus()==0){
                                        %>
                                        <a href="javascript:void(0)"  onclick= "return getActive(0,<%=cat.getId() %>)" title=""><img src="<%=request.getContextPath() %>/templates/public/images/deactive.gif" alt="" /></a>
                                        
                                        <%}else{ %>
    										 <a href="javascript:void(0)"  onclick= "return getActive(1,<%=cat.getId() %>)" title=""><img src="<%=request.getContextPath() %>/templates/public/images/active.gif" alt="" /></a>
    										 <%} %>
                                        </td>
                                        <td class="center">
                                        <%=cat.getOrder() %><br>
                                       <a href="<%=request.getContextPath() %>/admin-cat-order?cid=<%=cat.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sắp xếp</a>
                                        
                                        </td>
                                        
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin-cat-edit/<%=cat.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i>Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin-cat-del?cid=<%=cat.getId() %>" onclick="return confirm('bạn có chắc muốn xoá?')" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                    
                                <%}}else{ %>
                                 
                                <p>Không có danh mục</p>
                                 
                                <%}} %>
                                </tbody>
                            </table>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px"></div>
                                </div>
                                <%
                                int numberOfPages = (Integer) request.getAttribute("numberOfPages");
                                int currentPage = (Integer) request.getAttribute("currentPage");
                                if(listcat.size()>0){
                                 
                                %>
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
                                        <li class="paginate_button previous <%=disabled %>" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin-cat?page=<%=pagePrevious%>">Trang trước</a></li>
                                        <%
                                            for(int i =1; i<= numberOfPages; i++){
                                        	if(currentPage==i){
                                        	active = "active";
                                        	}else{
                                        	active = "";
                                        	}
                                        %>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin-cat?page=<%=i%>"><%=i %></a></li>
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
                                            <a href="<%=request.getContextPath()%>/admin-cat?page=<%=pageNext%>">Trang tiếp</a></li>
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
<script type="text/javascript">
		function getActive(trangthai, cid) {
				$.ajax({
				url : './admin-cat',
				type : 'post',
				cache : false,
				data : {
					atrangthai: trangthai,
					acid: cid
				},
				success : function(data) {
					$("#active"+cid).html(data);
				},
				error : function() {
					alert("Đã có lỗi trong quá trình xử lý.");
				}
			});
			return false;
		}
	</script>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>
