package controller.bsong;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Contact;
import model.dao.ContactDAO;

public class ContactPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ContactPublicController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/view/public/contact.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String content = request.getParameter("message");
		
		if(name.equals("")|email.equals("")) {
			
			request.setAttribute("error", "<p style='color:red'>Vui lòng nhập</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/view/public/contact.jsp");
			rd.forward(request, response);
			return;
			
		}
		Contact contact = new Contact(0, name, email, website, content);
		ContactDAO contactDao = new ContactDAO();
		
		if(contactDao.addContact(contact)>0) {
			request.setAttribute("success", "<p style='color:green'>Gửi thành công.</p>");
			
		}else {
			request.setAttribute("failed", "<p style='color:red'>lỗi khi gửi.</p>");

		}
		RequestDispatcher rd = request.getRequestDispatcher("/view/public/contact.jsp"); // dù có forwrad qua servlet hay jsp khác thì đường dẫn vẫn là sẻvlet chứa 
		rd.forward(request, response);
		//response.sendRedirect(request.getContextPath()+"/public-index");
	}

}
