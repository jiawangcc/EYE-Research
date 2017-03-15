package edu.uw.jiawang;

public class User {
	
	private String firstname;
	private String lastname;
	private String email;
	private String affiliation;
	
	protected User() {}
	
	protected User(String firstname, String lastname, String email, String affiliation) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.affiliation = affiliation;
	}

	protected void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	protected void setLastname(String lastname) {
		this.lastname = lastname;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	protected void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	protected String getFirstname() {
		return firstname;
	}

	protected String getLastname() {
		return lastname;
	}

	protected String getEmail() {
		return email;
	}

	public String getAffiliation() {
		return affiliation;
	}
}
