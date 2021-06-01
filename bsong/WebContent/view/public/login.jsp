<%@page import="model.bean.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
 
 ${error }
 <%
 if(request.getParameter("msg")!=null){
	 out.print("<p style='color:red'>Tên hoặc mật khẩu không đúng.</p>");
 }
 %>
 
 
  <form action="<%=request.getContextPath() %>/public-login" method="post">
  <label>Username </label>
  <input type="text" value="" name="username"><br><br>
  <label>Password </label>
  <input type="password" value="" name="password"><br><br>
  <button type="submit" name="submit" value="" >Đăng nhập</button>
  </form>
  
  
  
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
