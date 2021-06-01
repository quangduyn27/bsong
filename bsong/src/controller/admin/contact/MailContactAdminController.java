package controller.admin.contact;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constant.GlobalConstant;
import model.bean.Contact;
import model.dao.ContactDAO;
import utils.SendMailUtil;

public class MailContactAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MailContactAdminController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/contact/mail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		String tieude = request.getParameter("name");
		String content = request.getParameter("khungsoanthao");
		String usernam = GlobalConstant.USERNAME;
		String password = GlobalConstant.PASSWORD;

		int contid = Integer.parseInt(request.getParameter("contid"));
		
		ContactDAO contDao = new ContactDAO();
		//lấy contact cần gửi 
		Contact cont = contDao.getContactByCid(contid);
		// lấy email của contact cần gửi.
		String toEmail = cont.getEmail();
		if(SendMailUtil.sendMail(toEmail, tieude, content, usernam, password)) {
			response.sendRedirect(request.getContextPath()+"/admin-contact?msg=5");
			return;
		}
		request.setAttribute("error", "Gửi mail ko thành công");
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/contact/mail.jsp");
		rd.forward(request, response);
		
	}

}
