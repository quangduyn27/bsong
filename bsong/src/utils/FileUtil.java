package utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import constant.GlobalConstant;

public class FileUtil {

	public static String rename(String fileName) {
		String nameFile = "";
		if (!fileName.isEmpty()) {
			String[] arrImg = fileName.split("\\.");
			String duoiFileImg = arrImg[arrImg.length - 1];

			for (int i = 0; i < (arrImg.length - 1); i++) {
				if (i == 0) {
					nameFile = arrImg[i];
				} else {
					nameFile += "-" + arrImg[i];
				}
			}
			nameFile = nameFile + "-" + System.nanoTime() + "." + duoiFileImg;
		}
		return nameFile;
	}

	public static String getName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static String upload(String name, HttpServletRequest request) throws IOException, ServletException {
		Part filePart = request.getPart(name); // lấy file từ form
		String fileName = filePart.getSubmittedFileName(); // lay ten file, vd: bt1.png
		if (!fileName.equals("")) {
			fileName = StringUtil.makeSlug(StringUtil.getTenFile(fileName)) + System.nanoTime() + ".jpg";
			String dirPath = request.getServletContext().getRealPath(""); // tạo đường dẫn thực
			String dirUpload = dirPath + GlobalConstant.DIR_UPLOAD; // tạo đường dẫn thực lưu file
			File dirFileUpload = new File(dirUpload); // tạo mới thư mục lưu ảnh
			if (!dirFileUpload.exists()) {
				dirFileUpload.mkdirs(); // neu chua ton tai thi tao moi thu muc
			}
			System.out.println(dirUpload);
			String filePath = dirUpload + File.separator + fileName; // file se upload
			filePart.write(filePath);
		}
		return fileName;
	}
	public static String uploadmp3(String name, HttpServletRequest request) throws IOException, ServletException {
		Part filePart = request.getPart(name); // lấy file từ form
		String fileName = filePart.getSubmittedFileName(); // lay ten file, vd: bt1.png
		if (!fileName.equals("")) {
			fileName = StringUtil.makeSlug(StringUtil.getTenFile(fileName)) + System.nanoTime() + ".mp3";
			String dirPath = request.getServletContext().getRealPath(""); // tạo đường dẫn thực đến dự án
			String dirUpload = dirPath + GlobalConstant.DIR_UPLOAD_MP3; // tạo đường dẫn thực từ dự án đên file upload
			File dirFileUpload = new File(dirUpload); // tạo mới thư mục lưu ảnh
			if (!dirFileUpload.exists()) {
				dirFileUpload.mkdirs(); // neu chua ton tai thi tao moi thu muc
			}
			System.out.println(dirUpload);
			String filePath = dirUpload + File.separator + fileName; // file se upload
			filePart.write(filePath);
		}
		return fileName;
	}
	
	public static boolean delFile(String fileName, HttpServletRequest request) {
		if (!fileName.equals("")) {
			String filePath = request.getServletContext().getRealPath("") + GlobalConstant.DIR_UPLOAD + File.separator + fileName;
			File file = new File(filePath);
			return file.delete(); // true
		}
		return false;
	}
	public static boolean delFileMp3(String fileName, HttpServletRequest request) {
		if (!fileName.equals("")) {
			String filePath = request.getServletContext().getRealPath("") + GlobalConstant.DIR_UPLOAD_MP3 + File.separator + fileName;
			File file = new File(filePath);
			return file.delete(); // true
		}
		return false;
	}
	
}
