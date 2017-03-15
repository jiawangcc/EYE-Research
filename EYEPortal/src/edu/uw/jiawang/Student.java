package edu.uw.jiawang;

public class Student {
	
	private int stuId; 
	private String firstname;
	private String lastname;
	private String DOB;
	private String school;
	private int grade;
	
	public Student(){		
	}
			
	public void setStuId(int stuId){
		this.stuId = stuId;
	}
	
	public int getStuId(){
		return stuId;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public String getFirstname(){
		return firstname;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	public String getLastname(){
		return lastname;
	}
	
	public void setDOB(String DOB){
		this.DOB = DOB;
	}
	
	public String getDOB(){
		return DOB;
	}
	
	public void setSchool(String school){
		this.school = school;
	}
	
	public String getSchool(){
		return school;
	}
	
	public void setGrade(int grade){
		this.grade =  grade;
	}
	
	public int getGrade(){
		return grade;
	}
}