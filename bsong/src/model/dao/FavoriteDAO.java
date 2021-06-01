package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Cat;
import model.bean.Favorite;
import model.bean.Song;
import utils.ConnectDBLibrary;
import utils.DefineUtil;

public class FavoriteDAO extends AbstractDAO{
	public FavoriteDAO() {
		connectdblibrary = new ConnectDBLibrary();
	}

	public int addLike(Favorite fv) {
		String sql = "INSERT INTO forvarites (iduser, idsong, trangthai) VALUES(?,?,?)";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, fv.getIduser());
			pst.setInt(2, fv.getIdsong());
			pst.setInt(3, 1);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int delLike(int sid,int uid) {
		String sql = "DELETE FROM forvarites WHERE idsong = ? AND iduser = ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, sid);
			pst.setInt(2, uid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public ArrayList<Song> getFavoriteSongs(int iduser, int offset) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM forvarites INNER JOIN songs ON forvarites.idsong = songs.id "
				+ "INNER JOIN categories ON categories.id = songs.cat_id "
				+ "WHERE iduser = ? LIMIT ?,?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, iduser);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"),rs.getInt("categories.id"),rs.getInt("status")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}
	
	public ArrayList<Favorite> getFavoriteByUid(int iduser) {
		ArrayList<Favorite> listfv = new ArrayList<Favorite>();
		String sql = "SELECT * FROM forvarites WHERE iduser = ?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, iduser);
			rs = pst.executeQuery();
			while (rs.next()) {
				Favorite fv = new Favorite(rs.getInt("idfavorite"), rs.getInt("iduser"), rs.getInt("idsong"), rs.getInt("trangthai"));
				listfv.add(fv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listfv;
	}
	
	public int getNumberFavoriteByUid(int iduser) {
		String sql = "SELECT COUNT(*) AS count FROM forvarites WHERE iduser = ?";
		con = connectdblibrary.getConnection();
		int count = 0;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, iduser);
			rs = pst.executeQuery();
			if (rs.next()) {
				 count = rs.getInt("count");
				 return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return 0;
	}

	
}
