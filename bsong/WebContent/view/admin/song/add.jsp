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
                <h2>Thêm bài hát</h2>
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
                            ${error }
                            <%
                            if(request.getParameter("msg")!= null){
                                                    		out.print("<p style='color:red'>Xử lý không thành công.</p>");
                                                        }
                            %>
                            <%
                            Song song = null;
                                                        if(request.getAttribute("song")!=null){
                                                         song = (Song) request.getAttribute("song");
                                                        }
                            %>
                                <form role="form" action="" method="post" enctype="multipart/form-data" id="form">
                                    <div class="form-group">
                                        <label for="name">Tên bài hát</label>
                                        <input type="text"  value="<% if(request.getAttribute("song")!=null){out.print( song.getName());} %>" required="required" name="name" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label >Danh mục bài hát</label>
                                        <select name="category" class="form-control">
                                          <option value="" selected="selected">---Chọn danh mục---</option>
                                        <%
                                        @SuppressWarnings("unchecked")
                                        ArrayList<Cat> listcat = (ArrayList<Cat>) request.getAttribute("listcat");
                                        		for(Cat cat:listcat){
                                        		if(song!=null&&song.getId()==cat.getId()){
                                        %>
                                        <option value="<%=cat.getId()%>" selected="selected"><%=cat.getName() %></option>
	                                     <%}else{ %>
										  <option value="<%=cat.getId()%>"><%=cat.getName() %></option>
										 <%}} %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="picture">Hình ảnh</label>
                                        <input type="file" name="picture"  accept="image/*" />
                                    </div>
                                    <div class="form-group">
                                        <label for="mp3">Mp3</label>
                                        <input type="file" name="mp3" accept="audio/*" />
                                    </div>
                                    <div class="form-group">
                                        <label for="preview">Mô tả</label>
                                        <textarea class="form-control" rows="3" id="preview" name="preview"><% if(request.getAttribute("song")!=null){out.print( song.getPreview());} %></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="detail">Chi tiết</label>
                                        <textarea class="form-control" id="khungsoanthao" rows="5" name="khungsoanthao"><% if(request.getAttribute("song")!=null){out.print(song.getDetail());} %></textarea>
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
					},
					"category" : {
						required : true,
					},
					"picture" : {
						required : true,
					},
					"mp3" : {
						required : true,
					},
					"preview" : {
						required : true,
					},
					"khungsoanthao" : {
						required : true,
					},
				},
				messages : {
					"name" : {
						required : "	Vui lòng nhập tên bài hát",
					},
					"tenhoa" : {
						required : "	Vui lòng nhập danh mục ",
					},
					"picture" : {
						required : "	Vui lòng nhập hình ảnh",
					},
					"mp3" : {
						required : "Vui lòng nhập mp3",
					},
					"preview" : {
						required : "Vui lòng nhập mô tả",
					},
					"khungsoanthao" : {
						required : "Vui lòng nhập chi tiết",
					},
				}
			});
		});
	</script>
<script type="text/javascript">
	CKEDITOR.replace('khungsoanthao');

    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>