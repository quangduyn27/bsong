package controller.admin.song;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Cat;
import model.bean.Song;
import model.bean.User;
import model.dao.CatDAO;
import model.dao.SongDAO;
import utils.AuthUtil;
import utils.FileUtil;

@MultipartConfig
public class AddSongAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddSongAdminController() {
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
		CatDAO catDao = new CatDAO();
		request.setAttribute("listcat", catDao.getCatList());
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(AuthUtil.checkLogin(request, response)== false) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		SongDAO songDao = new SongDAO();
		CatDAO catDao = new CatDAO();

		String name = request.getParameter("name");
		String preview = request.getParameter("preview");
		String detail = request.getParameter("khungsoanthao");
		int idcat = Integer.parseInt(request.getParameter("category"));

//		if (name.equals("") | preview.equals("") | detail.equals("")) {
//			request.setAttribute("error", "<p style='color:red'>Vui lòng nhập.</p>");
//			request.setAttribute("listcat", catDao.getCatList());
//
//			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/add.jsp");
//			rd.forward(request, response);
//			return;
//		}
		
		String fileName = FileUtil.upload("picture", request);
		
//		if (fileName.equals("")) {
//			request.setAttribute("error", "<p style='color:red'>Vui lòng chon file hinh anh</p>");
//			request.setAttribute("listcat", catDao.getCatList());
//
//			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/add.jsp");
//			rd.forward(request, response);
//			return;
//		}
		String mp3 = FileUtil.uploadmp3("mp3", request);
//		if (mp3.equals("")) {
//			request.setAttribute("error", "<p style='color:red'>Vui lòng chon file mp3</p>");
//			request.setAttribute("listcat", catDao.getCatList());
//
//			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/add.jsp");
//			rd.forward(request, response);
//			return;
//		}
		Cat cat = new Cat(idcat, null,1,0);
		Song song = new Song(0, name, preview, detail, null, fileName,mp3, 0, cat);

		if (songDao.addSong(song) > 0) {

			response.sendRedirect(request.getContextPath() + "/admin-song?msg=1");
		} else {

			request.setAttribute("listcat", catDao.getCatList());

			request.setAttribute("song", song);
			RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/add.jsp?msg=2");
			rd.forward(request, response);
		}
	}

}
