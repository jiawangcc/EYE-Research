package edu.uw.jiawang;


import java.io.Serializable;

public class nurseRes implements Serializable{
	
	private int stuId;
	private int nurseId;
	private String nurse_fn;
	private String nurse_ln;
	private String note;
	private String date;
	
	public nurseRes(){		
	}
		
	public void setStuId(int stuId){
		this.stuId = stuId;
	}
	
	public int getStuId(){
		return stuId;
	}
	
	public void setNurseId(int nurseId){
		this.nurseId = nurseId;
	}
	
	public int getNurseId(){
		return nurseId;
	}
	
	public void setNurseFN(String nurse_fn){
		this.nurse_fn = nurse_fn;
	}
	
	public String getNurseFN(){
		return nurse_fn;
	}
	
	public void setNurseLN(String nurse_ln){
		this.nurse_ln = nurse_ln;
	}
	
	public String getNurseLN(){
		return nurse_ln;
	}
	
	public void setNote(String note){
		this.note = note;
	}
	
	public String getNote(){
		return note;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return date;
	}
	
}