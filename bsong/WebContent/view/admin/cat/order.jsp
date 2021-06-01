<%@page import="model.bean.Cat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sắp xếp danh mục</h2>
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
                            int order =0, cid = 0;
                            if(request.getParameter("errOrder")!=null){
                            	out.print("<p style='color:red'> Đã có thư mục ở số này.</p>");
                            	out.print("<p style='color:red'> Bạn có muốn tiêp tục lưu?.</p>");
                            	 order = Integer.parseInt(request.getParameter("order"));
                      			  cid = Integer.parseInt(request.getParameter("cid"));
                            }
                            
                            if(request.getParameter("msgOrder1")!=null){
                            	out.print("<p style='color:red'> Thay đổi thứ tự không thành công.</p>");
                            }
                            %>
                            <%
                            Cat cat = (Cat) request.getAttribute("cat");
                            %>
                                <form role="form" action="" method="post" >
                                    <div class="form-group">
                                        <label for="name">Tên danh mục</label><br>
                                        <span class="center" ><bold><%=cat.getName() %></bold></span>
                                     </div>
                                    <div class="form-group">
                                        <label for="name">Thứ tự hiển thị</label>
                                        <%if(request.getParameter("errOrder")!=null){%>
                                        <input type="text" id="order" readonly="readonly" value="<%=order%>" name="order"  />
                                        <%}else{ %>
                                        <input type="text" id="order" value="<%=cat.getOrder()%>" name="order"  />
										<%} %>                                       
                                    </div>
                                   
                                <%
                           		 if(request.getParameter("errOrder")!=null){
                           			 
                            	%>
                                <a href="<%=request.getContextPath()%>/admin-cat-continue?cid=<%=cid %>&order=<%=order %>" name="submit" class="btn btn-success btn-md">Tiếp tục</a>
                                <a href="<%=request.getContextPath()%>/admin-cat-order?cid=<%=cid %>" name="submit" class="btn btn-success btn-md">Huỷ</a>

                           		 <%}else{%>
                           		 
                           		  <button type="submit" name="submit" class="btn btn-success btn-md">Sửa</button>
                           		  <%} %>
                           		 
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
    document.getElementById("cat").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>