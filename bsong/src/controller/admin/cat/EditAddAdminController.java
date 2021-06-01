package controller.admin.cat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Cat;
import model.bean.User;
import model.dao.CatDAO;
import utils.AuthUtil;

public class EditAddAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditAddAdminController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(AuthUtil.checkLogin(request, response)== false) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		int cid = Integer.parseInt(request.getParameter("cid"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(user.getIdRole()!=2) {
			request.setAttribute("error", "<p style='color:red'>Không được quyền vao.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/admin-index");
			rd.forward(request, response);
			return;
		}
		
		CatDAO catDao = new CatDAO();
		request.setAttribute("cat", catDao.getCatByCid(cid));
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(AuthUtil.checkLogin(request, response)== false) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		CatDAO catDao = new CatDAO();
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name =  request.getParameter("name");
		if(name.equals("")) {
			request.setAttribute("error", "Vuilòng nhập");
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/add.jsp");
			rd.forward(request, response);
			return;
		}
		Cat cat = new Cat(cid, name,1,0);
		
		if(catDao.editCat(cat)>0) {
			response.sendRedirect(request.getContextPath()+ "/admin-cat?msg=2");
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin-cat?msg=4");
		}
		
	}

}
