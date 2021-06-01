package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Cat;
import utils.ConnectDBLibrary;
import utils.DefineUtil;

public class CatDAO extends AbstractDAO {

	public CatDAO() {
		connectdblibrary = new ConnectDBLibrary();
	}

	public ArrayList<Cat> getCatList() {
		ArrayList<Cat> listcat = new ArrayList<Cat>();
		String sql = "SELECT * FROM categories WHERE orders !=0 ORDER BY orders ASC ";
		con = connectdblibrary.getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Cat cat = new Cat(rs.getInt("id"), rs.getString("name"),rs.getInt("status"),rs.getInt("orders"));
				listcat.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, st, con);
		}
		return listcat;
	}

	public int addcat(Cat cat) {
		String sql = "INSERT INTO categories (name,status, orders)	 VALUES (?,?,?)";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setString(1, cat.getName());
			pst.setInt(2, 1);
			pst.setInt(3, 0);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();	
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public Cat getCatByCid(int cid) {
		String sql = "SELECT * FROM categories WHERE id = ?";
		con = connectdblibrary.getConnection();
		Cat cat = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			if(rs.next()) {
			 cat = new Cat(rs.getInt("id"), rs.getString("name"),rs.getInt("status"),rs.getInt("orders"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, st, con);
		}
		return cat;
	}

	public int editCat(Cat cat) {
		String sql = "UPDATE categories SET	name = ?, status = ? WHERE id= ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setString(1, cat.getName());
			pst.setInt(2, 1);
			pst.setInt(3, cat.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;

	}

	public int delcat(int cid) {
		String sql = "DELETE FROM categories WHERE id= ?";
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

	public ArrayList<Cat> getCatSearch(String search) {
		ArrayList<Cat> listcat = new ArrayList<Cat>();
		String sql = "SELECT * FROM categories WHERE name LIKE ?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, "%"+search+"%");
			rs = pst.executeQuery();
			while(rs.next()) {
				Cat cat = new Cat(rs.getInt("id"), rs.getString("name"),rs.getInt("status"),rs.getInt("orders"));
				listcat.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listcat;
	}

	public int activeCat(Cat cat) {
		String sql = "UPDATE categories SET	status = ? WHERE id= ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, cat.getStatus());
			pst.setInt(2, cat.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int getNumberOfCat() {
		String sql = "SELECT COUNT(*) AS count FROM categories";
		con = connectdblibrary.getConnection();
		int count = 0;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, pst, con);
		}
		return 0;
	}

	public ArrayList<Cat> getCatListPagination(int offset) {
		ArrayList<Cat> listcat = new ArrayList<Cat>();
		String sql = "SELECT * FROM categories ORDER BY id DESC LIMIT ?,?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				Cat cat = new Cat(rs.getInt("id"), rs.getString("name"),rs.getInt("status"),rs.getInt("orders"));
				listcat.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, st, con);
		}
		return listcat;
	}

	public int hasOrder(int cid) {
		String sql = "SELECT COUNT(*) AS count FROM categories WHERE orders = ?";
		con = connectdblibrary.getConnection();
		int count = 0;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			if(rs.next()) {
			 count = rs.getInt("count");
			 return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, pst, con);
		}
		return 0;
	}

	public int editOrder(int cid, int order) {
		String sql = "UPDATE categories SET	orders = ? WHERE id= ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, order);
			pst.setInt(2, cid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}	
		return result;
	}

	
	
	

}
