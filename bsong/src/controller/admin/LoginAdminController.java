package controller.admin;

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

public class LoginAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDao;

	public LoginAdminController() {
		super();
		userDao = new UserDAO();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/admin-index"); // url hiện đường dẫn
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username.equals("") | password.equals("")) {
			request.setAttribute("error", "vui long nhap");
			RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
			rd.forward(request, response);
			return;
		}
		password = StringUtil.md5(password);
		User user = userDao.existUser(username, password);
		if (user != null) {

			session.setAttribute("userLogin", user);
			response.sendRedirect(request.getContextPath() + "/admin-index");
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/auth/login.jsp?msg=4");
			return;
		}

	}

}
