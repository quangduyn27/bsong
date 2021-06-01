package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import utils.ConnectDBLibrary;

public abstract class AbstractDAO {

	public ConnectDBLibrary connectdblibrary;
	public Connection con;
	public Statement st;
	public ResultSet rs;
	public PreparedStatement pst;
	
}
