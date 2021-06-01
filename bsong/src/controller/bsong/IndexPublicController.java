
package controller.bsong;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Song;
import model.dao.SongDAO;
import utils.DefineUtil;

public class IndexPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IndexPublicController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		SongDAO songDao = new SongDAO();
		ArrayList<Song> listsong = new ArrayList<Song>();

		int numberOfSongs, offset, numberOfPages, currentpage, cid = 0;
		String search = "";

		if (request.getParameter("timkiem") != null) {
			search = request.getParameter("timkiem");
			if (request.getParameter("category") != null) {
				cid = Integer.parseInt(request.getParameter("category"));
			}
			numberOfSongs = songDao.getNumberOfSongSearchIndex(search, cid);
			numberOfPages = (int) Math.ceil((float) numberOfSongs / DefineUtil.NUMBER_PER_PAGE);
			currentpage = 1;
			if (request.getParameter("page") != null) {
				try {
					currentpage = Integer.parseInt(request.getParameter("page"));
					if (currentpage < 1) {
						throw new Exception();
					}
				} catch (Exception e) {
					response.sendRedirect(request.getContextPath() + "/public-index?msgErr=URL");
					return;
				}
			}
			offset = (currentpage - 1) * DefineUtil.NUMBER_PER_PAGE;

			listsong = songDao.getSongSearchIndexPagination(search, cid, offset);
			request.setAttribute("search", search);
			request.setAttribute("category", cid);

		} else {

			numberOfSongs = songDao.getNumberOfSong();
			numberOfPages = (int) Math.ceil((float) numberOfSongs / DefineUtil.NUMBER_PER_PAGE);

			currentpage = 1;
			if (request.getParameter("page") != null) {
				try {
					currentpage = Integer.parseInt(request.getParameter("page"));
					if (currentpage < 1) {
						throw new Exception();
					}
				} catch (Exception e) {
					response.sendRedirect(request.getContextPath() + "/public-index?msgErr=URL");
					return;
				}
			}
			offset = (currentpage - 1) * DefineUtil.NUMBER_PER_PAGE;
			listsong = songDao.getSongPagination(offset);

		}
		
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("listsong", listsong);
		RequestDispatcher rd = request.getRequestDispatcher("/view/public/index.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
