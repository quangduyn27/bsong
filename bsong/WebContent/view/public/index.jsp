<%@page import="model.bean.Song"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  
  
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
                            if(request.getAttribute("listsong")!=null){
                            	ArrayList<Song> listsong = (ArrayList<Song>)request.getAttribute("listsong");
                            	if(listsong.size()>0){
                            		int stt = 0;
                              	for(Song song: listsong){
                            %>

    <div class="article">
      <h2><a href="<%=request.getContextPath()%>/public-detail?sid=<%=song.getId()%>" title="<%=song.getName()%>"><%=song.getName()%></a></h2>
      <p class="infopost">Ngày đăng: <%=song.getDaycreate()%> Lượt xem: <%=song.getView()%> <a href="#" class="com"><span><%=stt+=1%></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath()%>/files/<%=song.getPicture()%>" width="177" height="213" alt="<%=song.getName()%>" class="fl" /></div>
      <div class="post_content">
        <p><%=song.getPreview()%></p>
        <p class="spec"><a href="<%=request.getContextPath()%>/public-detail?sid=<%=song.getId()%>" class="rm">Chi tiết &raquo;</a></p>
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
        String search = "";
            int cid = 0;
            if (request.getAttribute("search") != null) {
            	search = (String) request.getAttribute("search");
            }
            if (request.getAttribute("category") != null) {
            	cid = (int) request.getAttribute("category");
            }
            int currentpage =(Integer) request.getAttribute("currentpage");
            int numberOfPages =(Integer) request.getAttribute("numberOfPages");
        %>
    <p class="pages"><small>Trang <%=currentpage%> của <%=numberOfPages%></small>
    <%
    for(int i = 1; i<=numberOfPages; i++){
        	if(currentpage == i){
    %>
    <span><%=i%></span>
    <%
    }else{
    %>
    <a href="<%=request.getContextPath()%>/trang-chu/page/<%=i%><%if (!search.equals("")) { out.print("&timkiem=" + search);} if (cid != 0){ out.print("&category="+cid);}%>"><%=i%></a>
    <%
    }}
    %>
   	<%
   	int nextpage = currentpage +1;
   	   	if(currentpage == numberOfPages){
   	   		nextpage = currentpage;
   	   	}
   	%>
    <a href="<%=request.getContextPath()%>/trang-chu-page-<%=nextpage%>">&raquo;</a></p>
    <%
    }
    %>
  </div>
  
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
<script>
    document.getElementById("index").classList.add('active-menu');
</script>
