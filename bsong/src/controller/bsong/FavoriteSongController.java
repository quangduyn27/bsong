package controller.bsong;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Favorite;
import model.bean.Song;
import model.bean.User;
import model.dao.FavoriteDAO;
import utils.DefineUtil;

public class FavoriteSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FavoriteDAO fd;

	public FavoriteSongController() {
		super();
		fd = new FavoriteDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userLogin");
		int NumberoffavoriteSongs = fd.getNumberFavoriteByUid(user.getId());
		System.out.println(NumberoffavoriteSongs);
		 int NumberOfPages = (int) Math.ceil((float) NumberoffavoriteSongs / DefineUtil.NUMBER_PER_PAGE);
		 int currentpage = 1;
		if (request.getParameter("page") != null) {
			try {
				currentpage = Integer.parseInt(request.getParameter("page"));
				if (currentpage < 1) {
					throw new Exception();
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/public-favorite-song?msgErr=URL");
				return;
			}
		}
		 int offset = (currentpage - 1) * DefineUtil.NUMBER_PER_PAGE;

		ArrayList<Song> favoriteSongs = fd.getFavoriteSongs(user.getId(), offset);
		ArrayList<Favorite> favorite = fd.getFavoriteByUid(user.getId());
		System.out.println(favoriteSongs);
		request.setAttribute("listfavorite", favorite);
		request.setAttribute("listsong", favoriteSongs);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("numberOfPages", NumberOfPages);
		RequestDispatcher rd = request.getRequestDispatcher("/view/public/favorite.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userLogin");

		int trangthai = Integer.parseInt(request.getParameter("atrangthai"));
		int sid = Integer.parseInt(request.getParameter("aidsong"));
		if (trangthai == 1) {

			Favorite fv = new Favorite(0, user.getId(), sid, trangthai);
			if (fd.addLike(fv) > 0) {
				System.out.println("thich thanh cong");
			}
			response.getWriter()
					.print("<a href=\"javascript:void(0)\" onclick=\"return getLike(0)\" title=\"\"><img src=\""
							+ request.getContextPath() + "/templates/public/images/deactive.gif\"\n" + "alt=\"\" />");
		} else {
			if (fd.delLike(sid, user.getId()) > 0) {
				System.out.println("bo thich thanh cong");
			}
			response.getWriter()
					.print("<a href=\"javascript:void(0)\" onclick=\"return getLike(1)\" title=\"\"><img src=\""
							+ request.getContextPath() + "/templates/public/images/active.gif\"\n" + "alt=\"\" />");
		}
	}

}
