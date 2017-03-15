package edu.uw.jiawang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentDB extends BaseDB {
	
	public static boolean insertDocument(ProjectDocument document){

		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			
			String query = "insert into project_document(email, file_name, document)"+
					"values(?,?,?)";

			stmt = conn.prepareStatement(query);
			stmt.setString(1, document.getEmail());
			stmt.setString(2, document.getFileName());
			stmt.setBytes(3, document.getDocument().get());

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

	public static List<ProjectDocument> showAllDocuments(){

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ProjectDocument> docs = new ArrayList<>();

		try{

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select document_id, email, file_name, document, date from project_document";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			while(rs.next()){
				docs.add(new ProjectDocument(
						Optional.of(rs.getInt(1)),
						rs.getString(2), 
						rs.getString(3),
						Optional.of(rs.getBytes(4)),
						Optional.of(rs.getString(5))));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return docs;
	}
	
	public static ProjectDocument getDocument(int documentId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProjectDocument doc = null;

		try{

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select email, file_name, document, date" +
					" from project_document where document_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, documentId);
			rs = stmt.executeQuery();

			while(rs.next()){
				doc = new ProjectDocument(Optional.of(documentId), rs.getString(1),
						rs.getString(2), Optional.of(rs.getBytes(3)), Optional.of(rs.getString(4)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return doc;
		
	}

	public static boolean deleteDocument(int documentId) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			
			String query = "delete from project_document where document_id = ?";

			stmt = conn.prepareStatement(query);
			stmt.setInt(1, documentId);

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
