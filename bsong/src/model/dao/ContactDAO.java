package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Contact;
import utils.ConnectDBLibrary;
import utils.DefineUtil;

public class ContactDAO extends AbstractDAO{
	
	public ContactDAO() {
		connectdblibrary = new ConnectDBLibrary();
	}
	
	public ArrayList<Contact> getContList() {
		ArrayList<Contact> listcont = new ArrayList<Contact>();
		String sql = "SELECT * FROM contacts ORDER BY id DESC";
		con = connectdblibrary.getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Contact cont = new Contact(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("website"),rs.getString("message"));
				listcont.add(cont);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, st, con);
		}
		return listcont;
	}

	public ArrayList<Contact> getCatSearch(String search) {
			ArrayList<Contact> listcont = new ArrayList<Contact>();
			String sql = "SELECT * FROM contacts WHERE name LIKE ?";
			con = connectdblibrary.getConnection();
			try {
				pst = con.prepareStatement(sql);
				pst.setString(1, "%"+search+"%");
				rs = pst.executeQuery();
				while(rs.next()) {
					Contact cont = new Contact(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("website"),rs.getString("message"));
					listcont.add(cont);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				connectdblibrary.close(rs, pst, con);
			}
			return listcont;
	}

	public Contact getContactByCid(int contid) {
		String sql = "SELECT * FROM contacts WHERE id = ?";
		con = connectdblibrary.getConnection();
		Contact cont = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, contid);
			rs = pst.executeQuery();
			if(rs.next()) {
			 cont = new Contact(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("website"),rs.getString("message"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, st, con);
		}
		return cont;
	}

	public int editCat(Contact contact) {
		String sql = "UPDATE contacts SET name = ?, email =?, website=?, message=? WHERE id= ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setString(1, contact.getName());
			pst.setString(2, contact.getEmail());
			pst.setString(3, contact.getWebsite());
			pst.setString(4, contact.getContent());
			pst.setInt(5, contact.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;

	}

	public int delcontact(int contid) {
		String sql = "DELETE FROM contacts WHERE id= ?";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setInt(1, contid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int addContact(Contact contact) {
		String sql = "INSERT INTO contacts (name, email , website , message) VALUES(?,?,?,?)";
		int result = 0;
		con = connectdblibrary.getConnection();
		try {
			pst  = con.prepareStatement(sql);
			pst.setString(1, contact.getName());
			pst.setString(2, contact.getEmail());
			pst.setString(3, contact.getWebsite());
			pst.setString(4, contact.getContent());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(pst, con);
		}
		return result;
	}

	public int getNumberOfContact() {
		String sql = "SELECT COUNT(*) AS count FROM contacts";
		con = connectdblibrary.getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, st, con);
		}
		return 0;
	}

	public ArrayList<Contact> getContPagination(int offset) {
		ArrayList<Contact> listcont = new ArrayList<Contact>();
		String sql = "SELECT * FROM contacts ORDER BY id DESC LIMIT ?,?";
		con = connectdblibrary.getConnection();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				Contact cont = new Contact(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("website"),rs.getString("message"));
				listcont.add(cont);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectdblibrary.close(rs, pst, con);
		}
		return listcont;
	}

	
}

