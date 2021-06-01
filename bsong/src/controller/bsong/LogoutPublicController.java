package controller.bsong;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.AuthUtil;

public class LogoutPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogoutPublicController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  HttpSession session = request.getSession();
	  if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+"/view/public/login.jsp");
			return;
		}
	  session.invalidate();
	  response.sendRedirect(request.getContextPath()+"/public-login");
	  return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
