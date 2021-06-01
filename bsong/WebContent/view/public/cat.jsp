<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  <%
  Cat catbycid = (Cat) request.getAttribute("cat");
  %>
    <div class="article">
		<h1><%=catbycid.getName()%></h1>
    </div>
    <%
    ArrayList<Song> listsongbycid = (ArrayList<Song>)request.getAttribute("listsongbycid");
        	if(listsongbycid.size()>0){
        		int s = 1;
        		for(Song songbycid: listsongbycid){
    %>
    <div class="article">
      <h2><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(songbycid.getCat().getName())%>/<%=StringUtil.makeSlug(songbycid.getName())%>-<%=songbycid.getId()%>.html" title="<%=songbycid.getName()%>"><%=songbycid.getName()%></a></h2>
      <p class="infopost">Ngày đăng: <%=songbycid.getDaycreate()%> Lượt xem: <%=songbycid.getView()%> <a href="#" class="com"><span><%=s++%></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath()%>/files/<%=songbycid.getPicture()%>" width="177" height="213" alt="<%=songbycid.getName()%>" class="fl" /></div>
      <div class="post_content">
        <p>“Nhớ…tiếng mưa rơi ngày xưu…lúc đôi ta còn nhau, khi tình yêu… bắt đầu…….” Những ca từ quen thuộc của ngày nào bổng vang lên giữa một buổi chiều mưa nhẹ rơi…Đã từ rất lâu rồi tôi mới được nghe lại bài hát này. Bài hát khiến tôi nhớ về kỷ niệm một thời mà tôi cứ nghỡ như chuyện mới vừa xãy ra hôm qua vậy…!!!.</p>
        <p class="spec"><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(songbycid.getCat().getName())%>/<%=StringUtil.makeSlug(songbycid.getName())%>-<%=songbycid.getId()%>.html" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
    </div>
    <%
    }}else{
    %>
    <p>Không có bài hát</p>
    <%
    }
    %>
    <%
    int numberOfPages = (Integer)request.getAttribute("numberOfPages");
        int currentPage = (Integer)request.getAttribute("currentPage");
    %>
    <p class="pages"><small>Trang <%=currentPage%> của <%=numberOfPages%></small>
	 <%
	 for(int i= 1; i<=numberOfPages; i++){ 
	     	if(currentPage == i){
	 %>
    <span><%=i%></span>
    <%
    }else{
    %>
    <a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(catbycid.getName())%>/page-<%=i%>/<%=catbycid.getId()%>"><%=i%></a>
     <%
     }}
     %>
    <a href="#">&raquo;</a></p>
  </div>
 
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>