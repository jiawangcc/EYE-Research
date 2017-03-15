package edu.uw.jiawang;


public class Doctor extends User {
	
	private final int id;
	
	public Doctor(int id, String firstname, String lastname, String affiliation) {
		this.id = id;
		setFirstname(firstname);
		setLastname(lastname);
		setAffiliation(affiliation);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return getFirstname() + " " + getLastname();
	}
}