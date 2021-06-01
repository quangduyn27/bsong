<%@page import="model.bean.Contact"%>
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
                            Contact cont = (Contact) request.getAttribute("contact");
                            %>
                                <form role="form" action="<%=request.getContextPath() %>/admin-contact-edit" method="post" >
                                 <div class="form-group">
                                        <label for="id">ID</label>
                                        <input type="text" value="<%=cont.getId() %>" readonly="readonly" name="contid" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Tên danh mục</label>
                                        <input type="text" id="name" value="<%=cont.getName() %>" name="name" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Email</label>
                                        <input type="text" id="name" value="<%=cont.getEmail() %>" name="email" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Website</label>
                                        <input type="text" id="name" value="<%=cont.getWebsite() %>" name="website" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Nội Dung</label>
                                        <input type="text" id="name" value="<%=cont.getContent() %>" name="message" class="form-control" />
                                    </div>
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