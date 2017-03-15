package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class doctorDB extends UserDB {
	
	public static List<Doctor> listAllDoctors(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Doctor> doctors = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select doctor_id, first_name, last_name, affiliation from doctor;";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				doctors.add(new Doctor(rs.getInt(1),
						rs.getString(2), rs.getString(3),
						rs.getString(4)));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return doctors;
	}
}
