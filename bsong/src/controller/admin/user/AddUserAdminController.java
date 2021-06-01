package controller.admin.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UserDAO;
import utils.AuthUtil;
import utils.StringUtil;

public class AddUserAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddUserAdminController() {
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
			request.setAttribute("error", "<p style='color:red'>Không được quyền thêm.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/admin-user");
			rd.forward(request, response);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/user/add.jsp");
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
		UserDAO userDao = new UserDAO();
				
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fulname = request.getParameter("fullname");
		int idRole = Integer.parseInt(request.getParameter("idrole"));
		if (username.equals("")|password.equals("")|fulname.equals("")) {
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/user/add.jsp?msg=2");
			rd.forward(request, response);
			return;
		}
		if(userDao.hasUser(username).size()>0) {
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/user/add.jsp?msg=1");
			rd.forward(request, response);
			return;
		}
		//mã hoá pass
		password = StringUtil.md5(password);
		User user = new User(0, username, password, fulname, idRole);
		
		if (userDao.addUser(user) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin-user?msg=1");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin-user?msg=4");
		}
	}

}
