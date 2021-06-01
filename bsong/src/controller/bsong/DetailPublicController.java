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

public class DetailPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DetailPublicController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SongDAO songDao = new SongDAO();
		int sid = Integer.parseInt(request.getParameter("sid"));
		if(request.getParameter("favorid")!=null) {
		int favorid = Integer.parseInt(request.getParameter("favorid"));
		request.setAttribute("favorid", favorid);
		}
		Song song = songDao.getSongBySid(sid);
		if (songDao.addView(sid) > 0) {
			System.out.println("tc");
		}

		int cid = song.getCat().getId();
		ArrayList<Song> listrandsong = songDao.getRandSong(sid, cid);

		request.setAttribute("songbydid", song);
		

		request.setAttribute("listrandsong", listrandsong);

		RequestDispatcher rd = request.getRequestDispatcher("/view/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
