package controller.admin.cat;

import java.io.IOException;
import java.util.ArrayList;

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

public class OrderAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private CatDAO catDao;
    public OrderAdminController() {
        super();
        catDao = new CatDAO();
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
		
		int cid = Integer.parseInt(request.getParameter("cid"));
				System.out.println(cid);
		Cat cat = catDao.getCatByCid(cid);
		
		request.setAttribute("cat", cat);
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/order.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cid = Integer.parseInt(request.getParameter("cid"));
		int order = Integer.parseInt(request.getParameter("order"));
		
		Cat cat1 = new Cat(cid, "", 0, order);
		ArrayList<Cat> listcat = catDao.getCatList();
		for(Cat cat2: listcat) {
			if(cat2.getOrder()==cat1.getOrder()) {
				response.sendRedirect(request.getContextPath()+"/admin-cat-order?errOrder=1&cid=" + cid +"&order="+ order);
				return;
				
			}
		}
		
		if(catDao.editOrder(cid, order)>0) {
			response.sendRedirect(request.getContextPath()+"/admin-cat?msg=6");
			return;
		}else {
			response.sendRedirect(request.getContextPath()+"/admin-cat?msg=6");
			return;
		}
	}

}
