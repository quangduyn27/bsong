package utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;

public class AuthUtil {
	
	public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		if (session.getAttribute("userLogin") == null) {
			return false;
		}
		return true;
	}
	
	public static User checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		if (session.getAttribute("userLogin") != null) {
			return (User) session.getAttribute("userLogin");
		}
		return null;
	}
	
}
