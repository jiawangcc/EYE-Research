package edu.uw.jiawang;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class scoreDB extends BaseDB {
	
	public static List<Score> showStuScore(int studentId, int gameId){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Score> scores = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select name, score as answer, date, test_id "
					+ "from test inner join score using (test_id) "
					+ "where game_id = ? and student_id = ? order by name, date;";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, gameId);
			stmt.setInt(2, studentId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Score s = new Score();
				s.setTestName(rs.getString(1));
				s.setScore(rs.getString(2));
				s.setDate(rs.getString(3));
				s.setTestId(rs.getInt(4));
				scores.add(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return scores;
	}
	
	public static List<Score> showAllGames() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Score> gameNames = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select game_id, name from game order by game_id;";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Score s = new Score();
				s.setGameId(rs.getInt(1));
				s.setGameName(rs.getString(2));
				gameNames.add(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return gameNames;
	}
	
	public static List<Score> showAllTests() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Score> testNames = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select test_id, name from test order by test_id;";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Score s = new Score();
				s.setTestId(rs.getInt(1));
				s.setTestName(rs.getString(2));
				testNames.add(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return testNames;
	}
	
	public static List<Score> showAllTestsByGame(int gameId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Score> testNames = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select test_id, name from test where game_id = ? order by test_id;";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, gameId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Score s = new Score();
				s.setTestId(rs.getInt(1));
				s.setTestName(rs.getString(2));
				testNames.add(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return testNames;
	}
	
	
	public static List<Score> showAllScoreEntriesByTest(int testId) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Score> scores = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select score from score where test_id = ?;";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, testId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Score s = new Score();
				s.setScore(rs.getString(1));
				scores.add(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return scores;
	}
	
	public static boolean insertScore(Score s){
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "insert into score(student_id, test_id, date, score) "
					+ "values(?,?,?,?)";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, s.getStuId());
			stmt.setInt(2, s.getTestId());
			stmt.setTimestamp(3, Timestamp.valueOf(s.getDate()));
			stmt.setString(4, s.getScore());
						
			int i = stmt.executeUpdate();
			if(i > 0){
				result = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return result;
	}
    
    public static List<DatasetEntry> prepareScoreDataset(Optional<List<String>> schools,
    		Optional<List<Integer>> games,
    		Optional<List<Integer>> grades) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<DatasetEntry> entries = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			StringBuilder queryBuilder = new StringBuilder("select school, grade, game.name as game, test.name as test, score, date "
					+ "from game inner join test using (game_id) "
					+ "inner join score using (test_id) "
					+ "inner join student using (student_id) "
					+ "where 1=1 ");
			if (games.isPresent()) {
				String concat = games.get().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));
				queryBuilder.append("and game_id in (");
				queryBuilder.append(concat);
				queryBuilder.append(") ");
			}
			if (schools.isPresent()) {
				String concat = schools.get().stream().collect(Collectors.joining("','"));
				queryBuilder.append("and school in ('");
				queryBuilder.append(concat);
				queryBuilder.append("') ");
			}
			if (grades.isPresent()) {
				String concat = grades.get().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));
				queryBuilder.append("and grade in (");
				queryBuilder.append(concat);
				queryBuilder.append(") ");
			}
			queryBuilder.append("order by school, grade, game, test, date;");
			stmt = conn.prepareStatement(queryBuilder.toString());
			System.err.println(queryBuilder.toString());
			rs = stmt.executeQuery();
						
			while(rs.next()){
				entries.add(new DatasetEntry(rs.getString(1), 
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return entries;
	}
}
