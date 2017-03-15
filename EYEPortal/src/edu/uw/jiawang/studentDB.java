package edu.uw.jiawang;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.uw.jiawang.util.PasswordUtil;

public class studentDB extends UserDB {
		
	public static boolean updateStudent(Student student){
	
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "update student "
					+ "set first_name = ?, last_name = ?, dob = ?::date, school = ?, grade = ? "
					+ "where student_id = ?";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, student.getFirstname());
			stmt.setString(2, student.getLastname());
			stmt.setString(3, student.getDOB());
			stmt.setString(4, student.getSchool());
			stmt.setInt(5, student.getGrade());
			stmt.setInt(6, student.getStuId());
			
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
	
	public static List<String> getAllSchools(){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> schools = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select distinct school from student order by school;";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				schools.add(rs.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return schools;
	}
	
	public static List<Integer> getAllGrades(){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Integer> grades = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select distinct grade from student order by grade;";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				grades.add(rs.getInt(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return grades;
	}
	
	public static Student showStudent(int studentId){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Student s = new Student();
		s.setStuId(studentId);
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, dob, school, grade from student where student_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, studentId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				s.setFirstname(rs.getString(1));
				s.setLastname(rs.getString(2));
				s.setDOB(rs.getString(3));
				s.setSchool(rs.getString(4));
				s.setGrade(rs.getInt(5));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return s;
	}
	
	public static List<Student> showAllStudent(Optional<String> keyword){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Student> students = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, student_id, school, grade, dob from student order by student_id";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Student s = new Student();
				s.setFirstname(rs.getString(1));
				s.setLastname(rs.getString(2));
				s.setStuId(rs.getInt(3));
				s.setSchool(rs.getString(4));
				s.setGrade(rs.getInt(5));
				s.setDOB(rs.getString(6));
				students.add(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		
		if (keyword.isPresent()) {
			return students.stream()
					.filter(s -> matchesKeyword(s, keyword.get()))
					.collect(Collectors.toList());
		} else {
			return students;
		}
	}
	
	public static List<Student> showAllStudentForNurse(int nurse_id, Optional<String> keyword){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Student> students = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, student_id, school, grade, dob from student where school in (select school from nurse_school where nurse_id = ?) order by student_id";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, nurse_id);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Student s = new Student();
				s.setFirstname(rs.getString(1));
				s.setLastname(rs.getString(2));
				s.setStuId(rs.getInt(3));
				s.setSchool(rs.getString(4));
				s.setGrade(rs.getInt(5));
				s.setDOB(rs.getString(6));
				students.add(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		
		if (keyword.isPresent()) {
			return students.stream()
					.filter(s -> matchesKeyword(s, keyword.get()))
					.collect(Collectors.toList());
		} else {
			return students;
		}
	}
	
	public static List<Student> showStudentsByReferralStatus(int doctorId, Optional<Boolean> diagnosed, Optional<String> keyword){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Student> students = new ArrayList<>();
		
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			StringBuilder queryBuilder = new StringBuilder("with tmp as "
					+ "(select student.student_id, max(referral.date) as latest_referral, max(doctor_note.date) as latest_note "
					+ "from student inner join referral using (student_id) "
					+ "left outer join doctor_note on referral.refer_to_doctor_id = doctor_note.doctor_id "
					+ "where refer_to_doctor_id = ? "
					+ "group by student.student_id, doctor_id) "
					+ "select first_name, last_name, student_id, school, grade, dob from student "
					+ "where student_id in (select student_id from tmp where 1=1");
			if (diagnosed.isPresent()) {
				queryBuilder.append(" and latest_referral ");
				queryBuilder.append(diagnosed.get() ? '<' : '>');
				queryBuilder.append(" coalesce(latest_note,to_timestamp(0))");
			}
			queryBuilder.append(")");
			
			stmt = conn.prepareStatement(queryBuilder.toString());
			stmt.setInt(1, doctorId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Student s = new Student();
				s.setFirstname(rs.getString(1));
				s.setLastname(rs.getString(2));
				s.setStuId(rs.getInt(3));
				s.setSchool(rs.getString(4));
				s.setGrade(rs.getInt(5));
				s.setDOB(rs.getString(6));
				students.add(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		
		if (keyword.isPresent()) {
			return students.stream()
					.filter(s -> matchesKeyword(s, keyword.get()))
					.collect(Collectors.toList());
		} else {
			return students;
		}
	}

	public static String checkLogin(int id, String password) {
		
		if (password == null || password.length() == 0) {
			return "";
		}

        String salt = null;
        String storedPassword = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select salt, password from student where student_id = ?");
			stmt.setInt(1, id);
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
	
	public static String selectSalt(int id) {
		
        String salt = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select salt from student where student_id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()){
				salt = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return salt;
	}
	
	public static boolean checkSyncLogin(int id, String password) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean result = false;
		try{
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select 1 from student where student_id = ? and password = ?");
			stmt.setInt(1, id);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return result;
	}
	
	static boolean matchesKeyword(Student s, String keyword) {
		StringBuilder regexBuilder = new StringBuilder(".*(");
		regexBuilder.append(Arrays.asList(keyword.split(" ")).stream().collect(Collectors.joining("|")));
		regexBuilder.append(").*");
		Pattern p = Pattern.compile(regexBuilder.toString(), Pattern.CASE_INSENSITIVE);
		
		StringBuilder candidateBuilder = new StringBuilder();
		candidateBuilder.append(s.getStuId());
		candidateBuilder.append(s.getFirstname());
		candidateBuilder.append(s.getLastname());
		candidateBuilder.append(s.getGrade());
		candidateBuilder.append(s.getSchool());
		
		return p.matcher(candidateBuilder.toString()).matches();
	}
	
}
