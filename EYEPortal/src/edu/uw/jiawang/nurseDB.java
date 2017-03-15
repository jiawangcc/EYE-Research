package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class nurseDB extends UserDB {
	
	public static boolean insertNewReferral(int studentId, int nurseId, int doctorId) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into referral(student_id,refer_by_nurse_id,refer_to_doctor_id)"+ 
					" values(?,?,?);";
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, studentId);
			stmt.setInt(2, nurseId);
			stmt.setInt(3, doctorId);
			
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

}
