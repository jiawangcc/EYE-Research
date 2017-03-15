package edu.uw.jiawang;

public class Register {
	private final String role;
	private final String firstname;
	private final String lastname;
	private final String affiliation;
	private final String email;
	private final String contact;
	private final String phone;
	
	public Register(String role, String firstname, String lastname, String affiliation, String email, String contact,
			String phone) {
		this.role = role;
		this.firstname = firstname;
		this.lastname = lastname;
		this.affiliation = affiliation;
		this.email = email;
		this.contact = contact;
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public String getEmail() {
		return email;
	}

	public String getContact() {
		return contact;
	}

	public String getPhone() {
		return phone;
	}
	
	
}
