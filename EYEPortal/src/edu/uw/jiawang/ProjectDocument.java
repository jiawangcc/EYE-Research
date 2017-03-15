package edu.uw.jiawang;

import java.util.Optional;

public class ProjectDocument {

	private final Optional<Integer> documentId;
	private final String email;
	private final String fileName;
	private final Optional<byte[]> document;
	private final Optional<String> date;

	public ProjectDocument(Optional<Integer> documentId, String email, String fileName, Optional<byte[]> document, Optional<String> date) {
		this.documentId = documentId;
		this.email = email;
		this.fileName = fileName;
		this.document = document;
		this.date = date;
	}


	public Optional<Integer> getDocumentId() {
		return documentId;
	}

	public Optional<String> getDate() {
		return date;
	}

	public String getEmail() {
		return email;
	}

	public String getFileName() {
		return fileName;
	}

	public Optional<byte[]> getDocument() {
		return document;
	}


}
