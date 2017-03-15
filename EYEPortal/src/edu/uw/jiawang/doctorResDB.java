package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class doctorResDB extends BaseDB {
	
	public static ArrayList<doctorRes> showNote(int studentId){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<doctorRes> results = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, date, note from doctor inner join doctor_note using (doctor_id) "
					+ "where student_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, studentId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				doctorRes dr = new doctorRes();
				dr.setDoctorFN(rs.getString(1));
				dr.setDoctorLN(rs.getString(2));
				dr.setDate(rs.getString(3));
				dr.setNote(rs.getString(4));
				results.add(dr);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return results;
	}
	
	public static boolean insertNote(doctorRes dr){

		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

			String query = "insert into doctor_note(doctor_id, student_id, note)"+
					"values(?,?,?)";

			stmt = conn.prepareStatement(query);
			stmt.setInt(1, dr.getDoctorId());
			stmt.setInt(2, dr.getStuId());
			stmt.setString(3, dr.getNote());

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
