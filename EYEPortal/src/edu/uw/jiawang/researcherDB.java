package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class researcherDB extends UserDB {

	public static boolean updateResearcher(Researcher r){
		
	boolean result = false;
	Connection conn = null;
	PreparedStatement stmt = null;
	try{			
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		
		String query = "update Researcher "
				+ "set firstname = ?, lastname = ?, affiliation = ?, password = ? "
				+ "where researcherId = ?";
		
		stmt = conn.prepareStatement(query);
		stmt.setString(1, r.getFirstname());
		stmt.setString(2, r.getLastname());
		stmt.setString(3, r.getAffiliation());
		stmt.setInt(4, r.getResId());
		
		int i = stmt.executeUpdate();
		if(i > 0){
			result = true;
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		closeAll(stmt, conn);
	}
	
	return result;
}
	
	public static Researcher showResearcher(int resId){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Researcher r = new Researcher();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select firstname, lastname, affiliation, email from Researcher where researcherId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, resId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				r.setFirstname(rs.getString(1));
				r.setLastname(rs.getString(2));
				r.setAffiliation(rs.getString(3));
				r.setEmail(rs.getString(4));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return r;
	}
}
