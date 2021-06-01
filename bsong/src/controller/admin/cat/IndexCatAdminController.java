
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
import utils.DefineUtil;

public class IndexCatAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IndexCatAdminController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
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
		
		CatDAO catdao = new CatDAO();
		ArrayList<Cat> listcat = new ArrayList<Cat>();
		int numberOfItems = catdao.getNumberOfCat();
		int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			try {
	 			currentPage = Integer.parseInt(request.getParameter("page"));
				if (currentPage < 1) {
					throw new Exception();
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/admin-cat?msgErr=URL");
				return;
			}
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;

		listcat = catdao.getCatListPagination(offset);
		if (request.getParameter("search") != null) {
			String search = request.getParameter("search");
			listcat = catdao.getCatSearch(search);
			request.setAttribute("search", search);
		}
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("listcat", listcat);
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/cat/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CatDAO catDao = new CatDAO();
		int trangthai = Integer.parseInt(request.getParameter("atrangthai"));
		System.out.println(trangthai);
		int cid = Integer.parseInt(request.getParameter("acid"));
		System.out.println(cid);
		if (trangthai == 1) {
		
			Cat cat = new Cat(cid,"", 0,0);
			if(catDao.activeCat(cat)>0) {
				System.out.println("Đã tắt thư mục ");
			}
			response.getWriter()
					.print("<a href=\"javascript:void(0)\" onclick=\"return getActive(0,"+cat.getId()+")\" title=\"\"><img src=\""
							+ request.getContextPath() + "/templates/public/images/deactive.gif\"\n" + "alt=\"\" />");
		} else {
			Cat cat = new Cat(cid,"", 1,0);
			if(catDao.activeCat(cat)>0) {
				System.out.println("Đã tắt thư mục");
			}
			response.getWriter()
					.print("<a href=\"javascript:void(0)\" onclick=\"return getActive(1,"+cat.getId()+")\" title=\"\"><img src=\""
							+ request.getContextPath() + "/templates/public/images/active.gif\"\n" + "alt=\"\" />");
		}
	}

	

}
