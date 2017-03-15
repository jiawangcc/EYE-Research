package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class nurseResDB extends BaseDB {
	
	public static boolean insertNote(nurseRes nr){
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into nurse_note(nurse_id, student_id, note)"+ 
					"values(?,?,?)";
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, nr.getNurseId());
			stmt.setInt(2, nr.getStuId());
			stmt.setString(3, nr.getNote());
			
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
	
	public static ArrayList<nurseRes> showNote(int studentId){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<nurseRes> results = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, date, note from nurse inner join nurse_note using (nurse_id) "
					+ "where student_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, studentId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				nurseRes nr = new nurseRes();
				nr.setNurseFN(rs.getString(1));
				nr.setNurseLN(rs.getString(2));
				nr.setDate(rs.getString(3));
				nr.setNote(rs.getString(4));
				results.add(nr);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return results;
	}
}
