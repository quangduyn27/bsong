package controller.admin.song;

import java.io.IOException;

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
import utils.FileUtil;

public class DelSongAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DelSongAdminController() {
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
		SongDAO songDao = new SongDAO();
		int sid = Integer.parseInt(request.getParameter("sid"));
		Song songbyid  = songDao.getSongBySid(sid);
		
		if(songDao.delSong(sid)>0) {
			FileUtil.delFile(songbyid.getPicture(), request);
			FileUtil.delFileMp3(songbyid.getMp3(), request);
			response.sendRedirect(request.getContextPath() + "/admin-song?msg=3");

		}else {
			response.sendRedirect(request.getContextPath() + "/admin-song?msg=4");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
