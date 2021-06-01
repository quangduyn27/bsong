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

public class AddCatAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCatAdminController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(user.getIdRole()!=2) {
			request.setAttribute("error", "<p style='color:red'>Không được quyền vào.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/admin-index");
			rd.forward(request, response);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		CatDAO catDao = new CatDAO();
				
		String name =  request.getParameter("name");
		if(name.equals("")) {
			request.setAttribute("error", "<p style='color:red'>Vui lòng nhập.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/add.jsp");
			rd.forward(request, response);
			return;
		}
		if(catDao.getCatSearch(name).size()>0) {
			request.setAttribute("error", "<p style='color:red'>Danh mục đã tồn tại.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/add.jsp");
			rd.forward(request, response);
			return;
		}
		Cat cat = new Cat(0, name,1,0);
		
		if(catDao.addcat(cat)>0) {
			response.sendRedirect(request.getContextPath()+ "/admin-cat?msg=1");
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin-cat-add?msg=4");
		}
		
	}

}
