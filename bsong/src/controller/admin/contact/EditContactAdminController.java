package controller.admin.contact;

import java.io.IOException;

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

public class EditContactAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditContactAdminController() {
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
		int contid = Integer.parseInt(request.getParameter("contid"));
		ContactDAO contactDao = new ContactDAO();
		request.setAttribute("contact", contactDao.getContactByCid(contid));
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/contact/edit.jsp");
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
		ContactDAO contactDao = new ContactDAO();
		int contid = Integer.parseInt(request.getParameter("contid"));
		String name =  request.getParameter("name");
		String email =  request.getParameter("email");
		String website =  request.getParameter("website");
		String message =  request.getParameter("message");
		if(name.equals("")|email.equals("")|website.equals("")|message.equals("")) {
			request.setAttribute("error", "Vuilòng nhập");
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/contact/edit.jsp");
			rd.forward(request, response);
			return;
		}
		Contact contact = new Contact(contid, name, email, website, message);
		if(contactDao.editCat(contact)>0) {
			response.sendRedirect(request.getContextPath()+ "/admin-contact?msg=2");
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin-contact?msg=4");
		}
	}

}
