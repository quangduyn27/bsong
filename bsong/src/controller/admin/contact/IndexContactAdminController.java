	package controller.admin.contact;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Contact;
import model.bean.User;
import model.dao.ContactDAO;
import utils.AuthUtil;
import utils.DefineUtil;

public class IndexContactAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IndexContactAdminController() {
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
			request.setAttribute("error", "<p style='color:red'>Không được quyền vao.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/admin-index");
			rd.forward(request, response);
			return;
		}
		ContactDAO contactDao = new ContactDAO();
		ArrayList<Contact> listcont = new ArrayList<Contact>();
		
		int numberOfItems = contactDao.getNumberOfContact();
		int numberOfPages = (int) Math.ceil((float)numberOfItems/DefineUtil.NUMBER_PER_PAGE);
		System.out.println(numberOfPages);
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}catch (NumberFormatException e) {
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;
		
		listcont = contactDao.getContPagination(offset);
		if(request.getParameter("timkiem")!=null) {
			String search = request.getParameter("timkiem");
			listcont = contactDao.getCatSearch(search);
			request.setAttribute("search", search);
			
		}
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("listcont",listcont);
		
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/contact/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
