package controller.admin.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.UserDAO;
import utils.AuthUtil;

public class DelUserAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DelUserAdminController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("uid"));
		User userLogin = AuthUtil.checkUser(request, response);
		
		if(userLogin != null) {
			if (userLogin.getId() != uid && userLogin.getIdRole() == 0) {
				response.sendRedirect(request.getContextPath() + "/admin-user?err=2");
				return;
			}
		}
		UserDAO userDao = new UserDAO();
		
		if(userDao.delUser(uid)>0) {
			response.sendRedirect(request.getContextPath()+ "/admin-user?msg=3");
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin-user?msg=4");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
