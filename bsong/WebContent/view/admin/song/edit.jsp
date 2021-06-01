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
                <h2>Sửa bài hát</h2>
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
                            Song song = (Song) request.getAttribute("song");
                            %>
                                <form role="form" action="<%=request.getContextPath() %>/admin-song-edit" method="post" enctype="multipart/form-data" id="form">
                                    <div class="form-group">
                                        <label for="id">ID</label>
                                        <input type="text"  value="<%=song.getId() %>" name="sid" readonly="readonly" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Tên bài hát</label>
                                        <input type="text"  value="<%=song.getName() %>" name="name" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label >Danh mục bài hát</label>
                                        <select name="category" class="form-control">
                                        <%
                                        @SuppressWarnings("unchecked")
                                        ArrayList<Cat> listcat = (ArrayList<Cat>) request.getAttribute("listcat");
                                        		for(Cat cat:listcat){
                                        %>
                                        <option value="<%=cat.getId()%>" <%if(song.getCat().getId()==cat.getId()){out.print("selected");}%>><%=cat.getName() %></option>
	                                   <%} %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="picture">Hình ảnh</label>
                                        <img alt="" src="<%=request.getContextPath()%>/files/<%=song.getPicture()%>">
                                        <input type="file" name="picture" />
                                    </div>
                                    <div class="form-group">
                                        <label for="mp3">Mp3</label>
                                        <audio src="<%=request.getContextPath()%>/files/mp3/<%=song.getMp3()%>"></audio>
                                        <input type="file" name="mp3" />
                                    </div>
                                    <div class="form-group">
                                        <label for="preview">Mô tả</label>
                                        <textarea  class="form-control" rows="3" name="preview"><%=song.getPreview() %></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="detail">Chi tiết</label>
                                        <textarea  class="form-control" id="detail" rows="5" name="detail"><%=song.getDetail() %></textarea>
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