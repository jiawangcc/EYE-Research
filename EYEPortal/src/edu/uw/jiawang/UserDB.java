package edu.uw.jiawang;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.uw.jiawang.util.PasswordUtil;

public abstract class UserDB extends BaseDB {
	
	protected static boolean checkUserExistence(String email, String role) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean userExists = false;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select 1 from " + role + " where email = ?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			userExists = rs.next();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return userExists;
	}
	
	//insert a user
	public static boolean insertUser(User user, String role, String pwd){
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into " + role +"(first_name, last_name, email, affiliation, salt, password)"+ 
					"values(?,?,?,?,?,?)";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getFirstname());
			stmt.setString(2, user.getLastname());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getAffiliation());
			
			String[] generatedSaltPlusPwd = PasswordUtil.generateStrongPasswordHash(pwd).split(":");
			stmt.setString(5, generatedSaltPlusPwd[0]);
			stmt.setString(6, generatedSaltPlusPwd[1]);
			
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
	
	public static boolean updatePwd(Integer id, String role, String pwd){
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "update " + role +" set salt = ?, password = ? where "+ role + "_id = ?";
			
			stmt = conn.prepareStatement(query);			
			String[] generatedSaltPlusPwd = PasswordUtil.generateStrongPasswordHash(pwd).split(":");
			stmt.setString(1, generatedSaltPlusPwd[0]);
			stmt.setString(2, generatedSaltPlusPwd[1]);
			stmt.setInt(3,id);
			
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
	
	protected static String checkLoginByEmail(String role, String email, String password) {
		
		if (password == null || password.length() == 0) {
			return "";
		}

		int id = 0;
        String salt = null;
        String storedPassword = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select " + role + "_id, salt, password from " + role + " where email = ?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
				salt = rs.getString(2);
				storedPassword = rs.getString(3);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		if (salt == null || storedPassword == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		try {
			if (PasswordUtil.validatePassword(password,
					sb.append(salt).append(PasswordUtil.SALT_PASSWORD_SEPARATOR).append(storedPassword).toString())) {
				return String.valueOf(id);
			} else {
				return "";
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	protected static boolean updatePassword() {
		return false;
	}
}
