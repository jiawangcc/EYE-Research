package edu.uw.jiawang;


import java.util.List;

public class Nurse extends User {
	
	private int nurseId;
	private List<String> schools;

	
	public Nurse(){		
	}
			
	public void setNurseId(int nurseId){
		this.nurseId = nurseId;
	}
	
	public int getNurseId(){
		return nurseId;
	}
	
	public void setSchools(List<String> schools){
		this.schools = schools;
	}
	
	public List<String> getSchools(){
		return schools;
	}
}