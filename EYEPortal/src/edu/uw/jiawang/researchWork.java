package edu.uw.jiawang;


import java.util.Optional;

public class researchWork {
	
	private int paperId;
	private int researcherId;
    private String firstname;
    private String lastname;
    private String affiliation;
    private String email;
    private Optional<byte[]> paper;
    private String filename;
    private String title;
    private String keyword;
    private String abst;
    private String date;

    public researchWork(){
    }

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}
	
	public int getResearcherId() {
		return researcherId;
	}

	public void setResearcherId(int researcherId) {
		this.researcherId = researcherId;
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

    public void setAffil(String affiliation){
        this.affiliation = affiliation;
    }

    public String getAffil(){
        return affiliation;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPaper(Optional<byte[]> paper){
        this.paper = paper;
    }

    public Optional<byte[]> getPaper(){
        return paper;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getFilename(){
        return filename;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

    public String getKeyword(){
        return keyword;
    }

    public void setAbstract(String abst){
        this.abst = abst;
    }

    public String getAbstract(){
        return abst;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }
}