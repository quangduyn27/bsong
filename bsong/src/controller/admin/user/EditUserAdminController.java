package controller.admin.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.UserDAO;
import utils.AuthUtil;
import utils.StringUtil;

public class EditUserAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditUserAdminController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(AuthUtil.checkLogin(request, response)== false) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		int uid = Integer.parseInt(request.getParameter("uid"));
		
		User userLogin = AuthUtil.checkUser(request, response);
		if(userLogin != null) {
			if (userLogin.getId() != uid && userLogin.getIdRole() == 0) {
				response.sendRedirect(request.getContextPath() + "/admin-user?err=2");
				return;
			}
		}
		
		UserDAO userDao = new UserDAO();
		request.setAttribute("user", userDao.getUserByCid(uid));
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/user/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		UserDAO userDao = new UserDAO();
		int uid = Integer.parseInt(request.getParameter("uid"));
		User user = userDao.getUserByCid(uid);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int idRole = user.getIdRole();
		if(request.getParameter("idrole")!=null) {
		 idRole = Integer.parseInt(request.getParameter("idrole"));
		}
		if("".equals(password)) {
			password = user.getPassword();
		}else {
			password = StringUtil.md5(password);
		}
		String fulname = request.getParameter("fullname");
		if (fulname.equals("")) {
			request.setAttribute("error", "Vuilòng nhập");
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/add.jsp");
			rd.forward(request, response);
			return;
		}
		User user1 = new User(uid, username, password, fulname, idRole);

		if (userDao.editUser(user1) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin-user?msg=2");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin-user?msg=4");
		}
	}

}
