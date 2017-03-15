package edu.uw.jiawang;


import java.io.Serializable;

public class doctorRes implements Serializable{
	
	private int stuId;
	private int doctorId;
	private String doctor_fn;
	private String doctor_ln;
	private String note;
	private String date;
	
	public doctorRes(){		
	}
		
	public void setStuId(int stuId){
		this.stuId = stuId;
	}
	
	public int getStuId(){
		return stuId;
	}
	
	public void setDoctorId(int doctorId){
		this.doctorId = doctorId;
	}
	
	public int getDoctorId(){
		return doctorId;
	}
	
	public void setDoctorFN(String doctor_fn){
		this.doctor_fn = doctor_fn;
	}
	
	public String getDoctorFN(){
		return doctor_fn;
	}
	
	public void setDoctorLN(String doctor_ln){
		this.doctor_ln = doctor_ln;
	}
	
	public String getDoctorLN(){
		return doctor_ln;
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