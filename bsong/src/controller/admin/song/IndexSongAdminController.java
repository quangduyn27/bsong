package controller.admin.song;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Song;
import model.bean.User;
import model.dao.SongDAO;
import utils.AuthUtil;
import utils.DefineUtil;

public class IndexSongAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IndexSongAdminController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		if (!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if (user.getIdRole() != 2) {
			request.setAttribute("error", "<p style='color:red'>Không được quyền vao.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/admin-index");
			rd.forward(request, response);
			return;
		}
		SongDAO songDao = new SongDAO();
		// lấy số bài hát.
		int numberOfItems = songDao.getNumberOfSong();
		System.out.println(numberOfItems);

		int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		System.out.println(numberOfPages);
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			try {
	 			currentPage = Integer.parseInt(request.getParameter("page"));
				if (currentPage < 1) {
					throw new Exception();
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/admin-song?msgErr=URL");
				return;
			}
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;

		ArrayList<Song> listsong = new ArrayList<Song>();
		listsong = songDao.getSongPagination(offset);
		if (request.getParameter("timkiem") != null) {
			String search = request.getParameter("timkiem");
			listsong = songDao.getSongSearch(search);
			request.setAttribute("search", search);
		}

		request.setAttribute("currentPage", currentPage);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("listsong", listsong);
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
