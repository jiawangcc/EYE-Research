package edu.uw.jiawang;


public class DatasetEntry {
	
	private final String school;
	private final int grade;
	private final String gameName;
	private final String testName;
	private final String score;
	private final String date;
	
	public DatasetEntry(String school, int grade, String gameName, String testName, String score, String date) {
		super();
		this.school = school;
		this.grade = grade;
		this.gameName = gameName;
		this.testName = testName;
		this.score = score;
		this.date = date;
	}

	public String getSchool() {
		return school;
	}

	public int getGrade() {
		return grade;
	}

	public String getGameName() {
		return gameName;
	}

	public String getTestName() {
		return testName;
	}

	public String getScore() {
		return score;
	}

	public String getDate() {
		return date;
	}
}