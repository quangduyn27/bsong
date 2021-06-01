package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Cat;
import model.bean.Song;
import utils.ConnectDBLibrary;
import utils.DefineUtil;

public class SongDAO extends AbstractDAO {
	public SongDAO() {
		connectdblibrary = new ConnectDBLibrary();
	}

	public ArrayList<Song> getSongList() {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id ORDER BY songs.id DESC";
		con = connectdblibrary.getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, st, con);
		}
		return listsong;
	}

	public ArrayList<Song> getSongSearchIndexPagination(String search, int cid, int offset) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		con = connectdblibrary.getConnection();
		
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE songs.name LIKE ?";
		if(cid != 0) {
			sql += " AND categories.id = ? ";
		}
		sql += " LIMIT ?,?";
		try {
			pst = con.prepareStatement(sql);
			
			pst.setString(1, "%" + search + "%");
			if(cid != 0) {
				pst.setInt(2, cid);
				pst.setInt(3, offset);
				pst.setInt(4, DefineUtil.NUMBER_PER_PAGE);
				}else {
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}
	public int getNumberOfSongSearchIndex(String search, int cid) {
		con = connectdblibrary.getConnection();
		
		String sql = "SELECT COUNT(*) AS count FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE songs.name LIKE ?";
		if(cid != 0) {
			sql += " AND categories.id = ? ";
		}
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, "%" + search + "%");
			if(cid != 0) {
				pst.setInt(2, cid);
			}
			rs = pst.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return 0;
	}
	public ArrayList<Song> getSongSearch(String search) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE songs.name LIKE ?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, "%" + search + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}

	public int addSong(Song song) {
		String sql = "INSERT INTO songs (name, preview_text, detail_text, picture,mp3, cat_id)	 VALUES (?,?,?,?,?,?)";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setString(1, song.getName());
			pst.setString(2, song.getPreview());
			pst.setString(3, song.getDetail());
			pst.setString(4, song.getPicture());
			pst.setString(5, song.getMp3());
			pst.setInt(6, song.getCat().getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public Song getSongBySid(int sid) {
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE songs.id =? ";
		con = connectdblibrary.getConnection();
		Song song = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, sid);
			rs = pst.executeQuery();
			while (rs.next()) {
				 song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return song;
	}

	public int editSong(Song song) {
		String sql = "UPDATE songs SET name=?, preview_text = ?, detail_text = ?, picture =?,mp3=?, counter= ?, cat_id=? WHERE id =?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setString(1, song.getName());
			pst.setString(2, song.getPreview());
			pst.setString(3, song.getDetail());
			pst.setString(4, song.getPicture());
			pst.setString(5, song.getMp3());
			pst.setInt(6, 0);
			pst.setInt(7, song.getCat().getId());
			pst.setInt(8, song.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int delSong(int sid) {
		String sql = "DELETE FROM songs WHERE id =?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, sid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}
	public ArrayList<Song> get6NewSong() {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id ORDER BY songs.id DESC LIMIT 5";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
					rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}

	public ArrayList<Song> getSongByCid(int cid) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE categories.id =? ";
		con = connectdblibrary.getConnection();
		
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}

	public ArrayList<Song> getRandSong(int sid, int cid) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE songs.id !=? AND songs.cat_id =? ORDER BY RAND() LIMIT 2";
		con = connectdblibrary.getConnection();
		
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, sid);
			pst.setInt(2, cid);
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
						rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}

	public int addView(int sid) {
		String sql = "UPDATE songs SET counter= counter + 1 WHERE id =?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, sid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int getNumberOfSong() {
		String sql = "SELECT COUNT(*) AS count FROM songs";
		con = connectdblibrary.getConnection();
		//int count = 0;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return 0;
	}

	public ArrayList<Song> getSongPagination(int offset) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id ORDER BY songs.id DESC LIMIT ?,?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
					rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}

	public int getNumberOfSong(int cid) {
		String sql = "SELECT COUNT(*) AS count FROM songs WHERE cat_id = ?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return 0;
	}

	public ArrayList<Song> getSongPaginationByCid(int cid, int offset) {
		ArrayList<Song> listsong = new ArrayList<Song>();
		String sql = "SELECT * FROM songs INNER JOIN categories ON categories.id = songs.cat_id WHERE categories.id = ? ORDER BY songs.id DESC LIMIT ?,?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, cid);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				Song song = new Song(rs.getInt("songs.id"), rs.getString("songs.name"), rs.getString("preview_text"),
					rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"),rs.getString("mp3"),
						rs.getInt("counter"), new Cat(rs.getInt("categories.id"), rs.getString("categories.name"), rs.getInt("categories.status"),rs.getInt("orders")) );
				listsong.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listsong;
	}

	public int delSongByCid(int cid) {
		String sql = "DELETE FROM songs WHERE cat_id =?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, cid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	

	
}
