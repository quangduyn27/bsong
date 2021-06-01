package controller.admin.cat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.CatDAO;
import model.dao.SongDAO;
import utils.AuthUtil;

public class DelCatAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DelCatAdminController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(AuthUtil.checkLogin(request, response)== false) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(user.getIdRole()!=2) {
			request.setAttribute("error", "<p style='color:red'>Không được quyền vao.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/admin-index");
			rd.forward(request, response);
			return;
		}
		CatDAO catDao = new CatDAO();
		SongDAO songDao = new SongDAO();
		
		int cid = Integer.parseInt(request.getParameter("cid"));

		if(catDao.delcat(cid)>0&& songDao.delSongByCid(cid)>0) {
			
			response.sendRedirect(request.getContextPath()+ "/admin-cat?msg=3");
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin-cat?msg=4");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
