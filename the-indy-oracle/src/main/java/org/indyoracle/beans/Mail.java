package org.indyoracle.beans;

public class Mail {
	private String subject;
	private String from;
	private String text;
	
	public Mail(String subject, String from, String text) {
		this.subject = subject;
		this.from    = from;
		this.text    = text;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
