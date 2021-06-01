<%@page import="model.bean.Song"%>
<%@page import="utils.StringUtil"%>
<%@page import="model.dao.SongDAO"%>
<%@page import="model.dao.CatDAO"%>
<%@page import="model.bean.Cat"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="searchform">
  <form id="formsearch" name="formsearch" method="post" action="<%=request.getContextPath()%>/public-index">
    <span>
    <label>Thể loại</label>
      <select name="category" class="form-control">
       <option value="0">Tất cả</option>
  		<%
  		String search = "";
  		  		if(request.getAttribute("search")!=null){
  		  			search = (String) request.getAttribute("search");
  		  		}
  		  		CatDAO catDaoSearch = new CatDAO();
  		         ArrayList<Cat> listcatsearch = catDaoSearch.getCatList();
  		         for(Cat cat:listcatsearch){
  		%>
           <option value="<%=cat.getId()%>"
            <%if(request.getAttribute("category")!=null && (cat.getId()== (Integer) request.getAttribute("category")))
            {out.print("selected");}%>>
            <%=cat.getName()%></option>
	     <%
	     }
	     %>
         </select> 
     </span>
     <span>    
    <input name="timkiem" value="<%=search%>" class="editbox_search" id="editbxox_search" maxlength="80" placeholder="Tìm kiếm bài hát" type="text" />
    </span>
    <input name="button_search" src="<%=request.getContextPath()%>/templates/public/images/search.jpg" class="button_search" type="image" />
  </form>
</div>
<div class="clr"></div>
<div class="gadget">
  <h2 class="star">Danh mục bài hát</h2>
  <div class="clr"></div>
  <ul class="sb_menu">
  <%
  CatDAO catDao= new CatDAO();
  	  ArrayList<Cat> listcat= catDao.getCatList();
  	  if(listcat.size()>0){
  		  for(Cat cat: listcatsearch){
  	 if(cat.getStatus()==1){
  %>
    <li><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(cat.getName())%>-<%=cat.getId()%>"><%=cat.getName()%></a></li>
  <%
  }
  		  }
  	  }
  %>
  </ul>
</div>

<div class="gadget">
  <h2 class="star"><span>Bài hát mới</span></h2>
  <div class="clr"></div>
  <ul class="ex_menu">
  <%
  SongDAO songDao = new SongDAO();
  	  ArrayList<Song> list6newsong = songDao.get6NewSong();
  	  if(list6newsong.size()>0){
  		  for(Song newsong: list6newsong){
  %>
    <li><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(newsong.getCat().getName()) %>/<%=StringUtil.makeSlug(newsong.getName()) %>-<%=newsong.getId() %>.html"><%=newsong.getName() %></a><br />
    <%
	    if (newsong.getPreview().length() > 20){
	  	  out.print(newsong.getPreview().substring(0,20));
	    } else {
	  	  out.print(newsong.getPreview());
	    }
    %>
    </li>
  <%
		  }
	  }
  %>
  </ul>
</div>