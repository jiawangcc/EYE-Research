package edu.uw.jiawang;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.uw.jiawang.util.PasswordUtil;

public class AdminDB extends BaseDB {

	public static String checkLogin(String email, String password){
		
		if (email == null || email.length() == 0) {
			return null;
		}
		
		if (password == null || password.length() == 0) {
			return null;
		}

        String salt = null;
        String storedPassword = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select salt, password from admin where email = ?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()){
				salt = rs.getString(1);
				storedPassword = rs.getString(2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		
		if (salt == null || storedPassword == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		try {
			if (PasswordUtil.validatePassword(password,
					sb.append(salt).append(PasswordUtil.SALT_PASSWORD_SEPARATOR).append(storedPassword).toString())) {
				return email;
			} else {
				return null;
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
