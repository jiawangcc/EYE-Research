package edu.uw.jiawang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RegisterDB extends BaseDB {

	public static boolean insertRegisterEntry(Register r) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	
			String query = "insert into registrant(first_name, last_name, affiliation, email, contact, phone)"+
					"values(?,?,?,?,?,?)";
	
			stmt = conn.prepareStatement(query);
			stmt.setString(1, r.getFirstname());
			stmt.setString(2, r.getLastname());
			stmt.setString(3, r.getAffiliation());
			stmt.setString(4, r.getEmail());
			stmt.setString(5, r.getContact());
			stmt.setString(6, r.getPhone());
	
			result = (stmt.executeUpdate() > 0);
			
			query = "insert into registrant_role(email, role)"+
					"values(?,?::role)";
			stmt2 = conn.prepareStatement(query);
			stmt2.setString(1, r.getEmail());
			stmt2.setString(2, r.getRole());
			result = result && (stmt2.executeUpdate() > 0);
	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
	
		return result;
	}
	
	public static boolean deleteRegisterRoleEntry(String email, String role) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	
			String query = "delete from registrant_role where email = ? and role = ?::role";
	
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, role);
	
			int i = stmt.executeUpdate();
			result = (i > 0);
	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
	
		return result;
	}
	
	public static Register showRegisterEntry(String email){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Register r = null;
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select role, first_name, last_name, affiliation, email, contact, phone from registrant inner join registrant_role using (email) where email = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				r = new Register(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return r;
	}
	
	public static List<Register> showAllRegisterEntries(){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Register> entries = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select role, first_name, last_name, affiliation, email, contact, phone from registrant inner join registrant_role using (email)";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Register r = new Register(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
				entries.add(r);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return entries;
	}
	
	public static boolean checkUserAvail(String email){
		boolean isAvailable = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select 1 from registrant where email = ?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			isAvailable = !(rs.next());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return isAvailable;
	}
}
