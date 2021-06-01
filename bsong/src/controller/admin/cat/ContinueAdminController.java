package controller.admin.cat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Cat;
import model.dao.CatDAO;

public class ContinueAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ContinueAdminController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int cid = Integer.parseInt(request.getParameter("cid"));
		int order = Integer.parseInt(request.getParameter("order"));
		CatDAO catDao = new CatDAO();

		Cat cat1 = new Cat(cid, "", 0, order);
		ArrayList<Cat> listcat = catDao.getCatList();
		for (Cat cat2 : listcat) {
			if (cat2.getOrder() == cat1.getOrder()) {
				System.out.println(cat1.getOrder());
				cat2.setOrder(0);
				if (catDao.editOrder(cat1.getId(), cat1.getOrder()) > 0
						&& catDao.editOrder(cat2.getId(), cat2.getOrder()) > 0) {
					response.sendRedirect(request.getContextPath() + "/admin-cat?msg=6");
					return;
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
