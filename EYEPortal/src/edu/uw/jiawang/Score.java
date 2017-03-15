package edu.uw.jiawang;


import java.io.Serializable;

public class Score implements Serializable{
	
	private int stuId;
	private int gameId; 
	private int testId;
	private String score;
	private String gameName;
	private String testName;
	private String date;
	
	public Score(){		
	}
		
	public void setStuId(int stuId){
		this.stuId = stuId;
	}
	
	public int getStuId(){
		return stuId;
	}
	
	public void setGameId(int gameId){
		this.gameId = gameId;
	}
	
	public int getGameId(){
		return gameId;
	}
	
	public void setGameName(String gameName){
		this.gameName = gameName;
	}
	
	public String getGameName(){
		return gameName;
	}
	
	public void setScore(String score){
		this.score = score;
	}
	
	public String getScore(){
		return score;
	}
	
	public void setTestId(int testId){
		this.testId =  testId;
	}
	
	public int getTestId(){
		return testId;
	}
	
	public void setTestName(String testName){
		this.testName = testName;
	}
	
	public String getTestName(){
		return testName;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return date;
	}
	
}