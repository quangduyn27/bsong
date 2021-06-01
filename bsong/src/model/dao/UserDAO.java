package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.User;
import utils.ConnectDBLibrary;
import utils.DefineUtil;

public class UserDAO extends AbstractDAO {
	public UserDAO() {
		connectdblibrary = new ConnectDBLibrary();
	}

	public ArrayList<User> getUserList() {
		ArrayList<User> listUser = new ArrayList<User>();
		String sql = "SELECT * FROM users ORDER BY id DESC";
		con = connectdblibrary.getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("id_role"));
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, st, con);
		}
		return listUser;
	}

	public ArrayList<User> getUserSearch(String search) {
		ArrayList<User> listUser = new ArrayList<User>();
		String sql = "SELECT * FROM users WHERE username LIKE ?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, "%" + search + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("id_role"));
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listUser;
	}

	public int editUser(User user) {
		String sql = "UPDATE users SET password=?, fullname=?, id_role= ? WHERE id= ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, user.getPassword());
			pst.setString(2, user.getFullname());
			pst.setInt(3, user.getIdRole());
			pst.setInt(4, user.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public User getUserByCid(int uid) {
		String sql = "SELECT * FROM users WHERE id = ?";
		con = connectdblibrary.getConnection();
		User user = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("id_role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return user;
	}

	public int addUser(User user) {
		String sql = "INSERT INTO users (username, password, fullname, id_role) VALUES (?,?,?,?)";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getFullname());
			pst.setInt(4, user.getIdRole());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int delUser(int uid) {
		String sql = "DELETE FROM users WHERE id = ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public ArrayList<User> hasUser(String username) {
		ArrayList<User> listUser = new ArrayList<User>();
		String sql = "SELECT * FROM users WHERE username LIKE ?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, "%" + username + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("id_role"));
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listUser;
	}

	public User existUser(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		con = connectdblibrary.getConnection();
		User user = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("id_role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, pst, con);
		}
		return user;
	}

	public int getNumberOfUser() {
		String sql = "SELECT COUNT(*) AS count FROM users ";
		con = connectdblibrary.getConnection();
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
			connectdblibrary.close(rs, st, con);
		}
		return 0;
	}

	public ArrayList<User> getUserPagination(int offset) {
		ArrayList<User> listUser = new ArrayList<User>();
		String sql = "SELECT * FROM users ORDER BY id DESC LIMIT ?,?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("id_role"));
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectdblibrary.close(rs, st, con);
		}
		return listUser;
	}

}
