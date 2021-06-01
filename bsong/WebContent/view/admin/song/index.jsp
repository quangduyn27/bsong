<%@page import="model.bean.Song"%>
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
                <h2>Quản lý bài hát</h2>
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
                                    <a href="<%=request.getContextPath()%>/admin-song-add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="post" action="">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="timkiem" class="form-control input-sm" placeholder="Nhập tên bài hát" style="float:right; width: 300px;" />
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
                                                                	if (request.getParameter("msgErr") != null) {
                                %>
                                <div class="alert alert-danger" role="alert">
								  Loi url, so trang khong hop le
								</div>
                                <%
                                }
                                %>
                                <%
                                // Cat cat = (Cat)request.getAttribute("cat");
                                                               if(request.getAttribute("listsong")!=null){
                                                                	 @SuppressWarnings("unchecked")
                                                                	 ArrayList<Song> listsong = (ArrayList<Song>)request.getAttribute("listsong");
                                                                if(listsong.size()>0){
                                %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên bài hát</th>
                                        <th>Danh mục</th>
                                        <th>Lượt đọc</th>
                                        <th>Hình ảnh</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                 <%
                                 for(Song song: listsong){
                                 %>
                                    <tr>
                                         <td><%=song.getId() %></td>
                                         <%
                                         if(request.getParameter("sid")!=null && (song.getId()==Integer.parseInt(request.getParameter("sid")))){
                                         %>
                                        <td class="center"><%="<strong style='color:red'>"+ song.getName()+ "</strong>" %></td>
                                      <%}else{ %>
                                       <td class="center"><%=song.getName() %></td>
                                      <%} %>
                                        <td class="center"><%=song.getCat().getName() %></td>
                                        <td class="center"><%=song.getView() %></td>
                                        <td class="center">
                                        <%if(song.getPicture().isEmpty()){ %>
                                        <p>Chưa có hình ảnh</p>
                                        <%}else{ %>
											<img width="200px" height="200px" src="<%=request.getContextPath() %>/files/<%=song.getPicture() %>" alt="<%=song.getName()%>"/>
											<%} %>
                                        </td>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin-song-edit/<%=song.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin-song-del?sid=<%=song.getId() %>" onclick = "return confirm('bạn có muốn xoá?')" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                   
                                 <%}}else{ %>
                                 <p>Không có bài hát </p>
                                 <%} %>
                                </tbody>
                            </table>
                            <%
                            int numberOfPages = (Integer)request.getAttribute("numberOfPages");
                            int currentPage = (Integer)request.getAttribute("currentPage");
                            if(listsong.size()>0){
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
                                            
                                            	<a href="<%=request.getContextPath()%>/admin-song?page=<%=pagePrevious%>">Trang trước</a>
                                            </li>
                                            <%
                                            for(int i =1; i<= numberOfPages; i++){
                                            	if(currentPage==i){
                                            	active = "active";
                                            	}else{
                                            	active = "";
                                            	}
                                            %>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin-song?page=<%=i%>"><%=i %></a></li>
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
                                            <a href="<%=request.getContextPath()%>/admin-song?page=<%=pageNext%>">Trang tiếp</a></li>
                                         <%} %>   
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <%} %>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>
