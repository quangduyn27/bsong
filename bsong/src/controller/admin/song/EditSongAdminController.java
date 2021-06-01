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
public class EditSongAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditSongAdminController() {
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
		CatDAO catDao = new CatDAO();
		
		request.setAttribute("listcat", catDao.getCatList());
		request.setAttribute("song", songDao.getSongBySid(sid));
		RequestDispatcher rd = request.getRequestDispatcher("/view/admin/song/edit.jsp");
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
		
		SongDAO songDao = new SongDAO();
		int sid = Integer.parseInt(request.getParameter("sid"));
		System.out.println("Sid:" + sid);
		Song oldSong = songDao.getSongBySid(sid);
		String name = request.getParameter("name");
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		int idcat = Integer.parseInt(request.getParameter("category"));

		if (name.equals("")| preview.equals("")|detail.equals("")) {
			request.setAttribute("error", "<p style='color:red'>Vui lòng nhập.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp");
			rd.forward(request, response);
			return;
			} 
		String fileName = FileUtil.upload("picture", request);
		
		if (fileName.equals("")) {
			fileName = oldSong.getPicture();  //neu rong thi lay file cu
		} else {
			if (FileUtil.delFile(oldSong.getPicture(), request)) { //co gia tri thi xoa file cu
				System.out.println();
			}
		}
		String mp3 = FileUtil.uploadmp3("mp3", request);
		if (mp3.equals("")) {
			mp3 = oldSong.getMp3();  //neu rong thi lay file cu
		} else {
			if (FileUtil.delFileMp3(oldSong.getMp3(), request)) { //co gia tri thi xoa file cu
				System.out.println();
			}
		}
		Cat cat = new Cat(idcat, null, 1,0);
		Song song = new Song(sid, name, preview, detail, null, fileName,mp3, 0, cat);

		if (songDao.editSong(song) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin-song?msg=2&?sid="+sid);
		} else {
			
			response.sendRedirect(request.getContextPath() + "/admin-song?msg=4");
		}
	}

}
