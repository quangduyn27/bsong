	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
    <%
    Song songbysid = (Song) request.getAttribute("songbydid");
    %>
      <div class="article">
      <h1><%=songbysid.getName()%></h1>
      <div class="clr"></div>
      <p>Ngày đăng: <%=songbysid.getDaycreate()%> Lượt xem: <%=songbysid.getView()%></p>
      <div class="vnecontent">
         <%=songbysid.getDetail()%>
      </div>
     <audio src="<%=request.getContextPath()%>/files/mp3/<%=songbysid.getMp3()%>" controls="controls" autoplay="autoplay" ></audio>
   </div>
     <%
     if(session.getAttribute("userLogin")!=null){
         	  User user = (User) session.getAttribute("userLogin");
     %>
      
      <label>Yêu thích</label>
  	  <%
  	  if(request.getAttribute("favorid")!= null){
  	  %>
      <a href="javascript:void(0)" id="active" onclick= "return getLike(0)" title=""><img src="<%=request.getContextPath()%>/templates/public/images/deactive.gif" alt="" /></a>
      <%
      }else{
      %>
     <a href="javascript:void(0)" id="active" onclick= "return getLike(1)" title=""><img src="<%=request.getContextPath()%>/templates/public/images/active.gif" alt="" /></a>
     
      <%
           }}
           %>
    
    <%
        @SuppressWarnings("unchecked")
            ArrayList<Song> listrandsong = (ArrayList<Song>) request.getAttribute("listrandsong");
            if(listrandsong.size()>0){
        %>
    <div class="article">
      <h2>Bài viết liên quan</h2>
     
      <div class="clr"></div>
        <%
        for(Song randsong: listrandsong){
        %>
      <div class="comment"> 
     
      <a href=""><img src="<%=request.getContextPath()%>/files/<%=randsong.getPicture()%>" width="40" height="40" alt="" class="userpic" /></a>
        <h2><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(randsong.getCat().getName())%>/<%=StringUtil.makeSlug(randsong.getName())%>-<%=randsong.getId()%>.html"><%=randsong.getName()%></a></h2>
        <p><%=randsong.getPreview()%></p>
      </div>
      <%
      }
      %>
    </div>
    <%
    }else{
    %>
    <p>Không có bài hát </p>
    <%
    }
    %>
  </div>
  <script type="text/javascript">
		function getLike(trangthai) {
			 var idsong = <%=songbysid.getId()%>;
			$.ajax({
				url : './public-favorite-song',
				type : 'post',
				cache : false,
				data : {
					aidsong: idsong, 
					atrangthai: trangthai
				},
				success : function(data) {
					$("#active").html(data);
				},
				error : function() {
					alert("Đã có lỗi trong quá trình xử lý.");
				}
			});
			return false;
		}
	</script>
  <div class="sidebar">
  <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
