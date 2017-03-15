package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class researchWorkDB extends BaseDB {

	public static boolean insertWork(researchWork work){

		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into research_work(researcher_id, paper, file_name, title, keyword, abstract)"+
					"values(?,?,?,?,?,?)";

			stmt = conn.prepareStatement(query);
			stmt.setInt(1, work.getResearcherId());
			stmt.setBytes(2, work.getPaper().get());
			stmt.setString(3, work.getFilename());
			stmt.setString(4, work.getTitle());
			stmt.setString(5, work.getKeyword());
			stmt.setString(6, work.getAbstract());

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

	public static List<researchWork> showAllWork(){

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<researchWork> works = new ArrayList<>();

		try{

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, affiliation, email, title, keyword, abstract, date, paper_id" +
					" from researcher inner join research_work using(researcher_id)";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			while(rs.next()){
				researchWork r = new researchWork();
				r.setFirstname(rs.getString(1));
				r.setLastname(rs.getString(2));
				r.setAffil(rs.getString(3));
				r.setEmail(rs.getString(4));
				r.setTitle(rs.getString(5));
				r.setKeyword(rs.getString(6));
				r.setAbstract(rs.getString(7));
				r.setDate(rs.getString(8));
				r.setPaperId(rs.getInt(9));
				works.add(r);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return works;
	}
	
	public static researchWork getPaper(int paperId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		researchWork work = new researchWork();

		try{

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select file_name, paper" +
					" from research_work where paper_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, paperId);
			rs = stmt.executeQuery();

			while(rs.next()){
				work.setPaperId(paperId);
				work.setFilename(rs.getString(1));
				work.setPaper(Optional.of(rs.getBytes(2)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return work;
		
	}

	public static List<researchWork> searchWork(String keyword){

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<researchWork> works = new ArrayList<>();

		try{

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, affiliation, email, title, keyword, abstract, date, paper_id"
					+ " from researcher inner join research_work using(researcher_id) "
					+ "where first_name like '%" + keyword + "%' or last_name like '%" + keyword + "%' or affiliation like '%" 
					+ keyword + "%' or email like '%" + keyword + "%' or title like '%" + keyword + 
					"%' or keyword like '%" + keyword + "%'";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			while(rs.next()){
				researchWork r = new researchWork();
				r.setFirstname(rs.getString(1));
				r.setLastname(rs.getString(2));
				r.setAffil(rs.getString(3));
				r.setEmail(rs.getString(4));
				r.setTitle(rs.getString(5));
				r.setKeyword(rs.getString(6));
				r.setAbstract(rs.getString(7));
				r.setDate(rs.getString(8));
				r.setPaperId(rs.getInt(9));
				works.add(r);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return works;
	}
}
