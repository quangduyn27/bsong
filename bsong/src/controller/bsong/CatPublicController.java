package controller.bsong;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Song;
import model.dao.CatDAO;
import model.dao.SongDAO;
import utils.DefineUtil;

public class CatPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CatPublicController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int cid = Integer.parseInt(request.getParameter("cid"));

		CatDAO catDao = new CatDAO();
		SongDAO songDao = new SongDAO();
		int numberOfSongs = songDao.getNumberOfSong(cid);
		int numberOfPages = (int) Math.ceil((float) numberOfSongs / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		}
		if (currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;
		ArrayList<Song> listsongbycid = songDao.getSongPaginationByCid(cid, offset);
		System.out.println("page" + currentPage);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("listsongbycid", listsongbycid);
		request.setAttribute("cat", catDao.getCatByCid(cid));
		RequestDispatcher rd = request.getRequestDispatcher("/view/public/cat.jsp");
		rd.forward(request, response);
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
